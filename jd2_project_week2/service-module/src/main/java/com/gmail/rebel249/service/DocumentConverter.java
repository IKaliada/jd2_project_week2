package com.gmail.rebel249.service;

import com.gmail.rebel249.repository.model.MyDocument;
import com.gmail.rebel249.service.model.DocumentDTO;

public interface DocumentConverter {

    MyDocument fromDocumentDTO(DocumentDTO documentDTO);
    DocumentDTO toDocumentDTO(MyDocument myDocument);
}
