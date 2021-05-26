package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPromotions;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface PostsService {
    Response<DhPromotions> addPost(Authentication authentication, HttpHeaders httpHeaders, DhPromotions dhPromotions, String version) throws ServerSideException;
    Response<DhPromotions> deletePost(Authentication authentication, HttpHeaders httpHeaders, String postId, String version) throws ServerSideException;
    Response<DhPromotions> updatePost(Authentication authentication, HttpHeaders httpHeaders, DhPromotions dhPromotions, String version) throws ServerSideException;
    Response<DhPromotions> getPosts(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version);
    Response<DhPromotions> getPaginatedPosts(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String version);
    Response<DhPromotions> getPaginatedPostsByPlaceId(Authentication authentication, HttpHeaders httpHeaders, int page, int size, String placeId, String version);
}
