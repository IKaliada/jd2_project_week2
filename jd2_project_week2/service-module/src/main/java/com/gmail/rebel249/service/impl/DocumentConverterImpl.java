package com.gmail.rebel249.service.impl;

import com.gmail.rebel249.repository.model.MyDocument;
import com.gmail.rebel249.service.DocumentConverter;
import com.gmail.rebel249.service.model.DocumentDTO;
import org.springframework.stereotype.Component;

@Component
public class DocumentConverterImpl implements DocumentConverter {

    @Override
    public MyDocument fromDocumentDTO(DocumentDTO documentDTO) {
        MyDocument myDocument = new MyDocument();
        myDocument.setUniqueNumber(documentDTO.getUniqueNumber());
        myDocument.setDescription(documentDTO.getDescription());
        return myDocument;
    }

    @Override
    public DocumentDTO toDocumentDTO(MyDocument myDocument) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(myDocument.getId());
        documentDTO.setUniqueNumber(myDocument.getUniqueNumber());
        documentDTO.setDescription(myDocument.getDescription());
        return documentDTO;
    }
}
