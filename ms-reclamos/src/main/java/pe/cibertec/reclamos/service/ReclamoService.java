package pe.cibertec.reclamos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.cibertec.reclamos.dto.ReclamoRequest;
import pe.cibertec.reclamos.model.Reclamo;
import pe.cibertec.reclamos.repository.ReclamoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamoService {

    private final ReclamoRepository reclamoRepository;

    public ReclamoService(ReclamoRepository reclamoRepository) {
        this.reclamoRepository = reclamoRepository;
    }

    public Reclamo registrarReclamo(ReclamoRequest request) {
        if (request.nomcliente() == null || request.nomcliente().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del cliente no puede estar vacío");
        }
        if (request.ultimos4tarjeta() == null || !request.ultimos4tarjeta().matches("^\\d{4}$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los últimos 4 dígitos de la tarjeta son obligatorios y deben ser numéricos");
        }
        if (request.motivo() == null || request.motivo().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El motivo no puede estar vacío");
        }

        Reclamo reclamo = new Reclamo();
        reclamo.setNomcliente(request.nomcliente());
        reclamo.setUltimos4tarjeta(request.ultimos4tarjeta());
        reclamo.setMotivo(request.motivo());

        return reclamoRepository.save(reclamo);
    }

    public List<Reclamo> listarReclamos() {
        return reclamoRepository.findAll();
    }

    public Optional<Reclamo> buscarPorId(Integer numreclamo) {
        return reclamoRepository.findById(numreclamo);
    }

    public Reclamo actualizarReclamo(Integer numreclamo, Reclamo detallesReclamo) {
        Optional<Reclamo> reclamoOpt = reclamoRepository.findById(numreclamo);
        if (reclamoOpt.isPresent()) {
            Reclamo reclamo = reclamoOpt.get();
            reclamo.setNombreAnalista(detallesReclamo.getNombreAnalista());
            reclamo.setEstado(detallesReclamo.getEstado());
            reclamo.setObservacion(detallesReclamo.getObservacion());
            if (detallesReclamo.getMotivo() != null) {
                reclamo.setMotivo(detallesReclamo.getMotivo());
            }
            return reclamoRepository.save(reclamo);
        }
        return null;
    }
}