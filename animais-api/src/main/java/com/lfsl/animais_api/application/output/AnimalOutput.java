package com.lfsl.animais_api.application.output;

import com.lfsl.animais_api.domain.Animal;

public record AnimalOutput(
        String id,
        String nome,
        String especie,
        String raca,
        Integer idade,
        String status
) {
    public static AnimalOutput from(Animal animal) {
        return new AnimalOutput(
                animal.getId().id().toString(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getIdade(),
                animal.getStatus().name()
        );
    }
}