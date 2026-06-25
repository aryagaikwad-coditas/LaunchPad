package com.example.LaunchPad.service;

import com.example.LaunchPad.exceptions.AppException;
import com.example.LaunchPad.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {

    private final DocumentRepository documentRepository;

    public void uploadDocument(MultipartFile file) {
        if(file.isEmpty()) {
            throw new AppException("file is empty ");
        }
    }
}
