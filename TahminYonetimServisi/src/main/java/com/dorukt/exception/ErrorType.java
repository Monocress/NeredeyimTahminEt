package com.dorukt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {

    INTERNAL_ERROR_SERVER(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4100, "Parametre hatası", HttpStatus.BAD_REQUEST),
    KULLANICI_NOT_CREATED(4111, "Kullanıcı oluşturulamadı!", HttpStatus.BAD_REQUEST),
    KULLANICI_NOT_FOUND(4112, "Kullanıcı bulunamadı!", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(4116, "Geçersiz token!", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatus status;
}
