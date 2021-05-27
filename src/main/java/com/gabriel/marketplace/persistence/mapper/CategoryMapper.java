package com.gabriel.marketplace.persistence.mapper;

import com.gabriel.marketplace.domain.Category;
import com.gabriel.marketplace.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//For integration with Spring we add the componentModel key
@Mapper(componentModel = "Spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active")
    })
    Category toCategory(Categoria categoria);

    //This is the inverse of what we have
    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true) // to let the mapper know that we're not mapping productos
    Categoria toCategory(Category category);


}
