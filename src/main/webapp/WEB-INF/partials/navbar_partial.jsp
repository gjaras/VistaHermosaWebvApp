<%-- 
    Document   : navbar_partial
    Created on : Nov 7, 2018, 6:14:55 PM
    Author     : gabriel.jara
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-dark bg-info static-top text-uppercase justify-content-end" id="mainNav">
    <div class="navbar-header">
        <a class="navbar-brand" href="dashboard">Municipalidad Visa Hermosa</a>
    </div>
    <div class="dropdown show" style="margin-left: 10px">
        <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            Peticiones
        </a>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            
        </div>
    </div>
    <c:if test="${userParams.get('userType') == 'admin'}">
        <div class="dropdown show" style="margin-left: 10px">
            <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Usuarios
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a href="listUsers" class="dropdown-item">Listar Usuarios</a>
                <a href="createUser" class="dropdown-item">Crear Usuario</a>
            </div>
        </div>
    </c:if>
    <a class="btn btn-secondary ml-auto" href="dashboard">Logout</a>
</nav>
