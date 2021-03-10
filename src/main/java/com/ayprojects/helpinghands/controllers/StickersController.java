package com.ayprojects.helpinghands.controllers;


import com.ayprojects.helpinghands.api.ApiOperations;
import com.ayprojects.helpinghands.api.enums.StrategyName;
import com.ayprojects.helpinghands.exceptions.ServerSideException;
import com.ayprojects.helpinghands.models.DhStickers;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(value = "User API's", description = "CRUD for stickers")
@RestController
@ResponseStatus
@RequestMapping("/api/v{version}/stickers")
public class StickersController {

    @Autowired
    ApiOperations<DhStickers> apiOperations;

    @PostMapping(value = "/addStickerPack")
    public ResponseEntity<Response<DhStickers>> addSticker(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @RequestBody DhStickers dhStickers, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.add(authentication, httpHeaders, dhStickers, StrategyName.AddStickerStrategy, version), HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllStickerPacks")
    ResponseEntity<Response<DhStickers>> getAllStickerPacks(@RequestHeader HttpHeaders httpHeaders, Authentication authentication, @PathVariable String version) throws ServerSideException {
        return new ResponseEntity<>(apiOperations.get(authentication, httpHeaders, StrategyName.GetStickersStrategy, null, version), HttpStatus.OK);
    }
}
