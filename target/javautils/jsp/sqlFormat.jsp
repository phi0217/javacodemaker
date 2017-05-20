<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <title>以sql为原料生成</title>
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
                <h2>以sql为原料生成</h2></div>

            <form action="/sqlFormat" method="post">
                <div class="panel-body">
                    <p><span>生成路径</span><input name="path" class="title" type="text" required="required"
                                               value="${path}" placeholder="如：D:/"/></p>
                    <p><span>mapperName</span><input name="mapperName" class="title" type="text" required="required"
                                                     value="${mapperName}" placeholder="如：subjectDataMapper"/></p>
                    <p><span>dtoName</span><input name="dtoName" class="title" type="text" required="required"
                                                  value="${dtoName}" placeholder="如：SubjectDataDto"/></p>
                    <p><span>sql语句</span><textarea class="text" name="sql" required="required" placeholder="如：'subject_ownership'  VARCHAR(20), 回车分隔 (PowerDsigner原格式）">${sql}</textarea></p>
                    <p><span>sql注释（选填）</span><textarea class="text" name="anno"  placeholder="如：comment on column 't_subject_original'.'parameter_id' is 回车分隔 (PowerDsigner原格式）">${anno}</textarea></p>
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