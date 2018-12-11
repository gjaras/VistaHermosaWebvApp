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
                Validar Solicitud
            </div>
            <div class="card-body">
                <form id="validate-form" name="login-form" method="post" action="login">
                    <c:if test="${not empty message}">
                        <div class="${styleClass}" role="alert">
                            ${message}
                        </div>
                    </c:if>
                    <br />
                    <div class="form-group">
                        <input id="usr" type="text" name="usr" class="form-control" maxlength="10" title="usrName" placeholder="Codigo de Validación" />
                    </div>
                    <br />
                    <div class="form-group">
                        <input type="submit" name="submit" value="Validar" class="btn btn-lg btn-primary btn-block" />     
                    </div>
                </form>
            </div>
        </div>
        <div id="accept" title="Aceptar">
            <form id="delete-user" name="delete-user" method="post" action="changeSolState">
                <div class="form-group">
                    Este documento es Válido
                </div>

                <div class="form-group text-center" >
                    <button id="submitBtn" type="submit" class="btn btn-success">Aceptar</button>
                </div>
            </form>
        </div>
    </body>
    <script>
        $(document).ready(function () {
            $("#accept").dialog({
                autoOpen: false,
                modal: true
            });

            var form = document.getElementById("validate-form");

            form.addEventListener("submit", validate, true);
        });

        function validate(e) {
            e.preventDefault();
            $("#accept").dialog("open");
        }
    </script>
</html>
