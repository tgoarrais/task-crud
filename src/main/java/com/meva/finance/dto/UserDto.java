package com.meva.finance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meva.finance.model.User;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    // Declaração dos atributos da classe UserDto
    @NotBlank
    private String name; // Nome do usuário

    @CPF
    private String cpf;  // CPF do usuário
    @NotBlank
    private String city; // Cidade do usuário
    @NotBlank
    private String state; // Estado do usuário

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birth;// Data de nascimento do usuário

    @Pattern(regexp = "(?i)[MF]", message = "Insira um gênero válido!")
    private String genre; // Gênero do usuário

    private FamilyDto familyDto; // Dados da família do usuário

    // Construtor que aceita um objeto User
    public UserDto(User user) {
        this.name = user.getName();
        this.cpf = user.getCpf();
        this.city = user.getCity();
        this.state = user.getState();
        this.birth = user.getBirth();
        this.genre = user.getGenre();
        this.familyDto = new FamilyDto(user.getFamily());
    }

    public User converterToUser() {
        User user = new User(); // Cria uma nova instância de User
        user.setName(name); // Define o nome do usuário
        user.setCpf(cpf); // Define o CPF do usuário
        user.setGenre(genre); // Define o gênero do usuário
        user.setCity(city); // Define a cidade do usuário
        user.setBirth(birth); // Define a data de nascimento do usuário
        user.setState(state); // Define o estado do usuário
        return user; // Retorna o objeto User criado
    }

    // Método para definir os dados da família do usuário
    public void setFamily(FamilyDto familyDto) {
        this.familyDto = familyDto;
    }
}
