package com.epam.az.flower.shop.service;

import com.epam.az.flower.shop.dao.UserOrderDAO;
import com.epam.az.flower.shop.entity.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderService {
    private static final Class<UserOrderDAO> USER_ORDER_DAO_CLASS = UserOrderDAO.class;
    private UserService userService = new UserService();
    private UserTransactionService userTransactionService = new UserTransactionService();
    private Transaction transaction = new Transaction();
    private ProxyService proxyService = new ProxyService(USER_ORDER_DAO_CLASS);

    public void createOrder(User user, Product product) throws ServiceException {
        insertIntoUserTransaction(user, product);
        UserOrder userOrder = fillUserOrder(user, product);
        proxyService.insert(userOrder);
        userService.update(user);

    }
    private UserOrder fillUserOrder(User user, Product product){
        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setProduct(product);
        userOrder.setOrderDate(getDate());
        user.setBalance(user.getBalance() - product.getPrice());
        return userOrder;
    }
    private java.sql.Date getDate() {
        Calendar c = new GregorianCalendar();
        java.util.Date utilDate = c.getTime();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        return sqlDate;
    }

    private void insertIntoUserTransaction(User user, Product product) throws ServiceException {
        transaction.setId(2);
        UserTransaction userTransaction = new UserTransaction();
        userTransaction.setTransaction(transaction);
        userTransaction.setUser(user);
        userTransaction.setSum(product.getPrice());
        userTransaction.setTransactionDate(getDate());
        userTransactionService.insert(userTransaction);

    }

}
