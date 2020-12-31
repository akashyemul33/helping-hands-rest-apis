package com.ayprojects.helpinghands.util.aws.amazon_client;

import com.ayprojects.helpinghands.util.aws.AmazonClient;

import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class ConvertMultipartFileTest {

    @Autowired
     AmazonClient amazonClient;

    @Test
    void givenNullFileWhenConvertMultipartFileThenException() {
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.convertMultipartFile(null);
            }
        });
    }

    @Test
    void givenEmptyObjWhenConvertMultipartFileThenException() {
        MultipartFile multipartFile = new MockMultipartFile("abc", (byte[]) null);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.convertMultipartFile(multipartFile);
            }
        });
    }

    @Test
    void givenValidFileWhenConvertMultipartFileThenNotNullFile() throws IOException {
        InputStream inputStream = new FileInputStream("/home/ay/Desktop/delta_counts_2309.txt");
        MultipartFile multipartFile = new MockMultipartFile("adf", "/home/ay/Desktop/abc.txt", String.valueOf(ContentType.APPLICATION_JSON), inputStream);
        assertNotNull(amazonClient.convertMultipartFile(multipartFile));
    }

 /*Not needed,
 as MockMultipartFile() method throws Name must not be null exception*/
    /*
    @Test
    void givenEmptyFileNameWhenConvertMultipartFileThenException(){
        MultipartFile multipartFile = new MockMultipartFile("", (byte[]) null);
        assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                amazonClient.convertMultipartFile(multipartFile);
            }
        });
    }
*/
}
