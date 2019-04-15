package com.gmail.rebel249.web.impl;


import com.gmail.rebel249.service.model.DocumentDTO;
import com.gmail.rebel249.web.DocumentController;
import com.gmail.rebel249.web.config.AppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class DocumentControllerIntegrationTest {
    private static final String DESCRIPTION = "Some description";

    @Autowired
    DocumentController documentController;

    @Test
    public void shouldReturnSameDocumentWhenAddToDatabase() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(DESCRIPTION);
        documentDTO.setUniqueNumber(UUID.randomUUID());

        DocumentDTO returnedDocumentDTO = documentController.add(documentDTO);
        Assert.assertNotNull(returnedDocumentDTO.getId());
        Assert.assertEquals(DESCRIPTION, returnedDocumentDTO.getDescription());
        Assert.assertEquals(documentDTO.getUniqueNumber(), returnedDocumentDTO.getUniqueNumber());
    }

    @Test
    public void shouldReturnSameDocumentWhenReturningDocumentById() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription(DESCRIPTION);
        documentDTO.setUniqueNumber(UUID.randomUUID());

        documentDTO = documentController.add(documentDTO);
        DocumentDTO documentById = documentController.getDocumentById(documentDTO.getId());
        Assert.assertEquals(DESCRIPTION, documentById.getDescription());
        Assert.assertEquals(documentDTO.getUniqueNumber(), documentById.getUniqueNumber());
    }
}
