<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>


<table border="1" cellspacing="10px">
    <tr>
        <c:forEach items="${TARGET_PAGES}" var="pageLink">
            <td>
                <c:if test="${pageLink.value.content != null}">
                    <img width="180px" height="150px" src="<c:out value="${pageLink.value.content}" />"
                         alt="${pageLink.key}"/>
                </c:if>
                <br>
                <a href="<c:out value="${pageLink.key}"/>.form?sourcePage=<c:out value="${SOURCE_PAGE}"/>">${pageLink.value.displayName}</a>
            </td>
        </c:forEach>
    </tr>
</table>