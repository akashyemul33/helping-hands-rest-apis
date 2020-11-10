package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPlaceCategories;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface PlaceRepository extends MongoRepository<DhPlace,String> {

}
