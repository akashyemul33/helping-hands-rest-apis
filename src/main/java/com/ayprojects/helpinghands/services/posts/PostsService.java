package com.ayprojects.helpinghands.services.posts;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhPlace;
import com.ayprojects.helpinghands.models.DhPosts;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface PostsService {
    Response<DhPosts> addPost(Authentication authentication, HttpHeaders httpHeaders, DhPosts dhPosts, String version) throws ServerSideException;
    Response<DhPosts> deletePost(Authentication authentication, HttpHeaders httpHeaders,String postId, String version) throws ServerSideException;
    Response<DhPosts> updatePost(Authentication authentication, HttpHeaders httpHeaders, DhPosts dhPosts, String version) throws ServerSideException;
    Response<DhPosts> getPosts(Authentication authentication, HttpHeaders httpHeaders, String searchValue, String version);
    Response<DhPosts> getPaginatedPosts(Authentication authentication, HttpHeaders httpHeaders,int page,int size, String version);
    Response<DhPosts> getPaginatedPostsByPlaceId(Authentication authentication, HttpHeaders httpHeaders,int page,int size,String placeId, String version);

}
