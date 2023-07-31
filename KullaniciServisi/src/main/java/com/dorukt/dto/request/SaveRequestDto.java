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
public class SaveRequestDto {
    @NotBlank(message = "Kullanıcı adı boş olamaz.")
    private String kullaniciAdi;
    @NotBlank(message = "Şifre boş olamaz.")
    private String sifre;
    @NotBlank(message = "Email boş olamaz.")
    //Kontrol kolaylığı için @Email kullanılmamıştır.
    private String email;
}
