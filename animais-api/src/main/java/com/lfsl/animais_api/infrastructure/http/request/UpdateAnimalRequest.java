package com.lfsl.animais_api.infrastructure.http.request;

import com.lfsl.animais_api.application.input.UpdateAnimalInput;
import com.lfsl.animais_api.domain.AnimalStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateAnimalRequest(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 2, max = 100, message = "O nome deve possuir entre 2 e 100 caracteres")
        String nome,

        @NotBlank(message = "A espécie é obrigatória")
        @Size(min = 2, max = 50, message = "A espécie deve possuir entre 2 e 50 caracteres")
        String especie,

        @NotBlank(message = "A raça é obrigatória")
        @Size(min = 2, max = 50, message = "A raça deve possuir entre 2 e 50 caracteres")
        String raca,

        @NotNull(message = "A idade é obrigatória")
        @PositiveOrZero(message = "A idade não pode ser negativa")
        @Max(value = 30, message = "A idade deve ser no máximo 30")
        Integer idade,

        @NotNull(message = "O status é obrigatório")
        AnimalStatus status
) {
    public UpdateAnimalInput toInput() {
        return new UpdateAnimalInput(nome, especie, raca, idade, status);
    }
}