package com.epam.az.flower.shop.adapter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringAdapter {
    public static Date toSqlDate(String date){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date sqlDate = null;
        try {
            sqlDate = new Date(df.parse(date).getTime());
            System.out.println(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sqlDate;
    }
}