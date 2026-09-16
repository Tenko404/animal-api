package com.lfsl.animais_api.domain;

public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException(AnimalId animalId) {
        super("Animal com identificador " + animalId.id() + " não encontrado");
    }
}