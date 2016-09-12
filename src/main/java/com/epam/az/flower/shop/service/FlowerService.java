package com.epam.az.flower.shop.service;

import com.epam.az.flower.shop.dao.DAOException;
import com.epam.az.flower.shop.dao.DAOFactory;
import com.epam.az.flower.shop.dao.FlowerDAO;
import com.epam.az.flower.shop.entity.Flower;
import com.epam.az.flower.shop.entity.GrowingCondition;
import com.epam.az.flower.shop.entity.VisualParameters;

import java.util.List;

public class FlowerService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private FlowerDAO flowerDAO ;
    private VisualParametersService visualParametersService = new VisualParametersService();
    private GrowingConditionService growingConditionService = new GrowingConditionService();

    public Flower findById(int id) throws ServiceException {
        Flower flower;
        try {
            flowerDAO = daoFactory.getDao(FlowerDAO.class);
            flower = flowerDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Can't find object by id", e);
        }
        VisualParameters visualParameters = visualParametersService.findById(flower.getVisualParameters().getId());
        GrowingCondition growingCondition = growingConditionService.findById(flower.getGrowingCondition().getId());
        flower.setGrowingCondition(growingCondition);
        flower.setVisualParameters(visualParameters);

        return flower;
    }
    public void update(Flower flower) throws ServiceException {
        try {

            flowerDAO = daoFactory.getDao(FlowerDAO.class);
            daoFactory.startTransaction(flowerDAO);
            flowerDAO.update(flower);
            daoFactory.commitTransaction(flowerDAO);
//            TODO change this
        } catch (DAOException e) {
            try {
                daoFactory.rollBack(flowerDAO);
            } catch (DAOException e1) {
                e1.printStackTrace();
            }
            throw new ServiceException("can't get flower dao from factory", e);
        }

    }
    public int insert(Flower flower) throws ServiceException {
        try {
            flowerDAO = daoFactory.getDao(FlowerDAO.class);
            daoFactory.startTransaction(flowerDAO);
            int flowerId = flowerDAO.insert(flower);
            daoFactory.commitTransaction(flowerDAO);
            return flowerId;
        } catch (DAOException e) {
            try {
                daoFactory.rollBack(flowerDAO);
            } catch (DAOException e1) {
                e1.printStackTrace();
            }
            throw new ServiceException("can't get flower dao from factory", e);
        }
    }
}
