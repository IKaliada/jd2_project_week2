package com.gmail.rebel249.service;

import com.gmail.rebel249.service.model.DocumentDTO;

public interface DocumentService {

    DocumentDTO add(DocumentDTO documentDTO);

    DocumentDTO getDocumentById(Long id);

    void delete(Long id);
}
