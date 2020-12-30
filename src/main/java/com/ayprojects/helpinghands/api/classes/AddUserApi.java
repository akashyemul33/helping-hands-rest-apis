package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.http.HttpHeaders;

public class AddUserApi implements AddBehaviour<DhUser> {
    HttpHeaders httpHeaders;
    DhUser dhUser;
    String version;

    public AddUserApi(HttpHeaders httpHeaders, DhUser dhUser, String version) {
        this.httpHeaders=httpHeaders;
        this.dhUser = dhUser;
        this.version = version;
    }

    @Override
    public Response<DhUser> add() {
        //TODO whatever you want
        return null;
    }
}
