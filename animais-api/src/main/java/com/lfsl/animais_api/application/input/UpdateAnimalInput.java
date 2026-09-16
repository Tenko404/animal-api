package com.lfsl.animais_api.application.input;

import com.lfsl.animais_api.domain.AnimalStatus;

public record UpdateAnimalInput(
        String nome,
        String especie,
        String raca,
        Integer idade,
        AnimalStatus status
) {
}