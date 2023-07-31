package com.dorukt.controller;

import com.dorukt.dto.request.EkleRequestDto;
import com.dorukt.dto.request.ManagerTransferDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.repository.entity.Sehir;
import com.dorukt.service.SehirService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dorukt.constants.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SEHIR)
public class SehirController {

    private final SehirService sehirService;

    @Operation(summary = "Şehir Ekleme işlemi", description = "Şehir ekler.")
    @PostMapping(EKLE)
    public ResponseEntity<String> ekle(@RequestBody EkleRequestDto dto){
        Sehir sehir = sehirService.ekle(dto);
        return ResponseEntity.ok("Ekleme işlemi başarılı. Eklenen şehrin Şehir ID'si: "+sehir.getId());
    }


    @Operation(summary = "ID to Blurry Image", description = "ID'si verilen şehrin Blurlu resmini döndürür. Şu an için resim adını dönüyor.")
    @GetMapping(PVSEHIRID)
    public ResponseEntity<String> blurluSehirGetir(@PathVariable Long sehirId){
        return ResponseEntity.ok(sehirService.findBlurryPhoto(sehirId));
    }


    @Operation(summary = "TahminMicroServiceMetodu", description = "Tahmin Micro servisine cevap bilgisini döndürür.")
    @PostMapping(CEVAP)
    public ResponseEntity<Sehir> findCevapFromUlkeResim(@RequestBody ManagerTransferDto dto){
        return ResponseEntity.ok(sehirService.findCevapFromUlkeResimBlurred(dto.getUserToken(), dto.getUlkeResimBlurred()));
    }
    @Operation(summary = "Şehir güncelle", description = "Şehri günceller.")
    @PostMapping(UPDATE)
    public ResponseEntity<String> updateSehir(@RequestBody UpdateRequestDto dto){
        return ResponseEntity.ok(sehirService.guncelle(dto));
    }

    @Operation(summary = "Şehri sil", description = "ID'si verilen şehri siler.")
    @PostMapping(DELETE)
    public ResponseEntity<String> deleteSehir(@RequestBody Long id){
        return ResponseEntity.ok(sehirService.deleteWithId(id));
    }
}
