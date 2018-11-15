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
                <div class="card" style="margin-left: 5%; margin-right: 5%;margin-top:0;margin-bottom:10px">
                    <div class="card-header">
                        <h6>Buscar</h5>
                    </div>
                    <div class="card-body">
                        <form id="search-user" style="padding: 0% 2% 0% 2%;margin:0" name="search-user" method="post" action="listUsers?page=1">
                            <div class="form-group row justify-content-center">
                                <label for="idUsr" class="col-sm-1 col-form-label">ID: </label>
                                <div class="col-sm-2">
                                    <input id="idUsr" type="text" name="idUsr" class="form-control" maxlength="10" title="idUsr" placeholder="Id" />
                                </div>
                                <label for="nombreUsr" class="col-sm-2 offset-sm-1 col-form-label">Nombre: </label>
                                <div class="col-sm-3">
                                    <input id="nombreUsr" type="text" name="nombreUsr" class="form-control" maxlength="10" title="nombreUsr" placeholder="Nombre Usuario" />
                                </div>
                            </div>
                            <div class="form-group row justify-content-center">
                                <label for="tipoUsr" class="col-sm-1 col-form-label">Tipo: </label>
                                <div class="col-sm-2">
                                    <div class="form-group">
                                        <select id="tipoUsr" name="tipoUsr" class="form-control">
                                            <option value="Funcionario">Funcionario</option>
                                            <option value="Encargado">Encargado</option>
                                            <option value="Administrador">Administrador</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-6"></div>
                            </div>
                            <div class="form-group row justify-content-center">
                                <button type="submit" style="margin-top:2%;margin-bottom:0" class="btn btn-primary">Buscar</button>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="container-fluid">
                    <c:choose>
                        <c:when test="${not empty members}">
                            <table class="table" style="border-collapse: collapse;border:solid 1px black;margin-bottom:0px">
                                <thead class="thead-dark">
                                    <tr>
                                        <th style="width:5%" scope="col">ID</th>
                                        <th style="width:31%" scope="col">Nombre</th>
                                        <th style="width:20%" scope="col">Tipo</th>
                                        <th style="width:20%" scope="col">Rut</th>
                                        <th style="width:24%" scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${members}" var="member" begin="${10*(param.page-1)}" end="${10*(param.page-1) + 9}">
                                        <tr>
                                            <td>${member.get('id')}</td>
                                            <td>${member.get('nombre')}</td>
                                            <td>${member.get('tipo')}</td>
                                            <td>${member.get('rut')}</td>
                                            <td align="center"><i id="ver_${member.get('id')}" class="far fa-eye fa-lg" title="ver" onclick="getDetails(this.id)"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="far fa-edit fa-lg" title="editar"></i>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-trash-alt fa-lg" title="borrar"></i></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            <%@include file='/WEB-INF/partials/paginator_partial.jsp'%>
                        </c:when>
                        <c:otherwise>
                            No hay usuarios con esos parámetros.
                        </c:otherwise> 
                    </c:choose>
                </div>
            </div>
        </div>
        <div id="view" title="Detalles">
            <div id="listIndex" hidden="true"></div>
            <div style="content-fluid">
                <div class="row">
                    <div class="col-sm-3">
                        id:
                    </div>
                    <div id="idDetails" class="col-sm-9">

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        nombre:
                    </div>
                    <div id="nombreDetails" class="col-sm-9">

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        rut:
                    </div>
                    <div id="rutDetails" class="col-sm-9">

                    </div>
                </div>
                <div class="row">
                    <div class="col-sm-3">
                        tipo:
                    </div>
                    <div id="tipoDetails" class="col-sm-9">

                    </div>   
                </div>
            </div>
        </div>
        <div id="delete" title="Dialog box">
            chao a todos
        </div>
    </body>
    <style>
        .fa-eye:hover {
            color: green;
        }
        .fa-edit:hover {
            color: blue;
        }
        .fa-trash-alt:hover {
            color: red;
        }
    </style>

    <script>
        $(document).ready(function () {
            $("#view").dialog({
                autoOpen: false,
                modal: true
            });

            $("#delete").dialog({
                autoOpen: false,
                modal: true
            });
        });

        function getDetails(idRaw) {
            var baseUrl = getBaseUrl();
            var accessToken = getAccessToken();
            var id = idRaw.split("_")[1];
            return $.ajax({
                url: baseUrl + "/IntegracionVistaHermosa/restWs/usuario/requestSingleData?id=" + id,
                headers: {
                    'Content-Type': 'application/json',
                    'accessToken': accessToken
                },
                method: 'GET',
                dataType: 'json',
                success: function (data) {
                    console.log('succes. This is the data: ' + data.id);
                    $("#view").dialog("open");
                    $("#idDetails").text(data.id);
                    $("#nombreDetails").text(data.nombre);
                    $("#rutDetails").text(data.rut);
                    $("#tipoDetails").text(data.tipo);
                },
                error: function (error) {
                    console.log('error: ' + error);
                }
            });
        }
    </script>
</html>
