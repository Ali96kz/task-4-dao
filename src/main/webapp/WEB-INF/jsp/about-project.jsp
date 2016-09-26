<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:autorized-user-template>
    <jsp:attribute name="navbar">
<div class="container">

    <div class="row">
        <div class="col-md-9">
            <div class="row">
                Simple online flower shop where you can buy some flowers.
            </div>
        </div>
    </div>
</div>

    </jsp:attribute>
</t:autorized-user-template>