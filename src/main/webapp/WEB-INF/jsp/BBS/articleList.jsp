<%--
  Created by IntelliJ IDEA.
  User: hongjin
  Date: 2017/12/11
  Time: 下午5:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>
    <script>
        var msgTitle,url;
        $(function () {
        $('#datagrid').datagrid({
            fit: true,
            url: "${path}/BBS/getArticle",
            title: "帖子管理界面",
            loadMsg: "正在加载帖子数据，请稍等...",
            idField: 'ARTICLEID',
            pagination: true,
            border: false,
            nowrap: true,
            fitColumns: true,
            singleSelect: true,
            striped: true,
            rownumbers: true,
            columns: [[
                { title: '编号', field: 'ARTICLEID', width: 30 },
                { title: '帖子名称', field: 'ARTICLENAME', width: 100 },
                { title: '内容', field: 'ARTICLECONTENT', width: 100 },
//                { title: '图片', field: 'ARTICLEIMG', width: 100 },
                { title: '发表时间', field: 'ARTICLEDATE',formatter:formatterDate, width: 100 },
//                { title: '入职时间', field: 'regtime', align: "center",formatter:formatterTime, width: 90 },
                { title: '评论条数', field: 'DISCUSSCOUNT', width: 60 },
                { title: '观看次数', field: 'LOOKCOUNT', width: 60 },
                { title: '最后回复时间', field: 'lastEditTime',formatter:formatterDate, width: 100 },
                { title: '帖子状态', field: 'isDelete', width: 60 ,formatter:formatterStatus,align:"center"},
                { title: '是否精华', field: 'isJing', width: 60,formatter:formatterJudge,align:"center" },
                { title: '是否置顶', field: 'isDing', width: 60,formatter:formatterJudge,align:"center"},
                { title: '用户名称', field: 'NAME', width: 60,formatter:formatterName },
                { title: '用户头像', field: 'HEADURL', width: 40,formatter:formatterHeadurl,align:"center" }
//                    { title: '微信', field: 'weixin', width: 120 },
//                    { title: '邮箱地址', field: 'email', width: 150 }
            ]],
            onLoadSuccess: function (data) {
//                    console.log(data)
                if (data.rows.length > 0) {
                    $('#datagrid').datagrid("selectRow", 0);
                }else{
                    $.messager.alert("提示","无相关数据","error");
                }
            }
        });
        })

        function formatterStatus(value,row,index) {

            return  value==0 ? '<img src="../js/easyui/themes/icons/ok.png" alt="正常" title="正常" />' : '<img src="../js/easyui/themes/icons/stop.png" alt="禁用" title="禁用" />';
        }
        function formatterJudge(value,row,index) {
         return value == 1?"是":"否"
        }
        function formatterName(value,row,index) {

            return row.user.NAME;
        }
        function formatterHeadurl(value,row,index) {

            return '<img src="'+IMGURL+''+row.user.HEADURL+'" alt="正常" title="头像" style="width: 40px;height: 40px"/>';
        }

        function reload() {
            $('#datagrid').datagrid("reload");
        }

        function FindData() {
            var searchText = $('#search_username').val();
            if (searchText == null || searchText== ""){
                $.messager.alert("提示","请先输入搜索内容","error")

            }else {
                <%--$('.pagination-num').val(1);--%>
                <%--$('#datagrid').datagrid({--%>
                    <%--url:"${path}/BBS/getArticle?searchText="+searchText--%>
                <%--}).datagrid("reload")--%>
                initDatagrid($('#datagrid'),"${path}/BBS/getArticle?searchText="+searchText)
            }
        }
        function cancelSearch() {
            initDatagrid($('#datagrid'),"${path}/BBS/getArticle")
        }

        function editUser() {
              var row = $('#datagrid').datagrid("getSelected")
            console.log(row);
            if (row){
                  msgTitle = "编辑帖子成功"
                url = "${path}/BBS/editArticle?ARTICLEID="+row.ARTICLEID;
                $('#fm').form("clear");
                $('#fm #picContent').empty();

                  $('#fm').form("load",row);
                  $('#dlg').dialog("open").dialog("setTitle","编辑帖子")
                  var imgurlArray = row.ARTICLEIMG.split(',');
                  for (var index in imgurlArray){
                      var imgStr = '<img src="'+IMGURL+''+imgurlArray[index]+'" style="width: 100px;height: 100px"/>';
                  $('#fm #picContent').append(imgStr)
                  }

            }else {
                $.messager.alert("提示","请先选择要编辑的帖子","info");
            }
        }
        function saveChange() {
            showProgress()
            $('#fm').form('submit',{
                url:url,
                success:function (result) {
                    closeProgress()
                    var result = eval('('+result+')');
                    if (result.success){
                        $('#dlg').dialog('close');
                        $('#datagrid').datagrid('reload');
                    } else {
                        msgTitle = '编辑帖子失败';
                    }
                    $.messager.show({
                        title: msgTitle,
                        msg: result.msg
                    });

                }

            })
        }
    </script>
</head>
<body class="easyui-layout" fit="true">
<!-- 员工列表 -->
<table id="datagrid" toolbar="#toolbar"></table>
<!-- 按钮 -->
<div id="toolbar">
    <a href="javascript:void(0);" class="easyui-linkbutton"
       iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>

    <a href="javascript:void(0);" class="easyui-linkbutton"
       iconCls="icon-edit" plain="true" onclick="editUser();">编辑</a>
    <%--<a href="javascript:void(0);" class="easyui-linkbutton"--%>
       <%--iconCls="icon-remove" plain="true" onclick="deleteUser();">删除</a>--%>
    <span>搜索内容:</span><input name="search_username" id="search_username" value="" size=10 />
    <a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelSearch();" >取消查询</a>
    <%--<a href="javascript:void(0);" class="easyui-linkbutton"--%>
    <%--iconCls="icon-jright" plain="true" onclick="searchUser();">查询</a>--%>
</div>

<!-- 添加/修改对话框 -->
<div id="dlg" class="easyui-dialog"
     style="width:600px;height:500px;padding:30px 20px" closed="true"
     buttons="#dlg-buttons">
    <form id="fm" method="post" novalidate>
        <div class="fitem">
            <label>帖子名称:</label> <input name="ARTICLENAME" class="easyui-textbox" required="true" disabled="disabled" style="width: 400px">
        </div>
        <div class="fitem">
            <label>帖子内容:</label> <input disabled="disabled" class="easyui-textbox" name="ARTICLECONTENT" style="height:80px;width: 400px" data-options="multiline:true">
        </div>
        <div class="fitem">
            <label>帖子图片:</label> <div class="picContent" id="picContent"></div>
        </div>
        <div class="fitem">
            <label>是否加精:</label>
            <input type="radio" name="isJing"  value=1 style="width:50px;">是</input>
            <input type="radio" name="isJing"  value=0 style="width:50px;">否</input>
        </div>
        <div class="fitem">
            <label>是否置顶:</label>
            <input type="radio" name="isDing"  value=1 style="width:50px;">是</input>
            <input type="radio" name="isDing"  value=0 style="width:50px;">否</input>
        </div>
        <div class="fitem">
            <label>是否删除:</label>
            <input type="radio" name="isDelete"  value=1 style="width:50px;">是</input>
            <input type="radio" name="isDelete"  value=0 style="width:50px;">否</input>
        </div>

    </form>
</div>

<!-- 添加/修改对话框按钮 -->
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="saveChange()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
       style="width:90px">取消</a>
</div>
</body>
</html>
