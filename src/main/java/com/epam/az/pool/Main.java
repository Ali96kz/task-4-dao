package com.epam.az.pool;

import com.epam.az.pool.DAO.AbstractDAO;
import com.epam.az.pool.DAO.OriginDAO;
import com.epam.az.pool.entity.Origin;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) {
        AbstractDAO abstractDAO = new OriginDAO();
        Origin origin = new Origin();
        origin.setCountry("sss");
        origin.setProvince("asdasd");
        origin.setId(1);
//        abstractDAO.update(origin);
        abstractDAO.delete(origin);
    }
}
