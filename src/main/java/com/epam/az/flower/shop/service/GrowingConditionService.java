package com.epam.az.flower.shop.service;

import com.epam.az.flower.shop.dao.*;
import com.epam.az.flower.shop.entity.GrowingCondition;
import com.epam.az.flower.shop.entity.Temperature;
import com.epam.az.flower.shop.entity.WaterInWeek;

import java.util.List;

public class GrowingConditionService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private TemperatureService temperatureService;
    private WaterInWeekService waterInWeekService;
    private GrowingConditionDAO growingConditionDAO;
    public GrowingConditionService() throws ServiceException {
        try {
            temperatureService = new TemperatureService();
            waterInWeekService = new WaterInWeekService();
            growingConditionDAO = daoFactory.getDao(GrowingConditionDAO.class);
        } catch (DAOException e) {
            throw new ServiceException("", e);
        }
    }

    public List<GrowingCondition> getAllGrowingConditions() {
        return growingConditionDAO.getAll();
    }

    public GrowingCondition findById(Integer id) throws ServiceException {
        try {
            daoFactory.startOperation(growingConditionDAO);
            GrowingCondition growingCondition = growingConditionDAO.findById(id);
            fillGrowingCondition(growingCondition);
            daoFactory.endOperation(growingConditionDAO);
            return growingCondition;
        } catch (DAOException e) {

            throw new ServiceException("", e);
        }
    }

    public void fillGrowingCondition(GrowingCondition growingCondition) throws ServiceException {
        Temperature temperature ;
        try {
            temperature = temperatureService.findById(growingCondition.getTemperature().getId());
            WaterInWeek waterInWeek = waterInWeekService.findById(growingCondition.getWaterInWeek().getId());
            growingCondition.setWaterInWeek(waterInWeek);
            growingCondition.setTemperature(temperature);
        } catch (ServiceException e) {
            throw new ServiceException("can't fill growing condition", e);
        }
    }

    public int add(GrowingCondition growingCondition) throws ServiceException {
        try {
            daoFactory.startTransaction(growingConditionDAO);
            int growingConditionId = growingConditionDAO.insert(growingCondition);
            daoFactory.commitTransaction(growingConditionDAO);
            return growingConditionId;
        } catch (DAOException e) {
            try {
                daoFactory.rollBack(growingConditionDAO);
            } catch (DAOException e1) {
                e1.printStackTrace();
            }
            throw new ServiceException("can't add growing condition dao", e);
        }
    }
}
