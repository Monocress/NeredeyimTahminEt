package com.dorukt.mapper;

import com.dorukt.dto.request.EkleRequestDto;
import com.dorukt.dto.request.UpdateRequestDto;
import com.dorukt.repository.entity.Sehir;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISehirMapper {

    ISehirMapper INSTANCE = Mappers.getMapper(ISehirMapper.class);


    Sehir toSehir(EkleRequestDto dto);
    Sehir toSehir(UpdateRequestDto dto);

}
