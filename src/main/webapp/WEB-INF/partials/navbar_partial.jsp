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
            Solicitudes
        </a>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
            <a href="listSolicitudes?page=1" class="dropdown-item">
                <c:choose>
                    <c:when test="${userParams.get('userType') != 'Funcionario'}">
                        Todas las Solicitudes
                    </c:when>
                    <c:otherwise>
                        Mis Solicitudes
                    </c:otherwise>
                </c:choose>
            </a>
            <a href="createSolicitud" class="dropdown-item">Crear Solicitud</a>
        </div>
    </div>
    <c:if test="${userParams.get('userType') == 'Administrador' || userParams.get('userType') == 'Encargado'}">
        <div class="dropdown show" style="margin-left: 10px">
            <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Funcionarios
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a href="listFuncionarios?page=1" class="dropdown-item">Listar Funcionarios</a>
            </div>
        </div>
    </c:if>
    <c:if test="${userParams.get('userType') == 'Administrador'}">
        <div class="dropdown show" style="margin-left: 10px">
            <a class="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Usuarios
            </a>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                <a href="listUsers?page=1" class="dropdown-item">Listar Usuarios</a>
                <a href="createUser" class="dropdown-item">Crear Usuario</a>
            </div>
        </div>
    </c:if>
    <a class="btn btn-secondary ml-auto" href="logout">Logout</a>
</nav>
