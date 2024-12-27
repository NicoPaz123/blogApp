package com.example.blogApp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String addFileToPost(String path,MultipartFile file) throws IOException;

    public InputStream getFilesForAPost(String path, String imageName) throws FileNotFoundException;
}
