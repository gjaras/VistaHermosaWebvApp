<%-- 
    Document   : dashboard
    Created on : Nov 7, 2018, 6:14:25 PM
    Author     : gabriel.jara
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file='/WEB-INF/partials/head_partial.jsp'%>
    <body>
        <%@include file='/WEB-INF/partials/navbar_partial.jsp'%>
        <c:if test="${not empty message}">
            <div class="${styleClass}" role="alert">
                ${message}
            </div>
        </c:if>
        <div class="card" id="main-panel">
            <div class="card-header">
                <h3>Bienvenido <b>${userName}</b></h3>
            </div>
            <div class="card-body">
                <div class="container-fluid" style="background-color: darkgrey;border-radius: 3px">
                    <table id="totals_table" style="width:100%">
                        <tr>
                            <th><i class="fas fa-edit"></i> Peticiones Pendientes</th>
                            <th><i class="fas fa-check-circle"></i> Peticiones Aceptadas</th>
                            <th><i class="fas fa-times-circle"></i> Peticiones Rechazadas</th>
                        </tr>
                        <tr>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty solPend}">
                                        ${solPend}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise> 
                                </c:choose>
                            </td >
                            <td>
                                <c:choose>
                                    <c:when test="${not empty solAcept}">
                                        ${solAcept}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise> 
                                </c:choose>
                            </td> 
                            <td >
                                <c:choose>
                                    <c:when test="${not empty solRech}">
                                        ${solRech}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise> 
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="container-fluid" style="padding-top: 3%">
                    <h4>Ultima Petición:</h4>
                    <c:choose>
                        <c:when test="${not empty lastPermiso}">
                            <table class="table" style="border-collapse: collapse;border:solid 1px black">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Fecha</th>
                                        <th scope="col">Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>${lastPermiso.get("permisoId")}</td>
                                        <td>${lastPermiso.get("permisoFecha")}</td>
                                        <td>${lastPermiso.get("permisoStatus")}</td>
                                    </tr>
                                </tbody>
                            </table>

                        </c:when>
                        <c:otherwise>
                            No hay permisos previos.
                        </c:otherwise> 
                    </c:choose>
                </div>
            </div>
        </div>
        <style>
            #totals_table {
                border: none;
                border-collapse: collapse; 
            }
            #totals_table tr { 
                border: none; 
            }
            #totals_table th{
                padding-left: 1%;
                font-size: 20px;
                font-weight: bold;
                color: darkslategray
            }
            #totals_table td{
                font-size: 50px;
                font-weight: bold; 
                padding-left:2%;
                color: darkslategray;
                border-right: solid 1px darkslategrey; 
                border-left: solid 1px darkslategrey;
            }
            #totals_table td:first-child,#totals_table td:last-child{
                border-right: 0px;
                border-left: 0px
            }
        </style>
    </body>
</html>
