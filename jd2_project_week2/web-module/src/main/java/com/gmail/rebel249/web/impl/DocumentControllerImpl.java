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

    private static final String REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-" +
            "[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}";
    private static final Long EDGE = 100L;
    private static final String NOT_VALID_MESSAGE = "Not valid value!";
    private static final String DOCUMENT_NULL_MESSAGE = "Document is null!";

    private DocumentService documentService;

    @Autowired
    public DocumentControllerImpl(DocumentService appService) {
        this.documentService = appService;
    }

    @Override
    public DocumentDTO add(DocumentDTO documentDTO) {
        if (documentDTO == null){
            throw new NullDocumentDTOException(DOCUMENT_NULL_MESSAGE);
        }
        validate(documentDTO);
        documentDTO = documentService.add(documentDTO);
        validateReturningDocumentDTO(documentDTO);
        return documentDTO;
    }

    private void validate(DocumentDTO documentDTO) {
        if (documentDTO.getDescription().length() > EDGE
                || documentDTO.getUniqueNumber().toString().isEmpty()
                || !documentDTO.getUniqueNumber().toString().matches(REGEX)) {
            throw new NotValidValueException(NOT_VALID_MESSAGE);
        }
    }

    @Override
    public DocumentDTO getDocumentById(Long id) {
        validateId(id);
        DocumentDTO documentDTO = documentService.getDocumentById(id);
        validateReturningDocumentDTO(documentDTO);
        return documentDTO;
    }

    @Override
    public void delete(Long id) {
        validateId(id);
        documentService.delete(id);
    }

    private void validateId(Long id) {
        if (id == null) throw new NotValidValueException(NOT_VALID_MESSAGE);
    }

    private void validateReturningDocumentDTO(DocumentDTO documentDTO) {
        if (documentDTO == null) throw new NullDocumentDTOException(DOCUMENT_NULL_MESSAGE);
    }
}
