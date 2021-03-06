package com.epam.az.flower.shop.dao;

import com.epam.az.flower.shop.dao.manager.CachedDAO;
import com.epam.az.flower.shop.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionDAO extends CachedDAO<Transaction> {
    private static final Logger logger = LoggerFactory.getLogger(TransactionDAO.class);

    public Transaction getTransactionByName(String name) throws DAOException {
        String sql = "SELECT Transaction.id, Transaction.name FROM Transaction where name = '" + name + "';";
        try {
            ResultSet resultSet = getSqlExecutor().executeSqlQuery(sql, getConnection().createStatement());
            Transaction transaction = new Transaction();
            if (resultSet.next()) {
                transaction.setId(resultSet.getInt("Transaction.id"));
                transaction.setName(resultSet.getString("Transaction.name"));
            }
            return transaction;
        } catch (SQLException e) {
            logger.error("can't get transaction by name", e);
            throw new DAOException("can't get transaction by name", e);
        }
    }
}
