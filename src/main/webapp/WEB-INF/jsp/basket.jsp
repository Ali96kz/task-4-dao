<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%--@elvariable id="basket" type="com.epam.az.flower.shop.entity.Basket"--%>
<fmt:bundle basename="i18n">
    <fmt:message key="basket.number.of.products" var="numberProducts"/>
    <fmt:message key="basket.bill" var="operationBill"/>
    <fmt:message key="basket.delete.product" var="deleteProduct"/>
    <fmt:message key="basket.buy" var="buyBasket"/>
    <fmt:message key="basket.buy.all.basket" var="buyBasket"/>
    <fmt:message key="basket.buy.product" var="buyThisProduct"/>

</fmt:bundle>
<t:autorized-user-template>
    <jsp:attribute name="navbar">
    <div class="container">
        <div class="row">
            <div class="col-md-9">
                <div class="row">
                        ${numberProducts}:${basket.products.size()}<br>
                        ${operationBill}:${bill}<br>
                    <a href="buy-all-basket"></a>

                        <c:forEach items="${basket.products}" var="product">
                        <br><c:out value="${product.flower.name}"/><br>
                            <c:out value="${product.price}"/><br>
                            <c:out value="${product.description}"/><br>
                        <a href="delete-product-basket?productId=${product.id}">
                                ${deleteProduct}
                        </a>

                            <br>
                        <a href="buy-product?productId=${product.id}">
                            <c:out value="${buyThisProduct}"/><br>
                        </a>
                            </c:forEach>
                        <br>
                        <a href="buy-all-basket">
                            <c:out value="${buyBasket}"/><br>
                        </a>
                        <br>
                        <fmt:bundle basename="i18n">
                            <c:forEach items="${errorMsg}" var="msg">
                                <c:choose>
                                    <c:when test="${msg eq 'error.basket.empty'}">
                                        <font size="3" color="red"> <fmt:message key="error.basket.empty"/></font>
                                    </c:when>

                                    <c:when test="${msg eq 'error.havent.enough.money'}">
                                        <font size="3" color="red"> <fmt:message key="error.havent.enough.money"/></font>
                                    </c:when>
                                </c:choose>
                                <br>
                            </c:forEach>
                        </fmt:bundle>
                </div>
            </div>
        </div>
    </div>

    </jsp:attribute>
</t:autorized-user-template>
