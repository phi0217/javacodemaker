<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <title>根据Bean属性生成</title>
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
                <h2>根据Bean属性生成</h2></div>

            <form action="/bean" method="post">
                <div class="panel-body">
                    <p><span>源格式</span><input class="title" type="text" required="required" style="background: #efefef"
                                               value="private String subjectCode;//会计科目名称    #type# 类型 #name# 字段名 #uppercase# 首字母大写的字段名 #text# 注释 #enter# 回车" readonly="readonly"/></p>
                    <p><span>目标格式</span><input name="targetFormat" class="title" type="text" required="required"
                                                      placeholder="如：#type# #name#，"/></p>
                    <p><span>源语句</span><textarea class="text" name="srcTest" required="required" placeholder="如：private String subjectCode;//会计科目名称    回车分隔 注释可以没有">${srcTest}</textarea></p>
                    <p><span>set模板</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="req.set#uppercase#();  测试用    req.set#uppercase#(#name#);  control用" readonly="readonly"/></p>
                    <p><span>字段添加注解模板</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="     /**#enter#     * #text##enter#     */#enter#@ParamElement(required=true)#enter#private #type# #name#;" readonly="readonly"/></p>
                    <p><span>control入参模板1</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="#type#  #name#," readonly="readonly"/></p>
                    <p><span>control入参模板2</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="@RequestParam(value = &#34;#name#&#34;, required = false) #type# #name#," readonly="readonly"/></p>
                    <p><span>表头模板</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="<th>#text#</th>" readonly="readonly"/></p>
                    <p><span>搜索查询参数回写</span><input class="title" type="text" required="required" style="background: #efefef"
                                              value="if (!BaseBeanUtils.isNull(#name#)){#enter#			dto.set#uppercase#(#name#);#enter#		}" readonly="readonly"/></p>
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