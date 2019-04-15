package com.gmail.rebel249.web;

import com.gmail.rebel249.service.model.DocumentDTO;

public interface DocumentController {
    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
