<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<ol>
    <c:forEach items="${TARGET_PAGES}" var="pageLink">
        <li>
            <a href="<c:out value="${pageLink}"/>.form?sourcePage=<c:out value="${SOURCE_PAGE}"/>">${pageLink}</a>
        </li>
    </c:forEach>
</ol>
