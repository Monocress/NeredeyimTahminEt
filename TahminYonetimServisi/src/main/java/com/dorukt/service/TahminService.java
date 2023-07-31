package com.dorukt.service;

import com.dorukt.dto.request.ManagerTransferDto;
import com.dorukt.dto.request.TahminYapRequestDto;
import com.dorukt.exception.ErrorType;
import com.dorukt.exception.TahminManagerException;
import com.dorukt.manager.ISehirManager;
import com.dorukt.rabbitmq.model.SkorModel;
import com.dorukt.rabbitmq.producer.SkorProducer;
import com.dorukt.repository.ITahminRepository;
import com.dorukt.repository.entity.Sehir;
import com.dorukt.repository.entity.Tahmin;
import com.dorukt.utility.JwtTokenManager;
import com.dorukt.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TahminService extends ServiceManager<Tahmin, Long> {

    private final ITahminRepository tahminRepository;

    private final ISehirManager sehirManager;

    private final JwtTokenManager tokenManager;

    private final SkorProducer skorProducer;


    public TahminService(ITahminRepository tahminRepository, ISehirManager sehirManager, JwtTokenManager tokenManager, SkorProducer skorProducer) {
        super(tahminRepository);
        this.tahminRepository = tahminRepository;
        this.sehirManager = sehirManager;
        this.tokenManager = tokenManager;
        this.skorProducer = skorProducer;
    }

    /**
     * Login işleminden alınan usertoken, ID verilerek alınan görsel Blur image yolu ve tahmini alır doğruysa 10 puan ekler yanlışsa 5 puan azaltır ve ip ucu verir.
     * @param dto Token,Cevap ve Blurry Image ismi içeren DTO
     * @return Durum bilgisini döner.
     */
    public String yap(TahminYapRequestDto dto) {

        Optional<String> username = tokenManager.getUsernameFromToken(dto.getUserToken());
        if (username.isEmpty())
            throw new TahminManagerException(ErrorType.INVALID_TOKEN);
        Optional<Long> id = tokenManager.getIdFromToken(dto.getUserToken());
        if (id.isEmpty())
            throw new TahminManagerException(ErrorType.INVALID_TOKEN);

        Sehir cevap = null;
        try {
            cevap = sehirManager.findCevapFromUlkeResim(ManagerTransferDto.builder().ulkeResimBlurred(dto.getUlkeResimBlurred()).userToken(dto.getUserToken()).build()).getBody();
        } catch (Exception e) {
            throw new TahminManagerException(ErrorType.BAD_REQUEST, "Hatalı Resim Girdiniz.");
        }
        List<Tahmin> tahminMiktar = tahminRepository.findByUserIdAndDogruCevap(id.get(), cevap.getAd());
        if (tahminMiktar.size() < 5) {
            save(Tahmin.builder().dogruCevap(cevap.getAd()).userId(id.get()).tahminCevap(dto.getCevap()).build());
            if (dto.getCevap().equalsIgnoreCase(cevap.getAd())) {
                skorProducer.skorUpdate(SkorModel.builder().userId(id.get()).kullaniciAdi(username.get()).skor(10L).build());
                return "Cevabınız doğru! 10 puan kazandınız!";
            }
            skorProducer.skorUpdate(SkorModel.builder().userId(id.get()).kullaniciAdi(username.get()).skor(-5L).build());
            return hataliCevapDondur(cevap, tahminMiktar);
        }
        return "Malesef bu resim için tahmin hakkınız kalmadı!";
    }

    private String hataliCevapDondur(Sehir cevap, List<Tahmin> tahminMiktar) {

        switch (new Random().nextInt(1,5)){
            case 1:
                return "Cevabınız yanlış! 5 puan kaybettiniz!" + (5 - tahminMiktar.size() - 1) + " tahmininiz kaldı! Belki bu bilgi işinizi kolaylaştırır: Bu şehir şu ülkede: " + cevap.getUlke();
            case 2:
                return "Cevabınız yanlış! 5 puan kaybettiniz!" + (5 - tahminMiktar.size() - 1) + " tahmininiz kaldı! Belki bu bilgi işinizi kolaylaştırır: Bu şehrin alt yapı puanı: " + cevap.getAltyapiPuani();
            case 3:
                return "Cevabınız yanlış! 5 puan kaybettiniz!" + (5 - tahminMiktar.size() - 1) + " tahmininiz kaldı! Belki bu bilgi işinizi kolaylaştırır: Bu şehrin eğitim puanı: " + cevap.getEgitimPuani();
            case 4:
                return "Cevabınız yanlış! 5 puan kaybettiniz!" + (5 - tahminMiktar.size() - 1) + " tahmininiz kaldı! Belki bu bilgi işinizi kolaylaştırır: Bu şehrin kültür ve çevre puanı: " + cevap.getKulturVeCevrePuani();
            default:
                return "Cevabınız yanlış! 5 puan kaybettiniz!";
        }
    }
}
