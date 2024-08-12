package com.project.ecommerce.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.ecommerce.exceptions.BadApiRequestionException;
import com.project.ecommerce.services.FileService;

@Service
public class FileServieImpl implements FileService {
  private Logger logger = LoggerFactory.getLogger(FileServieImpl.class);

  @Override
  public String uploadFile(MultipartFile file, String path) throws IOException {
    String originalFileName = file.getOriginalFilename();
    logger.info("File Name : {}", originalFileName);
    String fileName = UUID.randomUUID().toString();
    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    String fileNameWithExtension = fileName + fileExtension;
    String fullPathWithFileName = path + fileNameWithExtension;

    if (fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg")
        || fileExtension.equalsIgnoreCase(".jpeg"))

    {
      File folder = new File(path);
      if (!folder.exists()) {
        folder.mkdirs();
      }
      Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
      return fileNameWithExtension;

    } else {
      throw new BadApiRequestionException("File With " + fileExtension + " Not Allowed");
    }
  }

  @Override
  public InputStream getResource(String path, String name) throws FileNotFoundException {
    String fullpathname = path + File.separator + name;

    InputStream inputStream = new FileInputStream(fullpathname);
    return inputStream;
  }

}
