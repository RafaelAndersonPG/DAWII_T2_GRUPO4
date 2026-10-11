package com.cibertec.msresolucion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ResolucionRequest(
		@NotNull Integer estado,
		@NotBlank @Size(max = 100) String nombreAnalista
) {
}
