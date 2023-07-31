package com.dorukt.mapper;


import com.dorukt.dto.request.SaveRequestDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.repository.entity.Kullanici;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IKullaniciMapper {

    IKullaniciMapper INSTANCE = Mappers.getMapper(IKullaniciMapper.class);


    Kullanici toKullanici(SaveRequestDto dto);

    Kullanici toKullanici(UpdateRequestDto dto);
}
