package com.dorukt.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@AllArgsConstructor
@NoArgsConstructor
@Data //@Getter @Setter @ToString hepsini kapsıyor.
@SuperBuilder
@Entity
public class Sehir extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ad;
    private String ulke;
    private Double istikrarPuani;
    private Double saglikPuani;
    private Double kulturVeCevrePuani;
    private Double egitimPuani;
    private Double altyapiPuani;
    private String ulkeResimBlurred;
    private String ulkeResimClear;

}
