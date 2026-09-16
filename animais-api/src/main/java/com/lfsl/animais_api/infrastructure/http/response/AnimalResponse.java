package com.lfsl.animais_api.infrastructure.http.response;

import com.lfsl.animais_api.application.output.AnimalOutput;

public record AnimalResponse(
        String id,
        String nome,
        String especie,
        String raca,
        Integer idade,
        String status
) {
    public static AnimalResponse from(AnimalOutput output) {
        return new AnimalResponse(
                output.id(),
                output.nome(),
                output.especie(),
                output.raca(),
                output.idade(),
                output.status()
        );
    }
}