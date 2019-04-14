package com.gmail.rebel249.repository.impl;

import com.gmail.rebel249.repository.ConnectionService;
import com.gmail.rebel249.repository.DocumentRepository;
import com.gmail.rebel249.repository.exception.ConnectionException;
import com.gmail.rebel249.repository.exception.DatabaseException;
import com.gmail.rebel249.repository.model.MyDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.UUID;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {

    private static final Logger logger = LogManager.getLogger(DocumentRepositoryImpl.class);

    private ConnectionService connectionService;

    public DocumentRepositoryImpl(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    public MyDocument add(MyDocument myDocument) {

        logger.info("Init in repository");
        String insertTableSQL = "INSERT INTO document (description, unique_number)" +
                "VALUES (?, ?)";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(insertTableSQL,
                        Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, myDocument.getDescription());
                preparedStatement.setString(2, String.valueOf(myDocument.getUniqueNumber()));
                preparedStatement.executeUpdate();
                try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                    if (rs.next()) {
                        myDocument.setId(rs.getLong(1));
                    }
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new DatabaseException("Database exception during saving document");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException("Exception during connection");
        }
        return myDocument;
    }

    @Override
    public MyDocument getDocumentById(Long id) {
        String requestSQL = "SELECT * FROM document where id = ?";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(requestSQL);
                preparedStatement.setLong(1, id);
                MyDocument myDocument;
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    myDocument = getDocument(resultSet);
                    return myDocument;
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new DatabaseException("Database exception during saving document");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException("Exception during connection");
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        String requestSQL = "UPDATE document SET deleted = TRUE WHERE id = ?";
        try (Connection connection = connectionService.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(requestSQL);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
                throw new DatabaseException("Database exception when trying to delete document");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ConnectionException("Exception during connection");
        }
    }

    private MyDocument getDocument(ResultSet resultSet) {
        MyDocument myDocument = new MyDocument();
        try {
            if (resultSet.next()) {
                myDocument.setDescription(resultSet.getString("description"));
                myDocument.setUniqueNumber(UUID.fromString(resultSet.getString("unique_number")));
                return myDocument;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseException("Database exception during getting document");
        }
        return null;

    }
}
