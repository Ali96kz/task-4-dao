package com.epam.az.flower.shop.action;

import com.epam.az.flower.shop.entity.User;
import com.epam.az.flower.shop.service.ServiceException;
import com.epam.az.flower.shop.service.UserService;
import com.epam.az.flower.shop.validator.AddMoneyValidator;
import com.epam.az.flower.shop.validator.Validator;
import com.epam.az.flower.shop.validator.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AddMoneyAction implements Action {
    private static final Logger log = LoggerFactory.getLogger(ActionFactory.class);
    private UserService userService = new UserService();
    private Validator validator = new AddMoneyValidator();

    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) throws ActionException {
        try {
            HttpSession session = req.getSession();
            List<String> errorMsg = validator.isValidate(req);
            int userId = (int) session.getAttribute(SESSION_PARAMETER_USER_ID);
            User user = userService.findById(userId);

            if (!isValidate(errorMsg, req, user)) {
                return ACTION_RESULT_CASH;
            }

            int money = Integer.parseInt(req.getParameter(PARAMETER_NAME_MONEY));
            userService.addMoneyToBalance(user, money);
            req.setAttribute(ATTRIBUTE_NAME_USER, user);

            return new ActionResult(JSP_PAGE_NAME_CASH, true);
        } catch (ServiceException e) {
            log.error("can't get entity from service", e);
            throw new ActionException("can't get user from service", e);
        } catch (ValidatorException e) {
            log.error("can't isValidate", e);
            throw new ActionException("can't isValidate", e);
        }
    }

    public boolean isValidate(List<String> errorMsg, HttpServletRequest req, User user) {
        if (errorMsg.size() > 0) {
            req.setAttribute(ATTRIBUTE_ERROR_MSG, errorMsg);
            req.setAttribute(ATTRIBUTE_NAME_USER, user);
            return false;
        }
        return true;
    }
}