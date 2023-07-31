package com.dorukt.service;

import com.dorukt.dto.request.EkleRequestDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.exception.ErrorType;
import com.dorukt.exception.SehirManagerException;
import com.dorukt.mapper.ISehirMapper;
import com.dorukt.repository.ISehirRepository;
import com.dorukt.repository.entity.Sehir;
import com.dorukt.utility.JwtTokenManager;
import com.dorukt.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SehirService extends ServiceManager<Sehir,Long> {

    private final ISehirRepository sehirRepository;

    private final JwtTokenManager tokenManager;
    public SehirService(ISehirRepository sehirRepository, JwtTokenManager tokenManager){
        super(sehirRepository);
        this.sehirRepository = sehirRepository;
        this.tokenManager = tokenManager;
    }

    public Sehir ekle(EkleRequestDto dto) {
        if(sehirRepository.existsByAdAndUlke(dto.getAd(),dto.getUlke()))
            throw new SehirManagerException(ErrorType.BAD_REQUEST,"Şehir zaten mevcut.");
        return save(ISehirMapper.INSTANCE.toSehir(dto));
    }


    /**
     * Sehir id'sine göre databasedeki Blur resim dosyasını döner.
     * @param sehirId Aranacak Id
     * @return Id'ye sahip Sehrin Blurlu resminin yolu. (Şu an için)
     */
    public String findBlurryPhoto(Long sehirId) {
        Optional<Sehir> sehir = findById(sehirId);
        if (sehir.isEmpty())
            throw new SehirManagerException(ErrorType.SEHIR_NOT_FOUND);
        return sehir.get().getUlkeResimBlurred();
    }

    /**
     * Tokenin doğru olup olmadığını kontrol eder ve geriye blurred resmi verilmiş şehrin bilgilerini döner.
     * @param userToken Longin karşılığı alınan token
     * @param ulkeResimBlurred  Sehir id'si verilerek alınan string değer.
     * @return Blurred resmin ait olduğu şehir bilgileri.
     */
    public Sehir findCevapFromUlkeResimBlurred(String userToken, String ulkeResimBlurred) {
        Optional<String> token = tokenManager.getUsernameFromToken(userToken);
        if(token.isEmpty())
            throw new SehirManagerException(ErrorType.INVALID_TOKEN);
        Optional<Sehir> sehir = sehirRepository.findByUlkeResimBlurred(ulkeResimBlurred);
        if(sehir.isEmpty())
            throw new SehirManagerException(ErrorType.BAD_REQUEST);
        return sehir.get();
    }

    public String guncelle(UpdateRequestDto dto) {
        Optional<Sehir> sehir = findById(dto.getId());
        Optional<Sehir> sehir2 = sehirRepository.findByAdAndUlke(dto.getAd(),dto.getUlke());
        if(sehir.isEmpty()){
            throw new SehirManagerException(ErrorType.SEHIR_NOT_FOUND);
        }
        if(sehir2.isPresent()&&!sehir2.get().getId().equals(sehir.get().getId())){
            throw new SehirManagerException(ErrorType.SEHIR_EXIST);
        }
        update(ISehirMapper.INSTANCE.toSehir(dto));
        return "Güncelleme işlemi başarılı";
    }

    public String deleteWithId(Long id) {
        if (!sehirRepository.existsById(id))
            throw new SehirManagerException(ErrorType.SEHIR_NOT_FOUND);
        deleteById(id);
        return "Silme işlemi tamamlandı.";

    }
}
