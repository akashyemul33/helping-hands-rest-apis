package com.ayprojects.helpinghands.dao.placecategories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;

import java.util.List;
import java.util.Optional;

public interface PlaceCategoryDao {
    DhPlaceCategories add(DhPlaceCategories dhPlaceCategories);
    Optional<DhPlaceCategories> findByPlaceCategoryId(String placeCategoryId);
    Optional<DhPlaceCategories> findByStatus(String status);
    Optional<List<DhPlaceCategories>> findAllByStatus(String status);
}
