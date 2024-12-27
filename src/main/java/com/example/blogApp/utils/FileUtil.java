package com.example.blogApp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class FileUtil {

    @Autowired
    private ResourceLoader resourceLoader;

    // Method to read a file from the resources folder
    public String readFileFromResources(String filename) throws IOException {
        // Getting the resource using the ResourceLoader
        Resource resource = resourceLoader.getResource("classpath:/user-data/" + filename);
        // Opening an InputStream to read the content of the resource
        InputStream inputStream = resource.getInputStream();
        // Creating a BufferedReader to read text from the InputStream efficiently
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // StringBuilder to accumulate the lines read from the file
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        // Reading each line from the file and appending it to the StringBuilder
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        // Closing the BufferedReader
        reader.close();
        // Returning the contents of the file as a string
        return stringBuilder.toString();
    }


}
