package com.lfsl.animais_api.domain;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {

    Animal save(Animal animal);

    List<Animal> findAll();

    Optional<Animal> findById(AnimalId id);

    void delete(AnimalId id);
}