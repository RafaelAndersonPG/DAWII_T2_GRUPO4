package com.cibertec.msresolucion.dto;

public record ReclamoUpdateRequest(
		String nombreAnalista,
		Integer estado,
		String observacion
) {
}
