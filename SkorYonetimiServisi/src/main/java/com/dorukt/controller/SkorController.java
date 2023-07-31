package com.dorukt.controller;

import com.dorukt.dto.request.TahminYapRequestDto;
import com.dorukt.manager.IKullaniciManager;
import com.dorukt.repository.entity.Skor;
import com.dorukt.service.SkorService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.dorukt.constants.EndPoints.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(SKOR)
public class SkorController {

    private final SkorService skorService;


    @Operation(summary = "En iyi 5", description = "Skora göre en yüksek skora sahip 5 kullanıcıyı döner.")
    @GetMapping(TOP5)
    public ResponseEntity<Page<Skor>> topSkor(){
        return ResponseEntity.ok(skorService.findTop5HighScore());
    }




}
