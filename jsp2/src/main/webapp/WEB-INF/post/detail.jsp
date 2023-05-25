<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>POST</title>
</head>
<body>
    <header>
        <h1>포스트 상세보기 페이지</h1>
    </header>

    <nav>
        <ul>
            <li>
                <c:url value="/user/signout" var="signOut"></c:url>
                <span>${ signedInUser }</span>
                <a href="${ signOut }">로그아웃</a>
            </li>
            <li>
                <c:url var="mainPage" value="/"></c:url>
                <a href="${ mainPage }">메인 페이지</a>
            </li>
            <li>
                <c:url var="postList" value="/post"></c:url>
                <a href="${ postList }">포스트 목록 페이지</a>
            </li>
            <c:if test="${ signedInUser == post.author }">
                <li>
                    <c:url var="postModify" value="/post/modify">
                        <c:param name="id" value="${ post.id }"></c:param>
                    </c:url>
                    <a href="${ postModify }">포스트 수정</a>
                </li>
            </c:if>
        </ul>
    </nav>

    <main>
        <form>
            <div>
                <input type="text" value="${ post.id }" readonly />
            </div>
            <div>
                <input type="text" value="${ post.title }" readonly />
            </div>
            <div>
                <textarea rows="5" cols="80" readonly>${ post.content }</textarea>
            </div>
            <div>
                <input type="text" value="${ post.author }" readonly />
            </div>
            <div>
                <input type="text" value="${ post.createdTime }" readonly />
            </div>
            <div>
                <input type="text" value="${ post.modifiedTime }" readonly />
            </div>
        </form>
    </main>
</body>
</html>