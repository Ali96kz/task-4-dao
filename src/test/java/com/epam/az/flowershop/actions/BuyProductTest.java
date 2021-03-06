package com.epam.az.flowershop.actions;

import com.epam.az.flower.shop.action.ActionException;
import com.epam.az.flower.shop.action.ActionResult;
import com.epam.az.flower.shop.action.BuyProductAction;
import com.epam.az.flower.shop.dao.DAOException;
import com.epam.az.flower.shop.entity.Product;
import com.epam.az.flower.shop.entity.User;
import com.epam.az.flower.shop.entity.UserTransaction;
import com.epam.az.flower.shop.pool.ConnectionPool;
import com.epam.az.flower.shop.service.ProductService;
import com.epam.az.flower.shop.service.ServiceException;
import com.epam.az.flower.shop.service.UserTransactionService;
import com.epam.az.flowershop.AbstractTest;
import com.epam.az.flowershop.TestHttpRequest;
import com.epam.az.flowershop.TestHttpResponse;
import com.epam.az.flowershop.TestSession;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class BuyProductTest extends AbstractTest {
    private static final int TEST_USER_ID_WITHOUT_MONEY = 1;
    private static final int TEST_USER_ID = 2;
    private static final String TRANSACTION_NAME_BUY_PRODUCT = "buy product";
    private static final String JSP_PAGE_NAME_BILL = "bill";
    private static final String TEST_PRODUCT_ID = "10";
    private static final String JSP_PAGE_NAME_VITRINE = "vitrine";
    public static final String PRODUCT_ID = "productId";
    public static final String SESSION_ATTRIBUTE_USER_ID = "userId";

    private TestHttpRequest request = new TestHttpRequest();
    private TestHttpResponse response = new TestHttpResponse();
    private TestSession session = new TestSession();
    private BuyProductAction buyProductAction = new BuyProductAction();
    private ConnectionPool connectionPool = new ConnectionPool();
    private ProductService productService = new ProductService();
    private UserTransactionService transactionService = new UserTransactionService();

    @Before
    public void init() throws ServiceException, SQLException {
        request.setParameter(PRODUCT_ID, TEST_PRODUCT_ID);
        session.setAttribute(SESSION_ATTRIBUTE_USER_ID, TEST_USER_ID);
        request.setHttpSession(session);

        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("UPDATE User SET User.balance = 10000 where User.id = " + TEST_USER_ID + ";");
        connection.close();
    }


  @Test
    public void testWithNormalValue() throws ActionException, SQLException, DAOException, ServiceException {
        User beforeUpdateUser = getUncacheUserById(TEST_USER_ID);

        ActionResult actionResult = buyProductAction.execute(request, response);
        assertEquals(JSP_PAGE_NAME_BILL, actionResult.getView());

        User afterActionExecuteUser = getUncacheUserById(TEST_USER_ID);
        Product product = productService.findById(Integer.parseInt(TEST_PRODUCT_ID));
        assertEquals(beforeUpdateUser.getBalance() - product.getPrice(), afterActionExecuteUser.getBalance());

        List<UserTransaction> transactions = transactionService.getAll(TEST_USER_ID);
        assertEquals(transactions.get(transactions.size() - 1).getSum(), product.getPrice());
        assertEquals(transactions.get(transactions.size() - 1).getTransaction().getName(), TRANSACTION_NAME_BUY_PRODUCT);
    }

    @Test
    public void testWithIncorrectProductId() throws ServiceException, SQLException, DAOException, ActionException {
        request.setParameter(PRODUCT_ID, "45.6");
        User beforeUpdateUser = getUncacheUserById(TEST_USER_ID);
        ActionResult actionResult = buyProductAction.execute(request, response);
        assertEquals(JSP_PAGE_NAME_VITRINE, actionResult.getView());

        User afterActionExecuteUser = getUncacheUserById(TEST_USER_ID);
        assertEquals(beforeUpdateUser.getBalance(), afterActionExecuteUser.getBalance());
    }

    @Test
    public void testWithProductIdBelowZero() throws ServiceException, SQLException, DAOException, ActionException {
        request.setParameter(PRODUCT_ID, "-45");
        User beforeUpdateUser = getUncacheUserById(TEST_USER_ID);
        ActionResult actionResult = buyProductAction.execute(request, response);
        assertEquals(JSP_PAGE_NAME_VITRINE, actionResult.getView());

        User afterActionExecuteUser = getUncacheUserById(TEST_USER_ID);
        assertEquals(beforeUpdateUser.getBalance(), afterActionExecuteUser.getBalance());
    }

    @Test
    public void testWithUnexistProduct() throws SQLException, DAOException, ActionException, ServiceException {
        request.setParameter(PRODUCT_ID, "1000");
        User beforeUpdateUser = getUncacheUserById(TEST_USER_ID);

        ActionResult actionResult = buyProductAction.execute(request, response);
        assertEquals(JSP_PAGE_NAME_VITRINE, actionResult.getView());

        User afterActionExecuteUser = getUncacheUserById(TEST_USER_ID);
        assertEquals(beforeUpdateUser.getBalance(), afterActionExecuteUser.getBalance());
    }

    @Test
    public void testWithUserDontHaveMoney() throws SQLException {
        Connection connection = connectionPool.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("UPDATE User SET User.balance = 0 where User.id = " + TEST_USER_ID_WITHOUT_MONEY + ";");
        connection.close();
    }

}
