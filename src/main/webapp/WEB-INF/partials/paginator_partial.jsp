<%-- 
    Document   : paginator_partial
    Created on : Nov 14, 2018, 2:29:19 PM
    Author     : gabriel.jara
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div  class='d-flex'>
    <ul class='pagination mx-auto' style="margin: 0px;" >
        <li class="page-item <c:if test="${param.page == 1}">disabled</c:if>">
            <a class="page-link" href="listUsers?page=${param.page - 1}" aria-label="Previous">
                <span aria-hidden="true">&laquo;</span>
                <span class="sr-only">Previous</span>
            </a>
        </li>
        <c:forEach var="i" begin="1" end="${pages}" step="1">
            <li class="page-item <c:if test="${i == param.page}">active</c:if>"><a class="page-link" href="listUsers?page=${i}">${i}</a></li>
        </c:forEach>
        <li class="page-item <c:if test="${param.page == pages}">disabled</c:if>">
            <a class="page-link" href="listUsers?page=${param.page + 1}" aria-label="Next">
                <span aria-hidden="true">&raquo;</span>
                <span class="sr-only">Next</span>
            </a>
        </li>
    </ul>
</div>
<style>
    .pagination>li>a{
        background-color: lightblue;
    }

    .pagination>li.page-item.disabled>a{
        background-color: lightblue;
    }

    .pagination>li.active>a {
        background: lightsteelblue !important;
        border-color: steelblue !important;
        color: #fff;
    }
</style>