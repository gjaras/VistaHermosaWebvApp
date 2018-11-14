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
                <h5>Crear Usuario</h5>
            </div>
            <div class="card-body">
                <div class="container-fluid">
                    <form id="login-form" name="login-form" method="post" action="createUser">
                        <c:if test="${not empty message}">
                            <div class="${styleClass}" role="alert">
                                ${message}
                            </div>
                        </c:if>
                        <div class="form-group">
                            <label for="rut" >Rut</label>
                            <input id="rut" type="text" name="rut" class="form-control" maxlength="10" title="rutUsuario" placeholder="Rut Usuario" />
                        </div>
                        <div class="form-group">
                            <label for="nombre">Nombre</label>
                            <input id="nombre" type="text" name="nombre" class="form-control" maxlength="10" title="nombreUsr" placeholder="Nombre Usuario" />
                        </div>
                        <div class="form-group">
                            <label for="pwd">Pass</label>
                            <input id="pwd" type="password" name="pwd" value="" title="passUsuario" class="form-control" placeholder="Contraseña" />
                        </div>
                        <div class="form-group">
                            <label for="type">Tipo</label>
                            <select id="type" name="type" class="form-control">
                                <option value="Funcionario">usuario</option>
                                <option value="Encargado">encargado</option>
                                <option value="Administrador">admin</option>
                            </select>
                        </div>
                        <br />
                        <div class="form-group">
                            <input type="submit" name="aubmit" value="Crear" class="btn btn-lg btn-primary btn-block" />     
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
