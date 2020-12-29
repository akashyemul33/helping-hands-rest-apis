package com.ayprojects.helpinghands.api.behaviours;

import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;

import java.util.List;

public interface GetBehaviour {
    List<AllCommonUsedAttributes> getAllObjectsIrrespectiveOfStatus(int version);
    List<AllCommonUsedAttributes> getAllObjectsWithStatus(String status,int version);
    List<AllCommonUsedAttributes> getActiveObjectsWithPagination(int size,int pageNumber,int version);
    AllCommonUsedAttributes getSingleObjById(String objId,int version);
}
