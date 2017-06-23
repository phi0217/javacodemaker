<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <title>接口文档生成</title>
    <link rel="stylesheet" href="/css/style.css"/>
    <link rel="stylesheet" href="/css/common.css"/>
    <script type="text/javascript" src="/js/jquery.min.js"></script>
</head>


<body>
<div class="wrapper">
    <!--头部-->
    <jsp:include page="/jsp/common/head.jsp"></jsp:include>
    <!--主体-->
    <div class="main">
        <div class="panel">
            <div class="panel-head">
                <h2>接口文档生成</h2></div>

            <form action="/doc" method="post">
                <div class="panel-body">
                    <p><span>接口路径</span><input name="interfacePath" class="title" type="text" required="required"
                                               placeholder="如：D:\WorkSpace\src\main\java\com\xinfengtech\fund\interfaces\service\externalaccount\ExternalAccountService.java" value="${interfacePath}"/></p>
                    <p><span>实体类路径</span><input name="paramPath" class="title" type="text" required="required"
                                               placeholder="如：D:\WorkSpace\src\main\java\com\xinfengtech\fund\schema\externalaccount" value="${paramPath}"/></p>
                    <p><span>接口所在项目名</span><input name="interfaceJar" class="title" type="text" required="required"
                                               placeholder="如：com.xinfengtech.fund.interfaces" value="${interfaceJar}"/></p>
                    <p><span>实体类所在项目名</span><input name="paramJar" class="title" type="text" required="required"
                                               placeholder="如：com.xinfengtech.fund.schema" value="${paramJar}"/></p>
                    <p><span>生成路径</span><input name="path" class="title" type="text" required="required"
                                               placeholder="如：d:\" value="${path}"/></p>
                </div>
                <div class="panel-foot lightgray">
                    <button type="submit" class="btn blue">生成</button>
                </div>
            </form>
            <textarea class="text" style="width: 100%;font: 14px/26px '微软雅黑';color: #333;min-height: 800px;">${res}</textarea>
        </div>
    </div>
</div>
<c:if test="${not empty msg}">
    <script type="text/javascript">
        alert("${msg}");
    </script>
</c:if>
<script type="text/javascript" src="/js/news.js"></script>

</body>

</html>
