package com.gabriel.marketplace.persistence;

import com.gabriel.marketplace.domain.Purchase;
import com.gabriel.marketplace.domain.repository.PurchaseRepository;
import com.gabriel.marketplace.persistence.crud.CompraCrudRepository;
import com.gabriel.marketplace.persistence.entity.Compra;
import com.gabriel.marketplace.persistence.mapper.PurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    @Autowired
    private CompraCrudRepository compraCrudRepository;

    @Autowired
    private PurchaseMapper mapper;

    @Override
    public List<Purchase> getAll() {
        List<Compra> myList1 = (List<Compra>)compraCrudRepository.findAll();
        List<Purchase> myList = mapper.toPurchases(myList1);
        return myList;
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(compras -> mapper.toPurchases(compras));
    }

    @Override
    public Purchase save(Purchase purchase) {
        Compra compra = mapper.toCompra(purchase);
        compra.getProductos().forEach(producto -> producto.setCompra(compra));

        return mapper.toPurchase(compraCrudRepository.save(compra));
    }
}
