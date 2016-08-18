package com.epam.az.pool;

import com.epam.az.pool.entity.Origin;
import com.epam.az.pool.entity.SyntheticFlower;
import com.epam.az.pool.entity.VisualParameters;
import com.epam.az.pool.pool.ConnectionPool;
import com.epam.az.pool.service.ProductService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductServiceTest {
    static ProductService productService = new ProductService();
    static VisualParameters visualParameters = new VisualParameters();
    static Origin origin = new Origin();
    static SyntheticFlower syntheticFlower = new SyntheticFlower();
    static ConnectionPool connectionPool = ConnectionPool.getInstance();
    static int id;
    @BeforeClass
    public static void init() {
        visualParameters.setId(2);

        origin.setProvince("Almaty");
        origin.setCountry("Kazakhstan");
        origin.setId(525);

        syntheticFlower.setVisualParameters(visualParameters);
        syntheticFlower.setOrigin(origin);
        syntheticFlower.setType("syntheticFlower");
        syntheticFlower.setName("Rose");
        syntheticFlower.setMaterial("plastic");
        syntheticFlower.setDescription("red rose simple");

    }

    @Test
    public void testInsert() throws SQLException {
        id = productService.insert(syntheticFlower);

        checkWithDataBaseVersion(id);
    }
    public void checkWithDataBaseVersion(int  id) throws SQLException {
        Statement statement = connectionPool.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, visualParametersId, material FROM SyntheticFlower WHERE ID = " + id);
        if (resultSet.next()) {
            assertEquals("Incorrect id", id, resultSet.getInt("id"));
            assertEquals("", syntheticFlower.getMaterial(), resultSet.getString("material"));
            assertEquals("", syntheticFlower.getVisualParameters().getId().intValue(), resultSet.getInt("visualParametersId"));
        }

        statement = connectionPool.getConnection().createStatement();
        resultSet = statement.executeQuery("SELECT id, originId, name, description, type, deleteDay FROM Product WHERE ID = " + id);

        if (resultSet.next()) {
            assertEquals("Incorrect id", id, resultSet.getInt("id"));
            assertEquals("", syntheticFlower.getName(), resultSet.getString("name"));
            assertEquals("", syntheticFlower.getOrigin().getId().intValue(), resultSet.getInt("originId"));
            assertEquals("", syntheticFlower.getDescription(), resultSet.getString("description"));
            assertEquals("", syntheticFlower.getType(), resultSet.getString("type"));
        }

    }
    @Test
    public void testUpdate() throws SQLException {
        syntheticFlower.setMaterial("steel");
        productService.update(syntheticFlower);
        checkWithDataBaseVersion(28);
    }
    @Test
    public void testFindById() throws SQLException{
        syntheticFlower = (SyntheticFlower) productService.findById(28, SyntheticFlower.class);
        Statement statement = connectionPool.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id, visualParametersId, material FROM SyntheticFlower WHERE ID = " + id);
        if (resultSet.next()) {
            assertNotNull(resultSet.getInt("id"));
            assertNotNull(resultSet.getString("material"));
            assertNotNull(resultSet.getInt("visualParametersId"));
        }

        statement = connectionPool.getConnection().createStatement();
        resultSet = statement.executeQuery("SELECT id, originId, name, description, type, deleteDay FROM Product WHERE ID = " + id);

        if (resultSet.next()) {
            assertNotNull(resultSet.getInt("id"));
            assertNotNull(resultSet.getString("name"));
            assertNotNull(resultSet.getInt("originId"));
            assertNotNull(resultSet.getString("description"));
            assertNotNull(resultSet.getString("type"));
        }

    }
    @Test
    public void testgetAll(){

    }
}
