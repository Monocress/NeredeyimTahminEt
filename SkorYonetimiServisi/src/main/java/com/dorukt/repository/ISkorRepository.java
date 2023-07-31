package com.dorukt.repository;

import com.dorukt.repository.entity.Skor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ISkorRepository extends JpaRepository<Skor,Long> {

    Optional<Skor> findByUserId(Long userId);
}
