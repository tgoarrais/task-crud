package com.meva.finance.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user_meva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String cpf;
    private String name;
    private String genre;
    private LocalDate birth;
    private String state;
    private String city;
    @ManyToOne // Define a relação muitos-para-um com a entidade Family
    @JoinColumn(name = "id_family") // Especifica a coluna de chave estrangeira na tabela user_meva
    @JsonIgnore // Ignora o campo `family` durante a serialização
    private Family family;
}
