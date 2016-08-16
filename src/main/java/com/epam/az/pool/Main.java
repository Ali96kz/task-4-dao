package com.epam.az.pool;

import com.epam.az.pool.DAO.AbstractDAO;
import com.epam.az.pool.DAO.OriginDAO;
import com.epam.az.pool.entity.Origin;

import java.lang.reflect.Field;

public class Main {
    public static void main(String[] args) {
        AbstractDAO abstractDAO = new OriginDAO();
        Origin origin = new Origin();
        origin.setCountry("sad");
        origin.setProvince("asd");
        origin.setId(1);
        abstractDAO.insert(origin);
    }
}
