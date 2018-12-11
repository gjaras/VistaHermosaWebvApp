<%-- 
    Document   : list
    Created on : Nov 8, 2018, 12:01:50 AM
    Author     : gabriel.jara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file='/WEB-INF/partials/head_partial.jsp'%>
    <body>
        <%@include file='/WEB-INF/partials/navbar_partial.jsp'%>
        <div class="card" id="main-panel">
            <div class="card-header">
                <h5>Listado de Funcionarios</h5>
            </div>
            <div class="card-body">
                <div class="card" style="margin-left: 5%; margin-right: 5%;margin-top:0;margin-bottom:10px">
                    <div class="card-header">
                        <h6>Funcionario</h5>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-sm-2">
                                <label>Nombre:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${nombre}</label>
                            </div>
                            <div class="col-sm-2">
                                <label>Rut:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${rut}</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">
                                <label>Fecha Nacimiento:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${fecNac}</label>
                            </div>
                            <div class="col-sm-2">
                                <label>Correo:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${correo}</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">
                                <label>Direccion:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${direccion}</label>
                            </div>
                            <div class="col-sm-2">
                                <label>Cargo:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${cargo}</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-2">
                                <label>Unidad:</label>
                            </div>
                            <div class="col-sm-4">
                                <label>${unidad}</label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${not empty solicitudes}">
                            <table class="table" style="border-collapse: collapse;border:solid 1px black">
                                <thead class="thead-dark">
                                    <tr>
                                        <th style="width:4%" scope="col">ID</th>
                                        <th style="width:12%" scope="col">Rut Solicitante</th>
                                        <th style="width:10%" scope="col">Tipo</th>
                                        <th style="width:10%" scope="col">Fecha Solicitud</th>
                                        <th style="width:10%" scope="col">Fecha Inicio</th>
                                        <th style="width:10%" scope="col">Fecha Fin</th>
                                        <th style="width:12%" scope="col">Estado</th>
                                        <th style="width:12%" scope="col">Autorizador</th>
                                        <th style="width:20%"scope="col">Acciones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${solicitudes}" var="member"  begin="${10*(param.page-1)}" end="${10*(param.page-1) + 9}">
                                        <tr>
                                            <td>${member.get('id')}</td>
                                            <td>${member.get('rutSol')}</td>
                                            <td>${member.get('type')}</td>
                                            <td>${member.get('fecSol')}</td>
                                            <td>${member.get('fecIni')}</td>
                                            <td>${member.get('fecFin')}</td>
                                            <td>${member.get('status')}</td>
                                            <td>${member.get('aut')}</td>
                                            <td align="center"><c:if test="${member.get('status') == 'Pendiente'}"><i id="accept_${member.get('id')}" class="fas fa-check-circle fa-lg" onclick="accept(this.id)" title="aceptar"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i id="reject_${member.get('id')}" class="fas fa-times-circle fa-lg" onclick="reject(this.id)" title="rechazar"></i></c:if></td>
                                            </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <%@include file='/WEB-INF/partials/paginator_partial.jsp'%>
                        </c:when>
                        <c:otherwise>
                           Este funcionario no tiene solicitudes
                        </c:otherwise> 
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
    <style>
        .fa-eye{
            color: black
        }
        .fa-eye:hover {
            color: green;
        }
    </style>
</html>
