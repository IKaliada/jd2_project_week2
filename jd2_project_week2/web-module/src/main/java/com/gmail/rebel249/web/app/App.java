package com.gmail.rebel249.web.app;

import com.gmail.rebel249.service.model.DocumentDTO;
import com.gmail.rebel249.web.DocumentController;
import com.gmail.rebel249.web.config.AppConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(AppConfig.class);
        context.refresh();

        DocumentController documentController = context.getBean(DocumentController.class);
        DocumentDTO documentDTO = context.getBean(DocumentDTO.class);

        documentDTO.setUniqueNumber(UUID.randomUUID());
        documentDTO.setDescription("some description");
        Long id = 1L;

        DocumentDTO documentDTOfromDatabase = documentController.add(documentDTO);
        documentDTO = documentController.getDocumentById(id);
        logger.info(documentDTO);
        logger.info("Document from database " + documentDTOfromDatabase);
        documentController.delete(id);

    }
}
