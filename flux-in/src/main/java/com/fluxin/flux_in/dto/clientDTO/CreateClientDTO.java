package com.fluxin.flux_in.dto.clientDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateClientDTO(
        Long id,
        @NotBlank(message = "Preencha o nome do cliente.")
        String name,
        @NotBlank(message = "Preencha o telefone do cliente.")
        @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$", message = "Telefone deve estar no formato (xx) xxxxx-xxxx")
        String telephone) {
}
