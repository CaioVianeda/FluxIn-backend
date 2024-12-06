package com.fluxin.flux_in.dto.establishmentDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEstablishmentDTO(
        @NotBlank(message = "Informe o nome.") String name,
        @NotBlank(message = "Informe o e-mail.")
        @Email(message = "Informe um e-mail válido.") String email) {
}
