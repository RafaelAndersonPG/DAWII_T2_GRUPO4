package com.cibertec.msresolucion.dto;

import java.time.LocalDate;

public record ReclamoResponse(
		Integer numreclamo,
		LocalDate fechareclamo,
		String nomcliente,
		String ultimos4tarjeta,
		String motivo,
		String nombreAnalista,
		Integer estado,
		String observacion
) {
}
