package com.epam.az.flower.shop.util.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Validator {

    List<String> isValidate(HttpServletRequest request);

}