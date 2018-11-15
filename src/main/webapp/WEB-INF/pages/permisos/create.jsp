<%-- 
    Document   : create
    Created on : 08-nov-2018, 11:50:03
    Author     : cetecom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file='/WEB-INF/partials/head_partial.jsp'%>
    <body>
        <%@include file='/WEB-INF/partials/navbar_partial.jsp'%>
        <div class="card" id="main-panel">
            <div class="card-header">
                <h5>Ingresar Solicitud de Permiso</h5>
            </div>
            <div class="card-body">
                <c:if test="${not empty message}">
                    <div class="${styleClass}" role="alert">
                        ${message}
                    </div>
                </c:if>
                <form id="create-solicitud" style="padding: 0% 2% 0% 2%;margin:0" name="create-solicitud" method="post" action="createSolicitud" enctype="multipart/form-data">
                    <div class="form-group row">
                        <label for="tipoPer" class="col-sm-2 col-form-label">Tipo: </label>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <select id="tipoPer" name="tipoPer" class="form-control">
                                    <option value="Administrativo">Administrativo</option>
                                    <option value="Fallecimiento Familiar">Fallecimiento Familiar</option>
                                    <option value="Feriado Legal">Feriado Legal</option>
                                    <option value="Parental">Parental</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="fecInit" class="col-sm-2 col-form-label">Fecha de Inicio: </label>
                        <div class="col-sm-3">
                            <input id="fecInit" type="text" name="fecInit" class="form-control" title="Fecha de Inicio" />
                        </div>
                        <label for="fecFin" class="col-sm-2 col-form-label">Fecha de Término: </label>
                        <div class="col-sm-3">
                            <input id="fecFin" type="text" name="fecFin" class="form-control" title="Fecha de Inicio" />
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="desc" class="col-sm-2 col-form-label">Descripción: </label>
                        <div class="col-sm-8">
                            <textarea class="form-control" style="margin-top:2%" id="desc" name="desc" rows="5" maxlength="300"></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="doc" class="col-sm-4 col-form-label">Documento Complementario: </label>
                        <div class="col-sm-5">
                            <input type="file" id="doc" name="doc" class="form-control-file" accept=".docx,.png,.jpg,.pdf">
                        </div>
                    </div>
                    <div class="form-group text-center">
                        <button type="submit" style="margin-top:2%;margin-bottom:0" class="btn btn-primary">Ingresar</button>
                    </div>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $("#fecInit").datepicker();
                $("#fecFin").datepicker();
            });
        </script>
    </body>
</html>
