package com.ayprojects.helpinghands.api.api_operations_factory;

import com.ayprojects.helpinghands.api.ApiOperationsFactory;
import com.ayprojects.helpinghands.api.behaviours.AddBehaviour;
import com.ayprojects.helpinghands.models.AllCommonUsedAttributes;
import com.ayprojects.helpinghands.models.DhImages;
import com.ayprojects.helpinghands.models.DhUser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class getObjectMethodTest {

    @Test
    void givenNullKeyThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ApiOperationsFactory.getObject(null, null, null);
            }
        });
    }

    @Test
    void givenInvalidObjectThenException() {
        AllCommonUsedAttributes allCommonUsedAttributes = new DhImages();
        assertThrows(ClassNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                ApiOperationsFactory.getObject(null, null, allCommonUsedAttributes);
            }
        });
    }

    @Test
    void givenValidObjectThenException() throws ClassNotFoundException {
        AllCommonUsedAttributes allCommonUsedAttributes = new DhUser();
        AddBehaviour<DhUser> returnObj = (AddBehaviour<DhUser>) ApiOperationsFactory.getObject(null, null, allCommonUsedAttributes);
        assertNotNull(returnObj);
    }

}
