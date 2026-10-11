package pe.cibertec.reclamos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ReclamoRequest(
        @NotBlank(message = "El nombre del cliente no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
        String nomcliente,

        @NotBlank(message = "Los últimos 4 dígitos de la tarjeta son obligatorios")
        @Pattern(regexp = "^\\d{4}$", message = "Debe contener exactamente 4 dígitos numéricos")
        String ultimos4tarjeta,

        @NotBlank(message = "El motivo no puede estar vacío")
        @Size(max = 255, message = "El motivo no puede superar los 255 caracteres")
        String motivo
) {}
