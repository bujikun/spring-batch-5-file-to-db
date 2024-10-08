package dev.bujiku.batch_demo;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

/**
 * @author Newton Bujiku
 * @since 2024
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalesInfoMapper {

    SalesInfo mapToEntity(SalesInfoDTO salesInfoDTO);
}
