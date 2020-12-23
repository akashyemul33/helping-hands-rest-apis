package com.ayprojects.helpinghands.dao.products;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.models.DhProduct;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<DhProduct> findByDefaultName(String defaultName);
}
