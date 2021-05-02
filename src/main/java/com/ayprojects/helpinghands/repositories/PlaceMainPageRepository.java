package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.DhPlaceMainPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaceMainPageRepository extends PagingAndSortingRepository<DhPlaceMainPage, Integer> {
    Page<DhPlaceMainPage> findAllByStatus(String status, Pageable pageRequest);
//    List<PlaceMainPage> findAllByStatus(String status);
}
