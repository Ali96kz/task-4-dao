package com.epam.az.flower.shop.action;


import com.epam.az.flower.shop.service.ServiceException;
import com.epam.az.flower.shop.service.UserService;
import com.epam.az.flower.shop.validator.LogInValidator;
import com.epam.az.flower.shop.validator.Validator;
import com.epam.az.flower.shop.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class LoginAction implements Action {
    public static final String JSP_PAGE_NAME_PROFILE = "profile";
    Logger log = LoggerFactory.getLogger(LoginAction.class);
    Validator validator = new LogInValidator();
    UserService userService;
    public LoginAction() throws ActionException {
        try {
            userService = new UserService();

        } catch (ServiceException e) {
            throw new ActionException("can't initialize service class", e);
        }
    }

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        HttpSession session = req.getSession();

        List<String> errorMsg = null;
        try {
            errorMsg = validator.isValidate(req);
        } catch (ValidatorException e) {
            throw new ActionException("problem with validating ", e);
        }
        if (errorMsg.size() > 0) {
            req.setAttribute("errorMsg", errorMsg);
            return new ActionResult("login");
        }
        String nickName = req.getParameter("nickName");
        String password = req.getParameter("password");

        int userId = 0;
        try {
            userId = userService.getUserByCredentials(nickName, password);
        } catch (ServiceException e) {
            throw new ActionException("can't find user by id", e);
        }

        session.setAttribute("userId", userId);
        return new ActionResult(JSP_PAGE_NAME_PROFILE, true);
        }
}
