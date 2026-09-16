package com.lfsl.animais_api.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Animal {

    private final AnimalId id;
    private String nome;
    private String especie;
    private String raca;
    private Integer idade;
    private AnimalStatus status;


    public Animal(String nome, String especie, String raca, Integer idade) {
        validate(nome, especie, raca, idade);
        this.id = new AnimalId();
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.status = AnimalStatus.DISPONIVEL;
    }

    public Animal(AnimalId id, String nome, String especie, String raca,
                  Integer idade, AnimalStatus status) {
        Assert.notNull(id, "O identificador do animal não pode ser nulo");
        validate(nome, especie, raca, idade);
        Assert.notNull(status, "O status do animal não pode ser nulo");
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.status = status;
    }

    public void update(String nome, String especie, String raca,
                       Integer idade, AnimalStatus status) {
        validate(nome, especie, raca, idade);
        Assert.notNull(status, "O status do animal não pode ser nulo");
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.status = status;
    }

    private static void validate(String nome, String especie, String raca,
                                 Integer idade) {
        Assert.hasText(nome, "O nome não pode estar vazio");
        Assert.isTrue(nome.length() >= 2 && nome.length() <= 100,
                "O nome deve possuir entre 2 e 100 caracteres");
        Assert.hasText(especie, "A espécie não pode estar vazia");
        Assert.isTrue(especie.length() >= 2 && especie.length() <= 50,
                "A espécie deve possuir entre 2 e 50 caracteres");
        Assert.hasText(raca, "A raça não pode estar vazia");
        Assert.isTrue(raca.length() >= 2 && raca.length() <= 50,
                "A raça deve possuir entre 2 e 50 caracteres");
        Assert.notNull(idade, "A idade não pode ser nula");
        Assert.isTrue(idade >= 0 && idade <= 30,
                "A idade deve estar entre 0 e 30 anos");
    }
}