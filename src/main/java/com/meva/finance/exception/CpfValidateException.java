package com.meva.finance.exception;

public class CpfValidateException extends RuntimeException {
    public CpfValidateException(String cpf){
        super("Cpf já existe");
    }
}
