package com.randolph.sisyphus.dto;

/**
 * Page Query Parameters
 *
 * @author jacky
 * @author <a href = "mailto:randolph_lu@163.com">randolph<a/>
 * @since 5.0.0
 */
public interface DTOAssembler<D, E> {

    D toDTO(E entity);

    E toEntity(D dto);
}
