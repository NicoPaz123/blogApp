package com.example.blogApp.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.blogApp.service.FileService;
@Service
public class FileServiceImpl implements FileService{

    @Override
    public String addFileToPost(String path, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString()+" "+fileName.substring(fileName.indexOf("."));
        String newFilePath = path+File.separator+uniqueFileName;
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        Files.copy(file.getInputStream(),Paths.get(newFilePath));
        return uniqueFileName;
    }

    @Override
    public InputStream getFilesForAPost(String path, String imageName) throws FileNotFoundException {
        String filePath = path+File.separator+imageName;
        InputStream inputStream = new FileInputStream(filePath);
        return inputStream;
    }

}
