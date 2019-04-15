package com.gmail.rebel249.repository;

import com.gmail.rebel249.repository.model.MyDocument;

public interface DocumentRepository {

    MyDocument add(MyDocument myDocument);

    MyDocument getDocumentById(Long id);

    void delete(Long id);
}
