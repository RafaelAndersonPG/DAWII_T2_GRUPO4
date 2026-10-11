package com.cibertec.msresolucion.negocio;

import com.cibertec.msresolucion.client.ReclamoClient;
import com.cibertec.msresolucion.dto.ReclamoResponse;
import com.cibertec.msresolucion.dto.ReclamoUpdateRequest;
import com.cibertec.msresolucion.dto.ResolucionRequest;
import com.cibertec.msresolucion.dto.ResolucionResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ResolucionService {

	public static final int ESTADO_PENDIENTE = 1;
	public static final int ESTADO_PROCEDENTE = 2;
	public static final int ESTADO_IMPROCEDENTE = 3;

	private final ReclamoClient reclamoClient;

	public ResolucionService(ReclamoClient reclamoClient) {
		this.reclamoClient = reclamoClient;
	}

	public ResolucionResponse resolverReclamo(Long numReclamo, ResolucionRequest request) {
		if (request.estado() != ESTADO_PROCEDENTE && request.estado() != ESTADO_IMPROCEDENTE) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Estado no valido. Use 2 (procedente) o 3 (improcedente)");
		}

		ReclamoResponse reclamo = obtenerReclamo(numReclamo);

		if (reclamo.estado() != ESTADO_PENDIENTE) {
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					"El reclamo " + numReclamo + " ya fue resuelto anteriormente");
		}

		ReclamoUpdateRequest actualizacion = new ReclamoUpdateRequest(
				request.nombreAnalista(),
				request.estado(),
				observacionSegunEstado(request.estado())
		);
		ReclamoResponse actualizado = reclamoClient.actualizar(numReclamo, actualizacion);

		return new ResolucionResponse(
				"Reclamo " + numReclamo + " resuelto como " + nombreEstado(request.estado()),
				actualizado
		);
	}

	private ReclamoResponse obtenerReclamo(Long numReclamo) {
		try {
			return reclamoClient.buscarPorNumero(numReclamo);
		} catch (FeignException.NotFound ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"El reclamo " + numReclamo + " no existe");
		} catch (FeignException ex) {
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"El servicio de reclamos no esta disponible");
		}
	}

	private String observacionSegunEstado(int estado) {
		return estado == ESTADO_PROCEDENTE
				? "Reclamo procedente: se devolvera el monto del consumo no reconocido"
				: "Reclamo improcedente: el consumo fue validado como realizado por el titular";
	}

	private String nombreEstado(int estado) {
		return estado == ESTADO_PROCEDENTE ? "PROCEDENTE" : "IMPROCEDENTE";
	}
}
