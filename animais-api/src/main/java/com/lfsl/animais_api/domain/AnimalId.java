package com.lfsl.animais_api.domain;

import org.springframework.util.Assert;

import java.util.UUID;

public record AnimalId(UUID id) {

    public AnimalId {
        Assert.notNull(id, "O identificador do animal não pode ser nulo");
    }

    public AnimalId() {
        this(UUID.randomUUID());
    }
}