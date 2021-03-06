package com.epam.az.flower.shop.validator;

import com.epam.az.flower.shop.service.ServiceException;
import com.epam.az.flower.shop.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RegisterProfileValidator extends AbstractValidator {
    private static final int NICKNAME_MAX_LENGTH = 16;
    private static final int NICKNAME_MIN_LENGTH = 3;
    private static final int PASSWORD_MIN_LENGTH = 6;
    private static final int PASSWORD_MAX_LENGTH = 12;
    private static final int LAST_NAME_MAX_LENGTH = 16;
    private static final int LAST_NAME_MIN_LENGTH = 3;
    private static final int FIRST_NAME_MAX_LENGTH = 16;
    private static final int FIRST_NAME_MIN_LENGTH = 3;
    private static Logger logger = LoggerFactory.getLogger(RegisterProfileValidator.class);

    private UserService userService = new UserService();

    public List<String> isValidate(HttpServletRequest request) throws ValidatorException {
        List<String> errorMsg = new ArrayList<>();
        String name = request.getParameter(PARAMETER_FIRST_NAME);
        String nickName = request.getParameter(PARAMETER_NICK_NAME);
        String lastName = request.getParameter(PARAMETER_LAST_NAME);
        String password = request.getParameter(PARAMETER_PASSWORD);
        String confirmPassword = request.getParameter(PARAMETER_CONFIRM_PASSWORD);
        String date = request.getParameter(PARAMETER_DATE_BIRTHDAY);

        equalsPassword(errorMsg, confirmPassword, password);
        isNicknameFree(nickName, errorMsg);
        validateDate(errorMsg, date);
        validateString(errorMsg, name, ERROR_ATTRIBUTE_FIRST_NAME, FIRST_NAME_MIN_LENGTH, FIRST_NAME_MAX_LENGTH);
        validateString(errorMsg, lastName, ERROR_ATTRIBUTE_NAME_LAST_NAME, LAST_NAME_MIN_LENGTH, LAST_NAME_MAX_LENGTH);
        validateString(errorMsg, nickName, ERROR_ATTRIBUTE_NAME_NICK_NAME, NICKNAME_MIN_LENGTH, NICKNAME_MAX_LENGTH);
        validateString(errorMsg, password, ERROR_ATTRIBUTE_NAME_PASSWORD, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);
        validateString(errorMsg, confirmPassword, ERROR_ATTRIBUTE_NAME_CONFIRM_PASSWORD, PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH);

        return errorMsg;
    }

    private void equalsPassword(List<String> errorMsg, String confirmPassword, String password) {
        if (!password.equals(confirmPassword)) {
            errorMsg.add(DIFFERENT_PASSWORD_ERROR_MSG);
        }
    }

    private void isNicknameFree(String nickName, List<String> errorMsg) throws ValidatorException {
        try {
            boolean isFree = userService.isFree(nickName);
            if (!isFree) {
                errorMsg.add(BUSY_NICKNAME_ERROR_MSG);
            }
        } catch (ServiceException e) {
            logger.error("can't validate object", e);
            throw new ValidatorException("can't isValidate nickname", e);
        }
    }
}
