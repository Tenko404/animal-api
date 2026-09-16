package com.lfsl.animais_api.application;

import com.lfsl.animais_api.domain.AnimalId;
import com.lfsl.animais_api.domain.AnimalNotFoundException;
import com.lfsl.animais_api.domain.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteAnimalUseCase {

    private final AnimalRepository repository;

    public DeleteAnimalUseCase(AnimalRepository repository) {
        this.repository = repository;
    }

    public void execute(AnimalId id) {
        if (repository.findById(id).isEmpty()) {
            throw new AnimalNotFoundException(id);
        }
        repository.delete(id);
    }
}