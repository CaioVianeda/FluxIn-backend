package com.fluxin.flux_in.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateEmployeeInformationsDTO(@NotBlank(message = "Preencha o nome do funcionário.") String name,
                                            @NotBlank(message = "Preencha o telefone do funcionário.")
                                            @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-?\\d{4}$", message = "Telefone deve estar no formato (xx) xxxxx-xxxx")
                                            String telephone,
                                            @NotBlank(message = "Preencha o email do funcionário.") @Email(message = "Preencha um e-mail válido.") String email) {
}