<%--
  Created by IntelliJ IDEA.
  User: hongjin
  Date: 2017/12/12
  Time: 下午4:08
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/jsp/include/easyui_core.jsp"%>
<html>
<head>
    <title>文件管理</title>
    <script>
        var msgTitle,url;
        $(function () {
            $('#datagrid').datagrid({
                fit: true,
                url: "${path}/file/getDataGrid",
                title: "文件管理界面",
                loadMsg: "正在加载文件数据，请稍等...",
                idField: 'fileName',
                pagination: true,
                border: false,
                nowrap: true,
                fitColumns: true,
                singleSelect: true,
                striped: true,
                rownumbers: true,
                columns: [[
                    { title: '文件名称', field: 'fileName', width: 100 },
                    { title: '上传时间', field: 'addtime',formatter:formatterDate, width: 100 },
                    { title: '下载栏目', field: 'action',formatter:formatterDown,width: 60 }
//                { title: '图片', field: 'ARTICLEIMG', width: 100 },

//                { title: '入职时间', field: 'regtime', align: "center",formatter:formatterTime, width: 90 },

                ]],
                onLoadSuccess: function (data) {
//                    console.log(data)
                    if (data.rows.length > 0) {
                        $('#datagrid').datagrid("selectRow", 0);
                    }
                }
            });
        })
        function formatterDown() {
            return '<a href="javascript:downloadFile()">下载文件</a>';
        }
        //下载
        function downloadFile() {
            var row = $('#datagrid').datagrid("getSelected");
            if(row){
                hj_downFile("${path}/file/down","downFileName",row.fileName);

            }else{
                $.messager.alert("提示","请先选择文件","error")
            }

        }



        function uplode() {
            var formData = new FormData();
            var file = $('#file1')[0].files[0];
            formData.append("file",file);
            $.messager.progress({title:'上传进度',msg:'正在上传',text:'正在上传，请稍等！ ',interval:0});
            hj_ajaxUploadPic("${path}/file/upload",formData,listenProgress,function(res) {
                var json = eval("("+res+")")
                if(json.success){
                    var fileName = json.obj.fileName;
                    var nowdate=getNowDate();

                    getAjax("${path}/file/addFile","fileName="+fileName+"&addtime="+nowdate+"",function (json) {
                        if(json.success){
                            alert("上传成功")
                            $("#datagrid").datagrid("reload")
                            $.messager.progress('close');
                        }
                    },function () {
                        $.messager.progress('close');
                    })
                }else {
                    alert("上传文件失败")
                    $.messager.progress('close');
                }

//                console.log(res);
            },function(res) { $.messager.progress('close'); alert("上传失败");});

        }
        /**
          * 侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
          */
        function listenProgress(evt) {
            var loaded = evt.loaded;     //已经上传大小情况
            var tot = evt.total;      //附件总大小
            var per = Math.floor(100*loaded/tot);  //已经上传的百分比
          var bar =  $.messager.progress('bar');
          bar.progressbar('setValue', per);
//            $("#son").html( per +"%" );
//            $("#son").css("width" , per +"%");
//            console.log("总的"+tot+"上去的"+loaded+"百分比"+per);
        }
        function addFile() {
            $('#dlg').dialog("open").dialog("setTitle","上传文件");
            msgTitle = "上传成功"

        }
        function reload() {
            $('#datagrid').datagrid("reload")

        }
        function FindData() {
            var searchValue = $("#search_username").val();
            if (searchValue == ""){
                $.messager.alert("提示","请先输入文件名称","info");
            }else{
                initDatagrid($('#datagrid'),"${path}/file/getDataGrid?fileName="+searchValue+"");
            }

        }
        function cancelSearch() {
            initDatagrid($('#datagrid'),"${path}/file/getDataGrid");
        }
    </script>
</head>
<body class="easyui-layout" fit="true">
<!-- 员工列表 -->
<table id="datagrid" toolbar="#toolbar"></table>
<div id="toolbar">
    <a href="javascript:void(0);" class="easyui-linkbutton"
       iconCls="icon-reload" plain="true" onclick="reload();">刷新</a>
    <a href="javascript:void(0);" class="easyui-linkbutton"
       iconCls="icon-edit" plain="true" onclick="addFile();">上传</a>

    <%--<a href="javascript:void(0);" class="easyui-linkbutton"--%>
    <%--iconCls="icon-remove" plain="true" onclick="deleteUser();">删除</a>--%>
    <span>搜索内容:</span><input name="search_username" id="search_username" value="" size=10 />
    <a href="javascript:FindData()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="cancelSearch();" >取消查询</a>
    <%--<a href="javascript:void(0);" class="easyui-linkbutton"--%>
    <%--iconCls="icon-jright" plain="true" onclick="searchUser();">查询</a>--%>
</div>


<div id="dlg" class="easyui-dialog"
     style="width:400px;height:300px;padding:30px 20px" closed="true"
     buttons="#dlg-buttons">
    <input type="file" id="file1">
    <a href="#" class="easyui-linkbutton" style="width:100%" onclick="uplode()">Upload</a>
    <%--<div id="fileUploadProgress" closed="true" class="easyui-progressbar" style="width:400px;height:15px"></div>--%>
</div>
<!-- 添加/修改对话框按钮 -->
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6"
       iconCls="icon-ok" onclick="uplode()" style="width:90px">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton"
       iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
       style="width:90px">取消</a>
</div>
</body>
</html>
