<%-- 
    Document   : login
    Created on : Nov 7, 2018, 12:02:20 AM
    Author     : gabriel.jara
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file='/WEB-INF/partials/head_partial.jsp'%>
    <body>
        <div class="card" id="login-panel">
            <div class="card-header">
                Login
            </div>
            <div class="card-body">
                <form id="login-form" name="login-form" method="post" action="login">
                    <c:if test="${not empty message}">
                        <div class="${styleClass}" role="alert">
                            ${message}
                        </div>
                    </c:if>
                    <img class="card-img" src="resources/imgs/Logo Muni.PNG" id="pic" />
                    <div class="form-group">
                        <input id="usr" type="text" name="usr" class="form-control" maxlength="10" title="usrName" placeholder="Nombre Usuario" />
                    </div>
                    <div class="form-group">
                        <input id="pwd" type="password" name="pwd" value="" title="passUsuario" class="form-control" placeholder="Contraseña" />
                    </div>
                    <br />
                    <div class="form-group">
                        <input type="submit" name="aubmit" value="Ingresar" class="btn btn-lg btn-primary btn-block" />     
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
