package com.dorukt.repository;

import com.dorukt.repository.entity.Tahmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITahminRepository extends JpaRepository<Tahmin,Long> {

    List<Tahmin> findByUserIdAndDogruCevap(Long aLong, String cevap);
}
