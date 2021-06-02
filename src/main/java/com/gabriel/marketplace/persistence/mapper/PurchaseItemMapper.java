package com.gabriel.marketplace.persistence.mapper;

import com.gabriel.marketplace.domain.PurchaseItem;
import com.gabriel.marketplace.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

//uses key let's the mapper know that it uses that class inside the category mapping
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    @Mappings({
            @Mapping(source = "id.idProducto", target = "productId"),
            @Mapping(source = "cantidad", target = "quantity"),
            @Mapping(source = "estado", target = "active")

    })
    PurchaseItem toPurchaseItem(ComprasProducto producto);

    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "compra", ignore=true),
            @Mapping(target = "producto", ignore=true),
            @Mapping(target = "id.idCompra", ignore=true),
    })
    ComprasProducto toComprasProducto(PurchaseItem item);
}
