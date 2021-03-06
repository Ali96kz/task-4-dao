package com.epam.az.flower.shop.dao.manager;

import com.epam.az.flower.shop.dao.DAOException;
import com.epam.az.flower.shop.entity.BaseEntity;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetParser<E extends BaseEntity> {
    private static final String DECLARED_FIELD_ID = "id";

    public E parseResultSet(E object, ResultSet resultSet) throws DAOException {
        try {
            Field[] fields = object.getClass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = getValue(fields[i], resultSet);
                fields[i].set(object, value);
            }

            object.setId(resultSet.getInt(object.getClass().getSimpleName() + ".id"));
            object.setDeleteDay(resultSet.getDate(object.getClass().getSimpleName() + ".deleteDay"));

            return object;
        } catch (SQLException | IllegalAccessException e1) {
            throw new DAOException("parse resultSet error", e1);
        }
    }

    private Object getValue(Field field, ResultSet resultSet) throws DAOException {
        try {
            Object value;
            Class fieldType = field.getType();
            if (fieldType == Integer.class || fieldType == int.class) {
                value = resultSet.getInt(field.getName());
            } else if (fieldType == String.class) {
                value = resultSet.getString(field.getName());
            } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                value = resultSet.getBoolean(field.getName());
            } else if (fieldType == Date.class) {
                value = resultSet.getDate(field.getName());
            } else {
                Field idField = BaseEntity.class.getDeclaredField(DECLARED_FIELD_ID);
                idField.setAccessible(true);
                value = (E) fieldType.newInstance();
                idField.set(value, resultSet.getInt(Util.lowFirstLetter(fieldType.getSimpleName()) + "Id"));
            }
            return value;
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            throw new DAOException("can't get value ", e);
        }
    }

}
