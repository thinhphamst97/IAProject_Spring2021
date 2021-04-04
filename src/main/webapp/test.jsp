<%-- 
    Document   : test
    Created on : Mar 13, 2021, 1:57:34 AM
    Author     : ThinhPH
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form>
            <h4 class="card-title ">Choose an OS: </h4>
            <select name="selectedOS" id="selectOS">
                <option value="">   --- Choose an OS ----   </option>
                <option value="windows">Windows</option>
                <option value="linux">Linux</option>
            </select>
            <button type="submit" class="btn btn-sm btn-outline-light" value="submit">OK</button>
        </form>
    </div>
    <c:set var="selectedOS" value="${param.selectedOS}"></c:set>
    <p>${selectedOS}</p>
</body>
</html>
