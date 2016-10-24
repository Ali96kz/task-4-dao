package com.epam.az.flower.shop.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class AddProductValidator extends AbstractValidator {

    @Override
    public List<String> isValidate(HttpServletRequest request) {
        List<String> errorMsg = new ArrayList<>();

        validateString(errorMsg, request.getParameter(PARAMETER_NAME_FLOWER_NAME), FIELD_MENU_FLOWER_NAME, FLOWER_NAME_MIN_LENGTH, FLOWER_NAME_MAX_LENGTH);
        validateString(errorMsg, request.getParameter(PARAMETER_NAME_DESCRIPTION), FIELD_MENU_DESCRIPTION, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);

        validatePositiveNumber(errorMsg, request.getParameter(PARAMETER_NAME_AVERAGE_HEIGHT), FIELD_MENU_AVERAGE_HEIGHT);
        validatePositiveNumber(errorMsg, request.getParameter(PARAMETER_NAME_PRICE), FIELD_MENU_PRICE);

        return errorMsg;
    }
}
