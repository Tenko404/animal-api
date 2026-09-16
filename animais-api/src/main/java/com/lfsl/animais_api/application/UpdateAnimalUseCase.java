package com.lfsl.animais_api.application;

import com.lfsl.animais_api.application.input.UpdateAnimalInput;
import com.lfsl.animais_api.application.output.AnimalOutput;
import com.lfsl.animais_api.domain.AnimalId;
import com.lfsl.animais_api.domain.AnimalNotFoundException;
import com.lfsl.animais_api.domain.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAnimalUseCase {

    private final AnimalRepository repository;

    public UpdateAnimalUseCase(AnimalRepository repository) {
        this.repository = repository;
    }

    public AnimalOutput execute(AnimalId id, UpdateAnimalInput input) {
        var animal = repository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));

        animal.update(
                input.nome(),
                input.especie(),
                input.raca(),
                input.idade(),
                input.status()
        );

        var updated = repository.save(animal);
        return AnimalOutput.from(updated);
    }
}