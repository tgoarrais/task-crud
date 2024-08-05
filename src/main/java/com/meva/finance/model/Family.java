package com.meva.finance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity // Indica que esta classe é uma entidade JPA (Java Persistence API)
@Table(name = "family") // Especifica o nome da tabela no banco de dados que esta entidade representa
@Getter // Gera automaticamente métodos getters para todos os campos da classe
@Setter // Gera automaticamente métodos setters para todos os campos da classe
@AllArgsConstructor // Gera automaticamente um construtor com todos os argumentos
@NoArgsConstructor // Gera automaticamente um construtor sem argumentos
public class Family {

    @Id // Indica que o campo 'idFamily' é a chave primária desta entidade
    @SequenceGenerator(name = "id_family", sequenceName = "id_family", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_family")//Essas anotações em conjunto garantem que o campo idFamily seja
    // automaticamente gerado pelo banco de dados usando uma sequência, proporcionando IDs únicos para cada entrada na tabela family.
    @Column(name = "id_family") // Mapeia o campo 'idFamily' para a coluna 'id_family' no banco de dados
    private Long idFamily; // Campo para armazenar o ID único da família

    @NotNull // Especifica que o campo 'description' não pode ser nulo
    @NotEmpty // Especifica que o campo 'description' não pode ser vazio
    private String description; // Campo para armazenar a descrição da família
}
