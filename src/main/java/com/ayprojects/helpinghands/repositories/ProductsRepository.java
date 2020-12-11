package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

public interface ProductsRepository extends MongoRepository<DhProduct,String> {
    Optional<DhProduct> findByDefaultName(String defaultName);
}
