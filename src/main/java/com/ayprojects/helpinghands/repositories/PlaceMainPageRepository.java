package com.ayprojects.helpinghands.repositories;

import com.ayprojects.helpinghands.models.PlaceMainPage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlaceMainPageRepository extends PagingAndSortingRepository<PlaceMainPage, Integer> {
    Page<PlaceMainPage> findAllByStatus(String status, Pageable pageRequest);
//    List<PlaceMainPage> findAllByStatus(String status);
}
