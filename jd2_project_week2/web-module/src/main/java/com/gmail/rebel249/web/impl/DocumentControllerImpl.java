package com.gmail.rebel249.web.impl;

import com.gmail.rebel249.service.DocumentService;
import com.gmail.rebel249.service.model.DocumentDTO;
import com.gmail.rebel249.web.DocumentController;
import com.gmail.rebel249.web.exception.NotValidValueException;
import com.gmail.rebel249.web.exception.NullDocumentDTOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentControllerImpl implements DocumentController {

    private static final Logger logger = LogManager.getLogger(DocumentControllerImpl.class);
    private static final String REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-" +
            "[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";

    private DocumentService documentService;

    @Autowired
    public DocumentControllerImpl(DocumentService appService) {
        this.documentService = appService;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        validate(documentDTO);
        try {
            return documentService.add(documentDTO);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

    private DocumentDTO validate(DocumentDTO documentDTO) {
        if (documentDTO == null) {
            throw new NullDocumentDTOException();
        } else if (documentDTO.getDescription().length() < 100
                && !documentDTO.getUniqueNumber().toString().isEmpty()
                && documentDTO.getUniqueNumber().toString().matches(REGEX)) {
            return documentDTO;
        } else throw new NotValidValueException();
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        if (id != 0) {
            DocumentDTO documentDTO = documentService.getDocumentById(id);
            try {
                return documentDTO;
            } catch (NullPointerException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
            }
        } else throw new NotValidValueException();
    }

    @Override
    public void delete(Long id) {
        if (id != 0) {
            documentService.delete(id);
        } else throw new NotValidValueException();
    }
}
