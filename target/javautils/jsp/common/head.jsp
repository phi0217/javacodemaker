<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="/css/style.css" />
    <link rel="stylesheet" href="/css/common.css" />
    <script type="text/javascript" src="/js/jquery.min.js"></script>
    <script type="text/javascript" src="/js/panel.js"></script>
    <script type="text/javascript" src="/js/common.js"></script>
    <style type="text/css">
        a{color:white}
    </style>
</head>

<body>
<!--头部-->
<div class="header">
    <div class="layout">
        <h1 class="title">JavaUtils</h1>
    </div>
</div>
<div class="nav">
    <ul>
        <li class="news nav-item"><a href="/toSql" >以sql为原料生成</a></li>
        <li class="nav-item"><a href="/toMethod">以method为原料生成</a></li>
        <li class="nav-item"><a href="/toStandard">以模块名生成标准模板</a></li>
        <li class="nav-item"><a href="/toBean">以bean字段为原料生成</a></li>
        <li class="nav-item"><a href="/toInterfaceFormat">以interface为原料生成</a></li>
    </ul>
</div>