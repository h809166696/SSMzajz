var IMGURL = "http://www.zangaijiazu.cn/Images/";
//出现加载框
function showProgress(message) {
    if(message == null){
        message = "正在处理，请稍候。。。";
    }
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height(),zIndex:99999}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html(message).appendTo("body").css({zIndex:99999,display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
}
//关闭加载框
function closeProgress() {
    if(document.getElementsByClassName("datagrid-mask")){
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }

}
//时间替换最后的.0
function formatterDate(value,row,index) {
    return value.replace(".0","");
}
//修改datagrid对应的url且为第一页
function initDatagrid(ele,url) {

    var $getPager = ele.datagrid('getPager');
    var $pagination = $($getPager).pagination("options");
    if ($pagination != undefined) {
        $pagination.pageNumber = 1;
    }

    ele.datagrid('options').pageNumber=1
    ele.datagrid('options').pagenumber='1'
    // var url = "${path}/user/datagrid?NAME="+searchValue; // 重新赋值url 属性
    ele.datagrid('options').url = url;
    ele.datagrid('reload')
}

