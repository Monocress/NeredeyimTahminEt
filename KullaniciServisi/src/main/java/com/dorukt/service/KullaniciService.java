package com.dorukt.service;

import com.dorukt.dto.request.FeignSkorRequestDto;
import com.dorukt.dto.request.LoginRequestDto;
import com.dorukt.dto.request.SaveRequestDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.exception.ErrorType;
import com.dorukt.exception.KullaniciManagerException;
import com.dorukt.mapper.IKullaniciMapper;
import com.dorukt.repository.IKullaniciRepository;
import com.dorukt.repository.entity.Kullanici;
import com.dorukt.utility.JwtTokenManager;
import com.dorukt.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KullaniciService extends ServiceManager<Kullanici, Long> {

    private final IKullaniciRepository kullaniciRepository;

    private final JwtTokenManager tokenManager;

    public KullaniciService(IKullaniciRepository kullaniciRepository, JwtTokenManager tokenManager) {
        super(kullaniciRepository);
        this.kullaniciRepository = kullaniciRepository;
        this.tokenManager = tokenManager;
    }


    public Kullanici kaydet(SaveRequestDto dto) {
        if (kullaniciRepository.existsByKullaniciAdi(dto.getKullaniciAdi()))
            throw new KullaniciManagerException(ErrorType.KULLANICI_EXIST);
        Kullanici kullanici = save(IKullaniciMapper.INSTANCE.toKullanici(dto));

        return kullanici;

    }

    public String girisYap(LoginRequestDto dto) {
        Optional<Kullanici> kullanici = kullaniciRepository.findByKullaniciAdiAndSifre(dto.getKullaniciAdi(), dto.getSifre());
        if (kullanici.isEmpty())
            throw new KullaniciManagerException(ErrorType.LOGIN_ERROR);
        Optional<String> token = tokenManager.createToken(kullanici.get().getId(), dto.getKullaniciAdi());
        if (token.isEmpty())
            throw new KullaniciManagerException(ErrorType.TOKEN_NOT_CREATED);
        return token.get();

    }

    /**
     * Verilen tokene uygun kullanıcıyı döner.
     * @param token
     * @return Kullanıcı tokene ait kullanıcı bilgileri
     */
    public Kullanici findKullaniciFromToken(String token) {

        Optional<Long> id = tokenManager.getIdFromUserToken(token);
        if (id.isEmpty())
            throw new KullaniciManagerException(ErrorType.INVALID_TOKEN);
        Optional<Kullanici> kullanici = findById(id.get());
        if (kullanici.isEmpty())
            throw new KullaniciManagerException(ErrorType.KULLANICI_NOT_FOUND);
        return kullanici.get();
    }

    /**
     * Feign clienttan gelen güncel skor bilgileriyle mevcut kullanıcı düzenler.
     * @param token Kullanıcının id ve skor bilgisini içerir.
     * @return Başarılıysa true döner.
     */
    public Boolean updateSkor(FeignSkorRequestDto token) {
        Long yeniSkor = token.getSkor();
        Long kullaniciId = token.getUserId();
        Optional<Kullanici> kullanici = findById(kullaniciId);
        if (kullanici.isEmpty())
            throw new KullaniciManagerException(ErrorType.KULLANICI_NOT_FOUND);
        kullanici.get().setSkor(yeniSkor);
        update(kullanici.get());
        return true;
    }


    public String updateKullanici(UpdateRequestDto dto) {
        Optional<Long> id = tokenManager.getIdFromUserToken(dto.getToken());
        if(id.isEmpty())
            throw new KullaniciManagerException(ErrorType.INVALID_TOKEN);
        Optional<Kullanici> kullanici = findById(id.get());
        if (kullanici.isEmpty())
            throw new KullaniciManagerException(ErrorType.KULLANICI_NOT_FOUND);
        Optional<Kullanici> kullanici2 = kullaniciRepository.findByKullaniciAdi(dto.getKullaniciAdi());
        if (kullanici2.isPresent() && !kullanici2.get().getId().equals(kullanici.get().getId()))
            throw new KullaniciManagerException(ErrorType.KULLANICI_EXIST);
        Kullanici tempKullanici = IKullaniciMapper.INSTANCE.toKullanici(dto);
        tempKullanici.setId(id.get());
        tempKullanici.setCreateDate(kullanici.get().getCreateDate());
        tempKullanici.setSkor(kullanici.get().getSkor());
        update(tempKullanici);
        return "Güncelleme başarılı.";
    }
}
