package com.lfsl.animais_api.application;

import com.lfsl.animais_api.application.output.AnimalOutput;
import com.lfsl.animais_api.domain.AnimalId;
import com.lfsl.animais_api.domain.AnimalNotFoundException;
import com.lfsl.animais_api.domain.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class GetAnimalByIdUseCase {

    private final AnimalRepository repository;

    public GetAnimalByIdUseCase(AnimalRepository repository) {
        this.repository = repository;
    }

    public AnimalOutput execute(AnimalId id) {
        return repository.findById(id)
                .map(AnimalOutput::from)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }
}