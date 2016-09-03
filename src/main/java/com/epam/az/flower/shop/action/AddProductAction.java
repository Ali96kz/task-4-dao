package com.epam.az.flower.shop.action;

import com.epam.az.flower.shop.adapter.StringAdapter;
import com.epam.az.flower.shop.entity.*;
import com.epam.az.flower.shop.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductAction implements Action {
    StringAdapter stringAdapter = new StringAdapter();
    ProductService productService = new ProductService();
    @Override
    public ActionResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Product product = new Product();
        Flower flower = new Flower();
        Origin origin = new Origin();
        VisualParameters visualParameters = new VisualParameters();
        GrowingCondition growingCondition = new GrowingCondition();
        growingCondition.setId(1);
        int originId = stringAdapter.toInt(req.getParameter("originId"));
        int visualParametersId = stringAdapter.toInt(req.getParameter("visualParametersId"));
        origin.setId(originId);
        visualParameters.setId(visualParametersId);

        flower.setName(req.getParameter("name"));
        flower.setAverageHeight(stringAdapter.toInt(req.getParameter("averageHeight")));
        flower.setVisualParameters(visualParameters);
        flower.setGrowingCondition(growingCondition);

        product.setOrigin(origin);
        product.setFlower(flower);
        product.setDescription(req.getParameter("description"));
        product.setPrice(stringAdapter.toInt(req.getParameter("price")));

        int productId = productService.addNewProduct(product);
        return new ActionResult("product-inf?id="+productId, true);
    }

}
