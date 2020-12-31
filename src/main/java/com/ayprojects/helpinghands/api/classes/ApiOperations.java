package com.ayprojects.helpinghands.api.classes;

import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.api.behaviours.UploadBehaviour;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhUser;
import com.ayprojects.helpinghands.models.Response;

import org.springframework.stereotype.Service;

@Service
public class ApiOperations<T> {
    AddBehaviour<T> addBehaviour;
    UploadBehaviour uploadBehaviour;
    T model;

    public void setModel(T model) {
        this.model = model;
    }

    public Response<T> add(){
        return addBehaviour.add();
    }

    public AddBehaviour<T> getAddBehaviour() {
        return addBehaviour;
    }

    public void setAddBehaviour(AddBehaviour<T> addBehaviour) {
        this.addBehaviour = addBehaviour;
    }

    public UploadBehaviour getUploadBehaviour() {
        return uploadBehaviour;
    }

    public void setUploadBehaviour(UploadBehaviour uploadBehaviour) {
        this.uploadBehaviour = uploadBehaviour;
    }
}
