package com.dorukt.controller;

import com.dorukt.dto.request.TahminYapRequestDto;
import com.dorukt.service.TahminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dorukt.constants.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(TAHMIN)
public class TahminController {

    private final TahminService tahminService;

    @Operation(summary = "Tahmin Yap", description = "Login işlemi tokeni, Blurry resim ismi ve kullanıcı cevabı ile tahmin yapılmasını sağlar.")
    @PostMapping(YAP)
    public ResponseEntity<String> yap(@RequestBody TahminYapRequestDto dto) {
            return ResponseEntity.ok(tahminService.yap(dto));
    }


}
