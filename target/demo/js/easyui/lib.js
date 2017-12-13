// var IMGURL = "https://whlyw.net:441/Themes/Scripts/utf8-net/net/upload/image/";
function getAjax(url, parm, callBack,failBack) {
    jQuery.support.cors = true;
    $.ajax({
        type: 'post',
        dataType: "text",
        url: url,
        data: parm,
        cache: true,
        async: true, //异步
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            failBack();
        },
        success: function (msg) {
            var json = eval("(" + msg + ")");
            //            alert(msg);
            callBack(json);
        }
    });
}
function getAjax1(url, parm, callBack, failBack) {
    jQuery.support.cors = true;
    $.ajax({
        type: 'post',
        dataType: "text",
        url: url,
        data: parm,
        cache: false,
        async: false, //异步
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            failBack();
        },
        success: function (msg) {
            var json = eval("(" + msg + ")");
            //            alert(msg);
            callBack(json);
        }
    });
}
function getAjaxBackStr(url, parm, callBack, failBack) {
    jQuery.support.cors = true;
    $.ajax({
        type: 'post',
        dataType: "text",
        url: url,
        data: parm,
        cache: true,
        async: true, //异步
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            failBack();
        },
        success: function (msg) {
            
            //            alert(msg);
            callBack(msg);
        }
    });
}
//模拟表单下载文件
function hj_downFile(url,fileKeyName,fileName) {
    // var url = "${path}/file/down";
    // var fileName = row.fileName; "downFileName"
    var form = $("<form></form>").attr("action", url).attr("method", "post");
    form.append($("<input></input>").attr("type", "hidden").attr("name", fileKeyName).attr("value", fileName));
    form.appendTo('body').submit().remove();
}
//上传文件
function hj_ajaxUploadPic(url,formData,progressFun,sucBlock,failBlock) {
//var formData = new FormData();
//formData.append('file', $('#file')[0].files[0]);
//formData.append('COLOR', "黄色");
//formData.append('GOODSID', "1");
//formData.append('action', "addGoodType");
$.ajax({
    url:url,
    type: 'POST',
    cache: false,
    data: formData,
    processData: false,
    contentType: false,
    xhr: function(){
        var xhr = $.ajaxSettings.xhr();
        if(progressFun && xhr.upload) {
            xhr.upload.addEventListener("progress" , progressFun, false);
            return xhr;
        }}
}).done(function(res) {
	sucBlock(res);
}).fail(function(res) {
 failBlock(res);
});
}

//增加浏览次数
function addCount() {
    var routeListConditionParm = 'action=AddCount';
    getAjax('../Sys/Ajax/GetCount.ashx', routeListConditionParm, function (rs) {

    });
}
function showSpin() {
    //      var spinnerOpts = {
    //              lines: 11 // 共有几条线组成
    //              , length: 13 // 每条线的长度
    //              , width: 8 // 每条线的长度
    //              , radius: 19 // 内圈的大小
    //              , scale: 0.5 // Scales overall size of the spinner
    //              , corners: 0.1 // 圆角的程度
    //              , color: '#000' // #rgb or #rrggbb or array of colors
    //              , opacity: 0.1 // Opacity of the lines
    //              , rotate: 18 // 整体的角度（因为是个环形的，所以角度变不变其实都差不多）
    //              , direction: 1 // 1: clockwise, -1: counterclockwise
    //              , speed: 0.8 // 速度：每秒的圈数
    //              , trail: 55 //  高亮尾巴的长度
    //              , fps: 20 // Frames per second when using setTimeout() as a fallback for CSS
    //              , zIndex: 2e9 // z-index的值 2e9（默认为2000000000
    //              , className: 'spinner' // The CSS class to assign to the spinner
    //              , top: '50%' // Top position relative to parent
    //              , left: '50%' // Left position relative to parent
    //              , shadow: false // 是否要阴影
    //              , hwaccel: false // 是否用硬件加速
    //              , position: 'absolute' // Element positioning
    //      };
    //      var spinTarget = document.getElementById('content');
    //      new Spinner(spinnerOpts).spin(spinTarget);
    //      
    new Spinner().spin(document.getElementById('content'));
}

function hideSpin() {

    $(".spinner").remove();
}

