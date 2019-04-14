package com.gmail.rebel249.web.impl;

import com.gmail.rebel249.service.DocumentService;
import com.gmail.rebel249.service.model.DocumentDTO;
import com.gmail.rebel249.web.DocumentController;
import com.gmail.rebel249.web.exception.NotValidValueException;
import com.gmail.rebel249.web.exception.NullDocumentDTOException;
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
        documentDTO.setDescription("some description");
        documentDTO.setUniqueNumber(UUID.fromString("251abb33-3ff5-47b5-92f8-d0cc34c648b1"));
        Long id = 1L;

        Mockito.when(documentController.getDocumentById(id)).thenReturn(documentDTO);
        documentController.getDocumentById(id);
        Assert.assertEquals(1L, documentDTO.getId().longValue());
        Assert.assertEquals("some description", documentDTO.getDescription());
        Assert.assertEquals(UUID.fromString("251abb33-3ff5-47b5-92f8-d0cc34c648b1"), documentDTO.getUniqueNumber());
    }

    @Test
    public void shouldReturnSameDocumentWhenAddToDatabase() {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setDescription("some description");
        documentDTO.setUniqueNumber(UUID.fromString("6a2f41a3-c54c-fce8-32d2-0324e1c32e22"));

        Mockito.when(documentController.add(documentDTO)).thenReturn(documentDTO);
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
        Long id = 0L;
        documentController.delete(id);
    }

    @Test(expected = NotValidValueException.class)
    public void shouldThrowNotValidValueExceptionWhenDescriptionLongerThan100Symbols() {
        String description = "some descriptionnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn" +
                "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setId(1L);
        documentDTO.setDescription(description);
        Mockito.when(documentService.add(documentDTO)).thenReturn(documentDTO);
        documentController.add(documentDTO);
    }
}

