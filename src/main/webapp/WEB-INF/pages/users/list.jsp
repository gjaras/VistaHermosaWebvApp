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
                <h5>Listar Usuarios</h5>
            </div>
            <div class="card-body">
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${not empty members}">
                            <%@include file='/WEB-INF/partials/paginator_partial.jsp'%>
                            <table class="table" style="border-collapse: collapse;border:solid 1px black;margin-bottom:0px">
                                <thead class="thead-dark">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Nombre</th>
                                        <th scope="col">Tipo</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${members}" var="member" begin="${10*(param.page-1)}" end="${10*(param.page-1) + 9}">
                                        <tr>
                                            <td>${member.get('id')}</td>
                                            <td>${member.get('nombre')}</td>
                                            <td>${member.get('tipo')}</td>
                                            <td>actions</td>
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
