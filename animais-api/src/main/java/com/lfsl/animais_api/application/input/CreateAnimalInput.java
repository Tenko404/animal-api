package com.lfsl.animais_api.application.input;

public record CreateAnimalInput(
        String nome,
        String especie,
        String raca,
        Integer idade
) {
}