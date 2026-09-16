package com.lfsl.animais_api.application;

import com.lfsl.animais_api.application.input.CreateAnimalInput;
import com.lfsl.animais_api.application.output.AnimalOutput;
import com.lfsl.animais_api.domain.Animal;
import com.lfsl.animais_api.domain.AnimalRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateAnimalUseCase {

    private final AnimalRepository repository;

    public CreateAnimalUseCase(AnimalRepository repository) {
        this.repository = repository;
    }

    public AnimalOutput execute(CreateAnimalInput input) {
        var animal = new Animal(
                input.nome(),
                input.especie(),
                input.raca(),
                input.idade()
        );
        var saved = repository.save(animal);
        return AnimalOutput.from(saved);
    }
}