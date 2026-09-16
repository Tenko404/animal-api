package com.lfsl.animais_api.application;

import com.lfsl.animais_api.application.output.AnimalOutput;
import com.lfsl.animais_api.domain.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAnimalsUseCase {

    private final AnimalRepository repository;

    public ListAnimalsUseCase(AnimalRepository repository) {
        this.repository = repository;
    }

    public List<AnimalOutput> execute() {
        return repository.findAll()
                .stream()
                .map(AnimalOutput::from)
                .toList();
    }
}