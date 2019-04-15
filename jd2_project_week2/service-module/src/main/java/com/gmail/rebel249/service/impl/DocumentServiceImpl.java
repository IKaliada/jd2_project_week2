package com.gmail.rebel249.service.impl;

import com.gmail.rebel249.repository.DocumentRepository;
import com.gmail.rebel249.repository.model.MyDocument;
import com.gmail.rebel249.service.DocumentConverter;
import com.gmail.rebel249.service.DocumentService;
import com.gmail.rebel249.service.model.DocumentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);

    private DocumentRepository appRepository;
    private DocumentConverter documentConverter;

    public DocumentServiceImpl(DocumentRepository appRepository,
                               DocumentConverter documentConverter) {
        this.appRepository = appRepository;
        this.documentConverter = documentConverter;

    }

    public DocumentDTO add(DocumentDTO documentDTO) {

        MyDocument myDocument = appRepository.add(documentConverter.fromDocumentDTO(documentDTO));
        DocumentDTO documentDTO1 = documentConverter.toDocumentDTO(myDocument);
        logger.info("DTO in service " + documentDTO1);
        return documentDTO1;
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        MyDocument myDocument = appRepository.getDocumentById(id);
        return documentConverter.toDocumentDTO(myDocument);
    }

    @Override
    public void delete(Long id) {
        appRepository.delete(id);
    }
}
