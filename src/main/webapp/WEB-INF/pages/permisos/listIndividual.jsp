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
                <h5>Lista Peticiones</h5>
            </div>
            <div class="card-body">
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${not empty members}">
                            <table class="table" style="border-collapse: collapse;border:solid 1px black">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col">Fecha Inicio</th>
                                        <th scope="col">Fecha Fin</th>
                                        <th scope="col">Estado</th>
                                        <th scope="col">Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${members}" var="member" begin="${10*(param.page-1)}" end="${10*(param.page-1) + 9}">
                                        <tr>
                                            <td>${member.get('id')}</td>
                                            <td>${member.get('type')}</td>
                                            <td>${member.get('fecSol')}</td>
                                            <td>${member.get('fecIni')}</td>
                                            <td>${member.get('fecFin')}</td>
                                            <td>${member.get('status')}</td>
                                            <td><i class="fas fa-edit"></i></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <%@include file='/WEB-INF/partials/paginator_partial.jsp'%>
                        </c:when>
                        <c:otherwise>
                            No existen usuarios en el sistema.
                        </c:otherwise> 
                    </c:choose>
                </div>
            </div>
        </div>
    </body>
</html>
