<%--
  Created by IntelliJ IDEA.
  User: hongjin
  Date: 2017/12/7
  Time: 下午10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户管理</title>
    <%@include file="/WEB-INF/jsp/include/easyui.jsp"%>
    <title>角色管理</title>

        <script>

        $(function () {
            //1.2、加载所选组织机构下的员工列表
            $('#datagrid').datagrid({
                fit: true,
                url: "${path}/sysrole/datagrid",
                title: "角色界面",
                loadMsg: "正在加载角色信息，请稍等...",
                idField: 'id',
                pagination: true,
                border: false,
                nowrap: false,
                fitColumns: true,
                singleSelect: true,
                striped: true,
                rownumbers: true,
                columns: [[
                    { title: '编号', field: 'id', width: 100 },
                    { title: '角色名称', field: 'name', width: 100 },
                    { title: '权限', field: 'action', width: 35,formatter:formatterAction, align: 'center' }
//                    { title: '账号', field: 'ACCOUNT', width: 100 },
//                    { title: '密码', field: 'PASSWORD', width: 100 },
//                    { title: '入职时间', field: 'regtime', align: "center",formatter:formatterTime, width: 90 },
//                    { title: '头像地址', field: 'HEADURL', width: 80 },
//                    { title: '角色ID', field: 'roleId', width: 80 },
//                    { title: '角色', field: 'roleName', width: 80 }
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
        function formatterAction(value,row,index) {
            return "<a href=\"javascript:menuManager()\">管理权限</a>";
        }
        //弹出管理权限界面
        function menuManager() {

        }
        function addRole() {
            $('#fm').form('clear');
            url = path+"/sysrole/saveRole";
            msgContent = "新增角色成功";


            $('#dlg').dialog('open').dialog('setTitle','新增角色');
        }
        function editRole() {
            $('#fm').form('clear');
            var row = $('#datagrid').datagrid('getSelected');

            if (row){
                var id = row.id;
                $('#dlg').dialog('open').dialog('setTitle','编辑角色');
                $('#fm').form('load',row);


                url = path+"/sysrole/saveRole?id="+id;
                msgContent = "编辑角色成功";
            }else{
                $.messager.alert('提示','请选择要编辑得角色','error');

            }
        }
        function deleteRole() {
            var row = $('#datagrid').datagrid('getSelected');
            if (row){
                var id = row.id;
                $('#dlg_delete').dialog('open').dialog('setTitle','删除角色');
//                $('#deletefm').form('load',row);
                url = path+"/sysrole/deleteRole?id="+id;
                msgContent = "删除角色成功";
            }else {
                $.messager.alert('提示','请选择要删除的角色','error');
            }
        }

        function saveRole() {

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
                        msgContent = "操作失败";
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

            initDatagrid($('#datagrid'),"${path}/sysrole/datagrid?name="+searchValue)
        }
        function cancelSearch() {
//       var searchValue = $('#search_username').val();

            initDatagrid($('#datagrid'),"${path}/sysrole/datagrid")
        }
        //权限分配
        function menuManager() {
            var row = $('#datagrid').datagrid("getSelected");
            if (row){
                url = "${path}/sysrole/changeRoleMenu?roleid="+row.id;
                msgContent = "编辑权限成功";
                $("#dlg_quanxian").dialog("open").dialog("setTitle","编辑权限");
                $("#quanXianUl").tree({
                    url:"${path}/getMenuByRole?id="+row.id,
                    parentField:"pid",

                    animate:true,
                    checkbox:true,
                    onLoadSuccess:function(node,data){
//                    console.log(data)
                    }
                    })
            }else {
                $.messager.alert("提示","请选择要分配的角色","error");
            }
        }
        //role_menu模型
        function roleMenuModel(roleid,menuid) {
            this.role_id = roleid;
            this.menu_id=menuid;
        }
        //分配好权限后保存方法
        function setRoleQuanXian() {
//            $.messager.progress({
//                title:'请稍后',
//                msg:'正在分配中...'
//            });
            showProgress();

//            document.getElementsByClassName("datagrid-mask").style.zIndex = 999999;
            //获取到所有的节点 包括父节点
              var selectTreeArray = $('#quanXianUl').tree("getCheckedExt");
              var modelArray = [];
              var roleId = url.split("=")[1];
       for (var index in selectTreeArray){
           var node = selectTreeArray[index];
           var rolemenuM = new roleMenuModel(roleId,node.id);
           modelArray.push(rolemenuM);
       }
            $.ajax({
                type:"post",
                url:"${path}/sysrole/changeRoleMenu",
//                contentType:"application/json;charset=utf-8",
//                JSON.stringify(modelArray)
                data:{"roleMenuStr":JSON.stringify(modelArray)},
                success:function(result){
                    var result = eval('('+result+')');
                    if (result.success){
                        $('#dlg_quanxian').dialog('close');
//                        $('#datagrid').datagrid('reload');
                    } else {
                        msgContent = '分配权限失败';
                    }
                    $.messager.show({
                        title: msgContent,
                        msg: result.msg
                    });
//                    alert(data);
   closeProgress()
//                    $.messager.progress('close');
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                    try {
                        parent.$.messager.progress('close');
                        parent.$.messager.alert('错误', XMLHttpRequest.responseText);
                    } catch (e) {
                        alert(XMLHttpRequest.responseText);
                    }
                    closeProgress()
//                    $.messager.progress('close');
                }
            });
//              console.log(selectTreeArray);
        }



        </script>
</head>
<body class="easyui-layout" fit="true">
<div region="center" border="false" style="overflow: hidden;">
    <!-- 用户信息列表 title="用户管理" -->

    <!-- 员工列表 -->
    <table id="datagrid" toolbar="#toolbar"></table>
    <!-- 按钮 -->
    <div id="toolbar">
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-add" plain="true" onclick="addRole();">新增</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-edit" plain="true" onclick="editRole();">编辑</a>
        <a href="javascript:void(0);" class="easyui-linkbutton"
           iconCls="icon-remove" plain="true" onclick="deleteRole();">删除</a>
        <span>角色名:</span><input name="search_username" id="search_username" value="" size=10 />
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
                <label>角色名称:</label> <input name="name" class="easyui-textbox" required="true">
            </div>

        </form>
    </div>

    <!-- 添加/修改对话框按钮 -->
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="saveRole()" style="width:90px">保存</a>
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
            <label>确定删除角色吗？</label>
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


  <%--分配权限对话框--%>
 <div id="dlg_quanxian" class="easyui-dialog"style="width:500px;height:400px;padding:30px 20px" closed="true" buttons="#dlg-quanxian-buttons">
     <ul id="quanXianUl"></ul>
 </div>
    <!-- 分配权限对话框按钮 -->
    <div id="dlg-quanxian-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6"
           iconCls="icon-ok" onclick="setRoleQuanXian()" style="width:90px">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
           iconCls="icon-cancel" onclick="javascript:$('#dlg_quanxian').dialog('close')"
           style="width:90px">取消</a>
    </div>
</div>
</body>
</html>
