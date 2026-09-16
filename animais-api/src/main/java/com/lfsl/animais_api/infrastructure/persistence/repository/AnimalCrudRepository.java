package com.lfsl.animais_api.infrastructure.persistence.repository;

import com.lfsl.animais_api.infrastructure.persistence.entity.AnimalEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AnimalCrudRepository
        extends CrudRepository<AnimalEntity, UUID> {
}