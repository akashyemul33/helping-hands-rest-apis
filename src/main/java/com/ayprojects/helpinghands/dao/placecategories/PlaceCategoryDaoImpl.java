package com.ayprojects.helpinghands.dao.placecategories;

import com.ayprojects.helpinghands.models.DhPlaceCategories;
import com.ayprojects.helpinghands.repositories.PlaceCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor;

@Repository
public class PlaceCategoryDaoImpl implements PlaceCategoryDao {

    @Autowired
    PlaceCategoriesRepository placeCategoriesRepository;


    @Override
    public DhPlaceCategories add(DhPlaceCategories dhPlaceCategories) {
        return placeCategoriesRepository.save(dhPlaceCategories);
    }

    @Override
    public Optional<DhPlaceCategories> findByPlaceCategoryId(String placeCategoryId) {
        return placeCategoriesRepository.findByPlaceCategoryId(placeCategoryId);
    }

    @Override
    public Optional<DhPlaceCategories> findByStatus(String status) {
        return placeCategoriesRepository.findByStatus(status);
    }

    @Override
    public Optional<List<DhPlaceCategories>> findAllByStatus(String status) {
        return placeCategoriesRepository.findAllByStatus(status);
    }


}
