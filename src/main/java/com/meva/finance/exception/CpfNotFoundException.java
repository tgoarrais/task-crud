package com.meva.finance.exception;

public class CpfNotFoundException extends RuntimeException{
    public CpfNotFoundException(String cpf) {
        super("Cpf não encontrado.");
    }
}
