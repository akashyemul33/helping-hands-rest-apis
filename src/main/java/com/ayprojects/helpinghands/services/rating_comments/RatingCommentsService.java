package com.ayprojects.helpinghands.services.rating_comments;

import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhRating_comments;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface RatingCommentsService {
    Response<DhRating_comments> addRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, DhRating_comments dhRatingComments, String version) throws ServerSideException;
    Response<DhRating_comments> getPaginatedRatingAndComments(Authentication authentication, HttpHeaders httpHeaders, String contentId, String contentType, String status, int page, int size, String version);
}
