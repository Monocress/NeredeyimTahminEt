package com.dorukt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@Builder
public class EkleRequestDto {
    @NotBlank(message = "Şehir adı olmadan kayıt yapamazsınız.")
    private String ad;
    @NotBlank(message = "Ülke adı olmadan kayıt yapamazsınız.")
    private String ulke;
    private Double istikrarPuani;
    private Double saglikPuani;
    private Double kulturVeCevrePuani;
    private Double egitimPuani;
    private Double altyapiPuani;
    @NotBlank(message = "Şehrin bulanık fotoğrafı olmadan kayıt yapamazsınız.")
    private String ulkeResimBlurred;
    @NotBlank(message = "Şehrin net fotoğrafı olmadan kayıt yapamazsınız.")
    private String ulkeResimClear;

}
