package com.dorukt.manager;


import com.dorukt.dto.request.ManagerTransferDto;
import com.dorukt.repository.entity.Sehir;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:7072/sehir",decode404 = true,name = "tahmin-to-sehir")
public interface ISehirManager {

    @PostMapping("/cevap")
    ResponseEntity<Sehir> findCevapFromUlkeResim(@RequestBody ManagerTransferDto dto);
}
