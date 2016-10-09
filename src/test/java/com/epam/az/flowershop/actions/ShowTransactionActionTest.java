package com.epam.az.flowershop.actions;

import com.epam.az.flower.shop.action.ActionException;
import com.epam.az.flower.shop.action.AddMoneyAction;
import com.epam.az.flower.shop.action.ShowTransactionAction;
import com.epam.az.flower.shop.dao.UserDAO;
import com.epam.az.flower.shop.dao.UserTransactionDAO;
import com.epam.az.flower.shop.entity.UserTransaction;
import com.epam.az.flower.shop.pool.ConnectionPool;
import com.epam.az.flowershop.TestHttpRequest;
import com.epam.az.flowershop.TestHttpResponse;
import com.epam.az.flowershop.TestSession;
import com.sun.glass.ui.EventLoop;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertNotNull;

public class ShowTransactionActionTest {
    public static final int TEST_USER_ID = 1;
    public static final String ATTRIBUTE_NAME_TRANSACTION_LIST = "transactions";
    public static final String ATTRIBUTE_NAME_USER_ID = "userId";
    private ShowTransactionAction showTransactionAction = new ShowTransactionAction();
    private TestHttpRequest request = new TestHttpRequest();
    private TestHttpResponse response = new TestHttpResponse();
    private TestSession session = new TestSession();

    public ShowTransactionActionTest() throws ActionException {
    }

    @Test
    public void testRequestContainAllTransaction() throws SQLException, ActionException {
        session.setAttribute(ATTRIBUTE_NAME_USER_ID, TEST_USER_ID);
        request.setHttpSession(session);
        showTransactionAction.execute(request, response);
        List<UserTransaction> transactions = (List<UserTransaction>) request.getAttribute(ATTRIBUTE_NAME_TRANSACTION_LIST);
        for (UserTransaction transaction : transactions) {
            assertNotNull(transaction);
            assertNotNull(transaction.getTransactionDate());
            assertNotNull(transaction.getTransaction());
            assertNotNull(transaction.getUser());
        }
    }

}
