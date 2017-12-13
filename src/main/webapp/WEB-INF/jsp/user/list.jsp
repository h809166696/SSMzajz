<%--
  Created by IntelliJ IDEA.
  User: hongjin
  Date: 2017/11/20
  Time: 下午10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>
    <script>

        $(function () {

            $('#datagrid').datagrid({
                fit: true,
                url: "${path}/user/datagrid",
                title: "用户界面",
                loadMsg: "正在加载用户数据，请稍等...",
                idField: 'UID',
                pagination: true,
                border: false,
                nowrap: false,
                fitColumns: true,
                singleSelect: true,
                striped: true,
                rownumbers: true,
                columns: [[
                    { title: '编号', field: 'UID', width: 100 },
                    { title: '姓名', field: 'NAME', width: 100 },
                    { title: '性别', field: 'SEX', width: 35,formatter:formatterSex, align: 'center' },
                    { title: '账号', field: 'ACCOUNT', width: 100 },
                    { title: '密码', field: 'PASSWORD', width: 100 },
                    { title: '入职时间', field: 'regtime', align: "center",formatter:formatterTime, width: 90 },
                    { title: '头像地址', field: 'HEADURL', width: 80 },
                    { title: '角色ID', field: 'roleId', width: 80 },
                    { title: '角色', field: 'roleName', width: 80 }
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

        var url = "";
        var msgContent = "";
        function formatterTime(value,row,index) {
            if(value != null && value != ""){
                return value.split('.0')[0];
            }
           return value;
        }
        function formatterSex(value,row,index) {
            var  imgStr = "";
            if (row.SEX == 1){
            imgStr = '<img src="../js/easyui/themes/icons/boy.png" alt="男" title="男" />';
            }else if (row.SEX ==0){
                imgStr = '<img src="../js/easyui/themes/icons/girl.png" alt="女" title="女" />';
            }else{
                imgStr = '<img src="../js/easyui/themes/icons/unKnow.png" alt="未知" title="未知" />';
            }
            return imgStr;
        }
        function addUser() {
            $('#fm').form('clear');
            url = path+"/user/saveUser";
            msgContent = "新增用户成功";
            $('#roleInput').combobox({  //为下拉框赋值
                url:"${path}/sysrole/allRole?ver="+Math.random(),
                valueField: 'id',
                loadMsg: "正在加载用户信息，请稍等...",
                textField: 'name',
                panelWidth: 150,
                panelHeight: 'auto',
                value:"无",
                onLoadSuccess: function (data) {
//                    var json = eval('('+data+')');
//                    if (row.roleName == null){
//                    $(this).combobox("select", "0");
                    $(this).combobox('setValue',0);
//                        $(this).combobox('setValue',json[json.length-1].name);
//                    }else{
//                        $('#roleInput').combobox('setValue',row.roleName);
//                    }

                }
            });

            $('#dlg').dialog('open').dialog('setTitle','新增用户');
        }
        function editUser() {
            $('#fm').form('clear');
            var row = $('#datagrid').datagrid('getSelected');

            if (row){
                var id = row.UID;
                $('#dlg').dialog('open').dialog('setTitle','编辑用户');
                $('#fm').form('load',row);
                var rowName = row.roleName == null ? "无":row.roleName;
                $('#roleInput').combobox({  //为下拉框赋值
                    url:"${path}/sysrole/allRole?ver="+Math.random(),
                    valueField: 'id',
                    loadMsg: "正在加载用户信息，请稍等...",
                    textField: 'name',
                    panelWidth: 150,
                    panelHeight: 'auto',
//                    value:rowName,//设置默认值
                    onLoadSuccess: function (data) { //加载完成后,设置选中第一项
//                        var  json = eval("("+data+")")
                        var json = data;
                        $(this).combobox('setValue',row.roleId == null?0:row.roleId);
//                        var val = $(this).combobox("getData");
//                        for (var item in json) {
//                            if (item["name"] == row.roleName) {
//                                $(this).combobox("select", item["id"]);
//                            }
//                        }
                    }
                });

                url = path+"/user/saveUser?UID="+id+"&roleName="+row.roleName;
                msgContent = "编辑用户成功";
            }else{
                $.messager.alert('提示','请选择要编辑得用户','error');

            }
        }
        function deleteUser() {
            var row = $('#datagrid').datagrid('getSelected');
            if (row){
                var id = row.UID;
                $('#dlg_delete').dialog('open').dialog('setTitle','删除用户');
//                $('#deletefm').form('load',row);
                url = path+"/user/deleteUser?UID="+id;
                msgContent = "删除用户成功";
            }else {
                $.messager.alert('提示','请选择要删除的用户','error');
            }
        }

        function saveUser() {

            $('#fm').form('submit',{
                url:url,
                onSubmit:function () {
                    return $(this).form('validate');
                },
                success:function (result) {
                   var result = eval('('+result+')');
                   if (result.success){
                       $('#dlg').dialog('close');
                       $('#datagrid').datagrid('reload')
                   }else {
                       msgContent = "新增用户失败";
                   }
                   $.messager.show({
                       title:msgContent,
                       msg:result.msg
                   })
                }
            })
        }

        function saveUser_del() {
            $('#deletefm').form('submit',{
                url:url,
                success:function (result) {
                    var result = eval('('+result+')');
                    if (result.success){
                        $('#dlg_delete').dialog('close');
                        $('#datagrid').datagrid('reload');
                    } else {
                        msgContent = '删除用户失败';
                    }
                    $.messager.show({
                        title: msgContent,
                        msg: result.msg
                    });
                }
            })
        }
        function reload() {
            $('#datagrid').datagrid('reload');
        }
   function FindData() {
       var searchValue = $('#search_username').val();
       if(searchValue == null || searchValue == ""){
           $.messager.alert("提示","不能为空",'error');
       }else{

           initDatagrid($('#datagrid'),"${path}/user/datagrid?NAME="+searchValue)
   }
   }
   function cancelSearch() {

       initDatagrid($('#datagrid'),"${path}/user/datagrid")

   }


    </script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
<!-- 用户信息列表 title="用户管理" -->
<%--<table id="datagrid" class="easyui-datagrid"--%>
       <%--fit="true"--%>
       <%--url="${path}/user/datagrid"--%>
       <%--toolbar="#toolbar"--%>
       <%--pagination="true"--%>
       <%--fitColumns="true"--%>
       <%--singleSelect="true"--%>
       <%--rownumbers="true"--%>
       <%--striped="true"--%>
       <%--border="false"--%>
       <%--nowrap="false">--%>
    <%--&lt;%&ndash;<thead>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<tr>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="UID" width="100">序号</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="NAME" width="100">用户名</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="SEX" width="100">性别</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;&lt;%&ndash;<th data-options="field:'SEX',width:50,formatter:formatterSex">性别</th>&ndash;%&gt;&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="ACCOUNT" width="100">账号</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="PASSWORD" width="100">密码</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;<th field="HEADURL" width="100">头像地址</th>&ndash;%&gt;--%>
        <%--&lt;%&ndash;&lt;%&ndash;<th field="DESCRIPTION" width="100"></th>&ndash;%&gt;&ndash;%&gt;--%>

        <%--&lt;%&ndash;<th field="regtime" width="100">注册时间</th>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</tr>&ndash;%&gt;--%>
    <%--&lt;%&ndash;</thead>&ndash;%&gt;--%>
<%--</table>--%>
    <!-- 员工列表 -->
    <table id="datagrid" toolbar="#toolbar"></table>
    <!-- 按钮 -->
    <div id="toolbar">
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-add" plain="true" onclick="addUser();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-edit" plain="true" onclick="editUser();">编辑</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-remove" plain="true" onclick="deleteUser();">删除</a>
        <span>用户名:</span><input name="search_username" id="search_username" value="" size=10 />
        <a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelSearch();" >取消查询</a>
        <%--<a href="javascript:void(0);" class="easyui-linkbutton"--%>
           <%--iconCls="icon-jright" plain="true" onclick="searchUser();">查询</a>--%>
    </div>

    <!-- 添加/修改对话框 -->
    <div id="dlg" class="easyui-dialog"
         style="width:400px;height:400px;padding:30px 20px" closed="true"
         buttons="#dlg-buttons">
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>用户名:</label> <input name="ACCOUNT" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>密码:</label> <input name="PASSWORD" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>名称:</label> <input name="NAME" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>性别:</label>
                <input type="radio" name="SEX" id="gender" value=1 style="width:50px;">男</input>
                <input type="radio" name="SEX" id="gender" value=0 style="width:50px;">女</input>
            </div>
            <div class="fitem">
                <label>注册时间:</label> <input name="regtime" type="text" class="easyui-datebox" required="required"/>
            </div>
            <div class="fitem">
                <label>角色分配:</label> <input name="roleId" id="roleInput" value=""/>
            </div>
        </form>
    </div>

    <!-- 添加/修改对话框按钮 -->
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="saveUser()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
           style="width:90px">取消</a>
    </div>


    <!-- 删除对话框 -->
    <div id="dlg_delete" class="easyui-dialog"
         style="width:300px;height:200px;padding:30px 20px" closed="true"
         buttons="#dlg-del-buttons">
        <div class="ftitle">请谨慎操作</div>
        <form id="deletefm" method="post" novalidate>
            <label>确定删除用户吗？</label>
        </form>
    </div>

    <!-- 删除对话框按钮 -->
    <div id="dlg-del-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="saveUser_del()" style="width:90px">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_delete').dialog('close')"
           style="width:90px">取消</a>
    </div>
    <!-- 查询对话框 -->
    <div id="dlgsearch" class="easyui-dialog"
         style="width:400px;height:380px;padding:30px 20px" closed="true"
         buttons="#dlgsearch-buttons">
        <form id="fmsearch" method="post" novalidate>
            <div class="fitem">
                <label>名称:</label> <input name="NAME" class="easyui-textbox" >
            </div>

            <div class="fitem">
                <label>性别:</label>
                <input type="radio" name="gender" id="gender" value="" style="width:30px;">全部</input>
                <input type="radio" name="gender" id="gender" value="男" style="width:30px;">男</input>
                <input type="radio" name="gender" id="gender" value="女" style="width:30px;">女</input>
            </div>
            <div class="fitem">
                <label>入职时间:</label> <input name="regtime" type="text" class="easyui-datebox" />
            </div>
            <div class="fitem">
                <label>至</label> <input name="regtime" type="text" class="easyui-datebox" />
            </div>

        </form>
    </div>

    <!-- 查询对话框按钮 -->
    <div id="dlgsearch-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="searchUser()" style="width:90px">查询</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlgsearch').dialog('close')"
           style="width:90px">取消</a>
    </div>


</div>
</body>
</html>
