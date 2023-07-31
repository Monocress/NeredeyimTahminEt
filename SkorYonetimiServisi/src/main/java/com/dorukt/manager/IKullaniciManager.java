package com.dorukt.manager;


import com.dorukt.dto.request.FeignSkorRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.dorukt.constants.EndPoints.UPDATESKOR;


@FeignClient(url = "http://localhost:7071/kullanici",decode404 = true,name = "skor-to-kullanici")
public interface IKullaniciManager {


    @PostMapping(UPDATESKOR)
    ResponseEntity<Boolean> updateScore(@RequestBody FeignSkorRequestDto dto);


}

