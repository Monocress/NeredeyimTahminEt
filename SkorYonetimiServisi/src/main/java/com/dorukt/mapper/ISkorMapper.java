package com.dorukt.mapper;

import com.dorukt.rabbitmq.model.SkorModel;
import com.dorukt.repository.entity.Skor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ISkorMapper {

    ISkorMapper INSTANCE = Mappers.getMapper(ISkorMapper.class);

    Skor toSkor(SkorModel model);
}
