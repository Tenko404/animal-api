package com.lfsl.animais_api.infrastructure.http;

import com.lfsl.animais_api.application.*;
import com.lfsl.animais_api.domain.AnimalId;
import com.lfsl.animais_api.infrastructure.http.request.CreateAnimalRequest;
import com.lfsl.animais_api.infrastructure.http.request.UpdateAnimalRequest;
import com.lfsl.animais_api.infrastructure.http.response.AnimalResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/animais")
public class AnimalController {

    private final CreateAnimalUseCase createAnimalUseCase;
    private final ListAnimalsUseCase listAnimalsUseCase;
    private final GetAnimalByIdUseCase getAnimalByIdUseCase;
    private final UpdateAnimalUseCase updateAnimalUseCase;
    private final DeleteAnimalUseCase deleteAnimalUseCase;

    public AnimalController(
            CreateAnimalUseCase createAnimalUseCase,
            ListAnimalsUseCase listAnimalsUseCase,
            GetAnimalByIdUseCase getAnimalByIdUseCase,
            UpdateAnimalUseCase updateAnimalUseCase,
            DeleteAnimalUseCase deleteAnimalUseCase) {
        this.createAnimalUseCase = createAnimalUseCase;
        this.listAnimalsUseCase = listAnimalsUseCase;
        this.getAnimalByIdUseCase = getAnimalByIdUseCase;
        this.updateAnimalUseCase = updateAnimalUseCase;
        this.deleteAnimalUseCase = deleteAnimalUseCase;
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> create(
            @RequestBody @Valid CreateAnimalRequest request) {
        var input = request.toInput();
        var output = createAnimalUseCase.execute(input);
        var response = AnimalResponse.from(output);
        var location = URI.create("/animais/" + output.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<AnimalResponse> list() {
        return listAnimalsUseCase.execute()
                .stream()
                .map(AnimalResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public AnimalResponse getById(@PathVariable UUID id) {
        var output = getAnimalByIdUseCase.execute(new AnimalId(id));
        return AnimalResponse.from(output);
    }

    @PutMapping("/{id}")
    public AnimalResponse update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateAnimalRequest request) {
        var output = updateAnimalUseCase.execute(
                new AnimalId(id),
                request.toInput()
        );
        return AnimalResponse.from(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteAnimalUseCase.execute(new AnimalId(id));
        return ResponseEntity.noContent().build();
    }
}