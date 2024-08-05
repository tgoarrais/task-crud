package com.meva.finance.exception;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FamilyNotFoundException extends RuntimeException{
    public FamilyNotFoundException(@NotNull @NotEmpty Long idFamily) {
        super("Id de Familia não encontrado");
    }
}
