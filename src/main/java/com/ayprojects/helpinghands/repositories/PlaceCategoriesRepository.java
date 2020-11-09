package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceCategoriesRepository extends MongoRepository<DhPlaceCategories,String> {
    Optional<DhPlaceCategories> findByPlaceCategoryId(String placeCategoryId);
    Optional<DhPlaceCategories> findByStatus(String status);
    Optional<List<DhPlaceCategories>> findAllByStatus(String status);
    Optional<DhPlaceCategories> findByPlaceCategoryNamePlacecategorynameInEnglish(String placecategorynameInEnglish);
}
