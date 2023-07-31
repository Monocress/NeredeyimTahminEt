package com.dorukt.repository;

import com.dorukt.repository.entity.Sehir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISehirRepository extends JpaRepository<Sehir,Long> {


    Boolean existsByAdAndUlke(String ad,String ulke);

    Optional<Sehir> findByUlkeResimBlurred(String blurred);

    Optional<Sehir>  findByAdAndUlke(String ad, String ulke);
}
