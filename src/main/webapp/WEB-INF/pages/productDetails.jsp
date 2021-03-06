<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
    <p>
        Welcome to Expert-Soft training!
    </p>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <tr>
            <td>
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <p>
        Price History:
    <table>
        <thead>
        <tr>
            <td>Date</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="entry" items="${product.getPriceHistory()}">
            <tr>
                <td>${entry.getKey()}</td>
                <td class="price"><fmt:formatNumber value="${entry.getValue()}" type="currency"
                                                    currencySymbol="${product.currency.symbol}"/></td>
            </tr>
        </c:forEach>
    </table
    </p>
    <p>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <label>Quantity: </label> <input name="quantity" value="${not empty param.quantity? param.quantity: 1}"/>
        <button>Add to cart</button>
        <c:if test="${not empty error}">
            <span style="color:red">${error}</span>
        </c:if>
        <c:if test="${param.success}">
            <span style="color:green">Added to cart</span>
        </c:if>
    </form>
    </p>
    <b>Recently viewed:</b>
    <table>
        <c:forEach var="product" items="${viewedProducts}">
            <tr>
                <td>
                    <img class="product-tile"
                         src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                </td>
                <td><a href="<c:url value="/products/${product.id}"/>">${product.description}</a></td>
                <td class="price">
                    <fmt:formatNumber value="${product.getPrice()}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>