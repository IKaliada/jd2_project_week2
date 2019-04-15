package com.gmail.rebel249.web.impl;

import com.gmail.rebel249.service.DocumentService;
import com.gmail.rebel249.service.model.DocumentDTO;
import com.gmail.rebel249.web.DocumentController;
import com.gmail.rebel249.web.exception.NotValidValueException;
import com.gmail.rebel249.web.exception.NullDocumentDTOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class DocumentControllerTest {

    private static final Logger logger = LogManager.getLogger(DocumentControllerTest.class);

    private DocumentController documentController;
    @Mock
    private DocumentService documentService;

    @Before
    public void init() {
        documentController = new DocumentControllerImpl(documentService);
    }

    @Test
    public void shouldReturnSameDocumentAsExpected() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(1L);
        documentDTO.setDescription("description");
        documentDTO.setUniqueNumber(UUID.fromString("6a2f41a3-c54c-fce8-32d2-0324e1c32e22"));
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(documentDTO);
        documentController.getDocumentById(1L);
    }

    @Test
    public void shouldReturnSameDocumentWhenAddToDatabase() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("some description");
        documentDTO.setUniqueNumber(UUID.fromString("6a2f41a3-c54c-fce8-32d2-0324e1c32e22"));

        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentDTO = documentController.add(documentDTO);
        Assert.assertEquals("some description", documentDTO.getDescription());
        Assert.assertEquals("6a2f41a3-c54c-fce8-32d2-0324e1c32e22", documentDTO.getUniqueNumber().toString());
    }

    @Test(expected = NullDocumentDTOException.class)
    public void shouldThrowNullDocumentDTOExceptionWhenArgumentIsNull() {
        documentController.add(null);
    }

    @Test(expected = NotValidValueException.class)
    public void shouldThrowNotValidValueException() {

        documentController.delete(null);
    }

    @Test(expected = NotValidValueException.class)
    public void shouldThrowNotValidValueExceptionWhenDescriptionLongerThan100Symbols() {
        String description = "some descriptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn" +
                "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setId(1L);
        documentDTO.setDescription(description);
        documentController.add(documentDTO);
    }

    @Test(expected = NullDocumentDTOException.class)
    public void shouldCatchNullPointerExceptionWhenGetDocumentById() {
        Mockito.when(documentService.getDocumentById(1L)).thenReturn(null);
        documentController.getDocumentById(1L);
    }

    @Test(expected = NullDocumentDTOException.class)
    public void shouldCatchNullPointerExceptionWhenReturningFromService() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription("description");
        Mockito.when(documentService.add(documentDTO)).thenReturn(null);
        documentController.add(documentDTO);
    }
}

