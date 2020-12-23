package com.ayprojects.helpinghands.services.rating_comments;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRatingAndComments;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface RatingCommentsService {
    Response<DhRatingAndComments> addRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, DhRatingAndComments dhRatingComments, String version) throws ServerSideException;
    Response<DhRatingAndComments> getPaginatedRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version);
}
