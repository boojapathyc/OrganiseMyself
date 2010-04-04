<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<ol>
    <c:forEach items="${TARGET_PAGES}" var="pageLink">
        <li>
            <c:if test="${pageLink.value != null}">
                <img src="<c:out value="${pageLink.value}" />" alt="${pageLink.key}"/>
            </c:if>
            <a href="<c:out value="${pageLink.key}"/>.form?sourcePage=<c:out value="${SOURCE_PAGE}"/>">${pageLink.key}</a>
        </li>
    </c:forEach>
</ol>