package com.meva.finance.dto;


import com.meva.finance.model.Family;
import lombok.*;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor // Gera um construtor com todos os atributos da classe
@NoArgsConstructor // Gera um construtor sem argumentos
@Data
public class FamilyDto {
    @NotNull
    @NotEmpty
    private Long idFamily;
    @NotNull
    @NotEmpty
    private String description;

    // Construtor que aceita um objeto Family
    public FamilyDto(Family family) {
        this.idFamily = family.getIdFamily();
        this.description = family.getDescription();
    }

    // Método que converte um FamilyDto em um Family
    public Family converterFamily(){
        Family family = new Family(); // Cria uma nova instância de Family
        family.setIdFamily(idFamily); // Define o id da família com o id do DTO
        family.setDescription(description); // Define a descrição da família com a descrição do DTO
        return family; // Retorna a instância de Family
    }
}
