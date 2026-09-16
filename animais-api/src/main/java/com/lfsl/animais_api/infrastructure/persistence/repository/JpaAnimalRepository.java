package com.lfsl.animais_api.infrastructure.persistence.repository;

import com.lfsl.animais_api.domain.Animal;
import com.lfsl.animais_api.domain.AnimalId;
import com.lfsl.animais_api.domain.AnimalRepository;
import com.lfsl.animais_api.infrastructure.persistence.entity.AnimalEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Repository
public class JpaAnimalRepository implements AnimalRepository {

    private final AnimalCrudRepository repository;

    public JpaAnimalRepository(AnimalCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Animal save(Animal animal) {
        var entity = toEntity(animal);
        var saved = repository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Animal> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Animal> findById(AnimalId id) {
        return repository.findById(id.id())
                .map(this::toDomain);
    }

    @Override
    public void delete(AnimalId id) {
        repository.deleteById(id.id());
    }

    private AnimalEntity toEntity(Animal animal) {
        return new AnimalEntity(
                animal.getId().id(),
                animal.getNome(),
                animal.getEspecie(),
                animal.getRaca(),
                animal.getIdade(),
                animal.getStatus()
        );
    }

    private Animal toDomain(AnimalEntity entity) {
        return new Animal(
                new AnimalId(entity.getId()),
                entity.getNome(),
                entity.getEspecie(),
                entity.getRaca(),
                entity.getIdade(),
                entity.getStatus()
        );
    }
}