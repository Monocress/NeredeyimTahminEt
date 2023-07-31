package com.dorukt.exception;

import lombok.Getter;

@Getter
public class SkorManagerException extends RuntimeException{
    private final ErrorType errorType;

    public SkorManagerException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType=errorType;
    }
    public SkorManagerException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType=errorType;
    }

}
