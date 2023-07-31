package com.dorukt.service;

import com.dorukt.dto.request.FeignSkorRequestDto;
import com.dorukt.manager.IKullaniciManager;
import com.dorukt.mapper.ISkorMapper;
import com.dorukt.rabbitmq.model.SkorModel;
import com.dorukt.repository.ISkorRepository;
import com.dorukt.repository.entity.Skor;
import com.dorukt.utility.JwtTokenManager;
import com.dorukt.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkorService extends ServiceManager<Skor,Long> {

    private final ISkorRepository skorRepository;

    private final IKullaniciManager kullaniciManager;
    private final JwtTokenManager tokenManager;
    public SkorService(ISkorRepository skorRepository, IKullaniciManager kullaniciManager, JwtTokenManager tokenManager){
        super(skorRepository);
        this.skorRepository = skorRepository;
        this.kullaniciManager = kullaniciManager;
        this.tokenManager = tokenManager;
    }

    public void guncelle(SkorModel model) {
        Skor skor = ISkorMapper.INSTANCE.toSkor(model);
        Optional<Skor> usersSkor = skorRepository.findByUserId(skor.getUserId());
        if(usersSkor.isEmpty())
            save(skor);
        else{
            usersSkor.get().setSkor(usersSkor.get().getSkor()+ skor.getSkor());
            skor = update(usersSkor.get());
        }
        kullaniciManager.updateScore(FeignSkorRequestDto.builder().skor(skor.getSkor()).userId(skor.getUserId()).build());
    }

    /**
     * En yüksek skora sahip 5 kullanıcıyı skora göre sıralayıp döner
     * @return Top 5 skor.
     */
    public Page<Skor>findTop5HighScore() {
        Sort sort = Sort.by(Sort.Direction.fromString("DESC"),"skor");
        Pageable pageable = PageRequest.of(0,5,sort);
        return skorRepository.findAll(pageable);
    }
}
