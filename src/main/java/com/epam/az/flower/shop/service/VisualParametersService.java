package com.epam.az.flower.shop.service;

import com.epam.az.flower.shop.dao.DAOFactory;
import com.epam.az.flower.shop.dao.VisualParametersDAO;
import com.epam.az.flower.shop.entity.VisualParameters;

import java.util.List;

public class VisualParametersService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private VisualParametersDAO visualParametersDAO = daoFactory.getDao(VisualParametersDAO.class);
    public List<VisualParameters> getAllVisualParameters(){
        return visualParametersDAO.getAll();
    }
    public VisualParameters  findById(int id){
        return visualParametersDAO.findById(id);
    }
}
