package com.gabriel.marketplace.persistence.crud;

import com.gabriel.marketplace.persistence.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {

    // Native Query
    @Query(value= "SELECT * FROM productos WHERE id_catogoria = ?", nativeQuery = true)
    List<Producto> getProductoBy(int idCategoria);

    // Query Methods
    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);
}
