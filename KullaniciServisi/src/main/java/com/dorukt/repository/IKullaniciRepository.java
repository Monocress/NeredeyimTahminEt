package com.dorukt.repository;

import com.dorukt.repository.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IKullaniciRepository extends JpaRepository<Kullanici,Long> {

    Boolean existsByKullaniciAdi(String kullaniciAdi);

    Optional<Kullanici> findByKullaniciAdiAndSifre(String kullaniciAdi, String sifre);

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);
}
