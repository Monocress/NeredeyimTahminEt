package com.dorukt.controller;

import static com.dorukt.constants.EndPoints.*;

import com.dorukt.dto.request.FeignSkorRequestDto;
import com.dorukt.dto.request.LoginRequestDto;
import com.dorukt.dto.request.SaveRequestDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.repository.entity.Kullanici;
import com.dorukt.service.KullaniciService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(KULLANICI)
public class KullaniciController {

    private final KullaniciService kullaniciService;


    @Operation(summary = "Kullanıcı Kayıt işlemi", description = "Veritabanına kullanıcı kaydı yapar.")
    @PostMapping(KAYIT)
    public ResponseEntity<String> kaydet(@RequestBody SaveRequestDto dto){
        kullaniciService.kaydet(dto);
        return ResponseEntity.ok("Kayıt başarılı.");
    }


    @Operation(summary = "Kullanıcı Girişi işlemi", description = "Giriş işlemi yapar ve sorunsuzsa token döner.")
    @PostMapping(GIRIS)
    public ResponseEntity<String> girisYap(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(kullaniciService.girisYap(dto));
    }


    @Operation(summary = "Tüm kullanıcıları listele", description = "Tüm kullanıcıları listeler.")
    @GetMapping(LISTELE)
    public ResponseEntity<List<Kullanici>> findAll(){
      return ResponseEntity.ok(kullaniciService.findAll());
    }

    @Operation(summary = "Bilgileri getir.", description = "Login karşılığında verilen token kullanılırsa kullanıcı bilgilerini döner.")
    @GetMapping(GETIR)
    public ResponseEntity<Kullanici> bilgileriGetir(@RequestParam String token){
      return ResponseEntity.ok(kullaniciService.findKullaniciFromToken(token));
    }



    @Operation(summary = "Kullanıcı Güncelleme işlemi", description = "Kullanıcıyı günceller.")
    @PutMapping(UPDATE)
    public ResponseEntity<String> updateKullanici(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(kullaniciService.updateKullanici(dto));
    }


    @Operation(summary = "Skor-Microservice Metodu.", description = "Microserviceden gelen bilgiler doğrultusunda kullanıcı skorunu günceller.")
    @PostMapping(UPDATESKOR)
    public ResponseEntity<Boolean> updateScore(@RequestBody FeignSkorRequestDto token){
        return ResponseEntity.ok(kullaniciService.updateSkor(token));
    }

}
