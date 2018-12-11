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
                <div class="card" style="margin-left: 5%; margin-right: 5%;margin-top:0;margin-bottom:10px">
                    <div class="card-header">
                        <h6>Buscar</h5>
                    </div>
                    <div class="card-body" style="margin:0 3% 0 0;padding:0">
                        <form id="search-user" style="padding:2% 0% 0%;margin:0" name="search-user" method="post" action="listUsers?page=1">
                            <div class="form-group row justify-content-center">
                                <label for="idUsr" class="col-sm-1 col-form-label">ID: </label>
                                <div class="col-sm-2">
                                    <input id="idUsr" type="text" name="idUsr" class="form-control" maxlength="10" title="idUsr" placeholder="Id" />
                                </div>
                                <label for="tipoUsr" class="col-sm-1 offset-sm-1 col-form-label">Tipo: </label>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <select id="tipoUsr" name="tipoUsr" class="form-control">
                                            <option value="Funcionario">Administrativo</option>
                                            <option value="Encargado"></option>
                                            <option value="Administrador"></option>
                                        </select>
                                    </div>
                                </div>                 
                            </div>
                            <div class="form-group row justify-content-center">
                                <label for="nombreUsr" class="col-sm-2 col-form-label" style="padding-right:2;margin-right:0">Fecha Inicio: </label>
                                <div class="col-sm-2">
                                    <input id="nombreUsr" type="text" name="nombreUsr" class="form-control" maxlength="10" title="nombreUsr" placeholder="Fecha Inicio" />
                                </div>
                                <label for="nombreUsr" class="col-sm-2 col-form-label">Fecha Fin: </label>
                                <div class="col-sm-2">
                                    <input id="nombreUsr" type="text" name="nombreUsr" class="form-control" maxlength="10" title="nombreUsr" placeholder="Fecha Fin" />
                                </div>
                            </div>
                            <div class="form-group row justify-content-center">
                                <label for="tipoUsr" class="col-sm-1 col-form-label">Tipo: </label>
                                <div class="col-sm-3">
                                    <div class="form-group">
                                        <select id="tipoUsr" name="tipoUsr" class="form-control">
                                            <option value="Funcionario">Aceptados</option>
                                            <option value="Encargado"></option>
                                            <option value="Administrador"></option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-sm-4"></div>
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
                                    <c:forEach items="${members}" var="member"  begin="${10*(param.page-1)}" end="${10*(param.page-1) + 9}">
                                        <tr>
                                            <td><a href="#" >${member.get('id')}</a></td>
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
                            No existen solicitudes en el sistema.
                        </c:otherwise> 
                    </c:choose>
                </div>
            </div>
        </div>
        <div id="accept" title="Aceptar">
            <form id="delete-user" name="delete-user" method="post" action="changeSolState">
                <div class="form-group">
                    Esta seguro de querer aceptar esta Solicitud?
                </div>
                <input type="hidden" id="solIdAccept" name="solIdAccept"/>
                <input type="hidden" id="opType" name="opType" value="accept"/>
                <div class="form-group text-center" >
                    <button type="submit" class="btn btn-success">Aceptar</button>
                </div>
            </form>
        </div>
        <div id="reject" title="Rechazar">
            <form id="delete-user" name="delete-user" method="post" action="changeSolState">
                <div class="form-group">
                    Esta seguro de querer rechazar esta Solicitud?
                </div>
                <input type="hidden" id="solIdReject" name="solIdReject"/>
                <input type="hidden" id="opType" name="opType" value="reject"/>
                <div class="form-group text-center" >
                    <button type="submit" class="btn btn-danger">Rechazar</button>
                </div>
            </form>
        </div>
    </body>
    <style>
        .fa-check-circle:hover {
            color: green;
        }

        .fa-times-circle:hover {
            color: red;
        }
    </style>

    <script>
        $(document).ready(function () {
            $("#accept").dialog({
                autoOpen: false,
                modal: true
            });

            $("#reject").dialog({
                autoOpen: false,
                modal: true
            });
        });

        function accept(idRaw) {
             var id = idRaw.split("_")[1];
            $("#accept").dialog("open");
            $("#solIdAccept").val(id);
        }
        
        function reject(idRaw) {
             var id = idRaw.split("_")[1];
            $("#reject").dialog("open");
            $("#solIdReject").val(id);
        }
    </script>
</html>
