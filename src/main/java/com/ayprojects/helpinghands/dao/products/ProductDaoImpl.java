package com.ayprojects.helpinghands.dao.products;

import com.ayprojects.helpinghands.models.DhProduct;
import com.ayprojects.helpinghands.repositories.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao{

    @Autowired
    ProductsRepository productsRepository;
    @Override
    public Optional<DhProduct> findByProductNameProductnameInEnglish(String productnameInEnglish) {
        return productsRepository.findByProductNameProductnameInEnglish(productnameInEnglish);
    }
}