function hj_showSpin(Id){
    if (document.getElementById(Id)) { new Spinner().spin(document.getElementById(Id)); }

}
function hj_noData(isHavaMoreData) {

    if (isHavaMoreData) {
        $(".dropload-refresh").text("↑上拉加载更多");
    } else {
        $(".dropload-refresh").text("暂无数据");
    }
}
function showNodataDiv(duration, elementId) {
    $("#" + elementId + "").css("position", "relative");
    var html = '<p class = "noDataP" style = "position:absolute;width:80px;height:20px;background-color:rgba(0,0,0,0.6);top:50%;left:50%;color:White;margin:-10px 0 0 -40px;text-align:center;border-radius:5px;font-size:13px;line-height:20px">暂无数据</p>';
    $("#"+elementId+"").append(html);
    setTimeout(function () {
        $(".noDataP").fadeOut(function () {
            $(".noDataP").remove();
        });
    }, duration*1000);
}
//跳转页面
function Urlhref(url) {
    window.location.href = url;
    return false;
}
/********
接收地址栏参数
key:参数名称
**********/
function GetQuery(key) {
var URL = document.URL;
var tmparray = URL.split("?");
if(tmparray.length == 1){
    return null;

}else
{
var str2 = tmparray[1];
var arr2 = str2.split("&");
for(i in arr2){
var str3 = arr2[i];
var arr3 = str3.split("=");
if(arr3[0] == key){
return arr3[1];
}

}
return null;
}
//    var search = location.search.slice(1); //得到get方式提交的查询字符串
//    var arr = search.split("&");
//    for (var i = 0; i < arr.length; i++) {
//        var ar = arr[i].split("=");
//        if (ar[0] == key) {
//            return ar[1];
//        }
//    }
//    return null;
}
//去掉字符串转义字符和特殊字符
var excludeSpecial = function (s) {
    // 去掉转义字符  
    s = s.replace(/(\n)+|(\r\n)+/g, "");
    // 去掉特殊字符  
    s = s.replace(/[\@\#\$\%\^\&\*\{\}\:\"\L\<\>\?]/);
    s = s.replace("/n", "");
    s = s.replace(/(\n)/g, "");
    s = s.replace(/(\t)/g, "");
    s = s.replace(/(\r)/g, "");
    s = s.replace(/<\/?[^>]*>/g, "");
    s = s.replace(/\s*/g, "");
    return s;
};
//<!--存cookie-->
function setCookie(c_name, value, expireTimes) {
    var times = new Date().getTime() + expireTimes * 1000;
    var exdate = new Date(times);
    //exdate.setDate(exdate.getDate()+expiredays)
    document.cookie = c_name + "=" + escape(value) +
    ((expireTimes == null) ? "" : ";expires=" + exdate.toGMTString()) + ";path=/";
}

//<!--获取cookie-->
function getCookie(c_name) {
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=")
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1
            c_end = document.cookie.indexOf(";", c_start)
            if (c_end == -1) c_end = document.cookie.length
            return unescape(document.cookie.substring(c_start, c_end))
        }
    }
    return ""
}

//获取当前日期的字符
function getNowDate() {
    var d = new Date();
    var years = d.getFullYear();
    var month = add_zero(d.getMonth() + 1);
    var days = add_zero(d.getDate());
    var hours = add_zero(d.getHours());
    var minutes = add_zero(d.getMinutes());
    var seconds = add_zero(d.getSeconds());
    var ndate = years + "-" + month + "-" + days + " " + hours + ":" + minutes;
    return ndate;
}
function hj_dateTurnToStr(tmpDate) {
    var d = tmpDate;
    var years = d.getFullYear();
    var month = add_zero(d.getMonth() + 1);
    var days = add_zero(d.getDate());
    var hours = add_zero(d.getHours());
    var minutes = add_zero(d.getMinutes());
    var seconds = add_zero(d.getSeconds());
    var ndate = years + "-" + month + "-" + days + " " + hours + ":" + minutes;
    return ndate;
}
function add_zero(temp) {
    if (temp < 10) return "0" + temp;
    else return temp;
}


//根据选中的图片获取到imgURL
function hj_getFileURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}
function showMessageDiv(duration, elementId, message) {
    var length = 13 * message.length;
    $("#" + elementId + "").css("position", "relative");
    var html = '<p class = "noDataP" style = "position:absolute;width:' + length + 'px;height:20px;background-color:rgba(0,0,0,0.6);top:50%;left:50%;color:White;margin:-10px 0 0 '+(-length/2)+'px;text-align:center;border-radius:5px;font-size:13px;line-height:20px">'+message+'</p>';
    $("#" + elementId + "").append(html);
    setTimeout(function () {
        $(".noDataP").fadeOut(function () {
            $(".noDataP").remove();
        });
    }, duration * 1000);
}