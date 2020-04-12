<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }"/>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${path}/view/bootstrap/css/bootstrap.css">
    <script src="${path}/view/bootstrap/js/jquery-1.8.3.js"></script>
    <script src="${path}/view/bootstrap/js/jsencrypt.min.js"></script>
    <script type="text/javascript" src="${path}/view/bootstrap/js/bootstrap.js"></script>
    <script>
        $(function () {
            var filePuk = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZkk4HIDD7rhQW8BexZFvT+MocIT8zTIV0+G0pDwmtR3eUMpWj0vDzxfNZ8JYDBw2U85uJJBT//UD1DrRzcy9TqDwOS85Xba8ns9o3ERTNItXC4FZ7UlOT3kRoTw5eMoPKwunVCA9Ewl4cZ0Pn93pGq8XiFFIHhVmsJROIAsdjyQIDAQAB";
            var filePrk = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANmSTgcgMPuuFBbwF7FkW9P4yhwhPzNMhXT4bSkPCa1Hd5QylaPS8PPF81nwlgMHDZTzm4kkFP/9QPUOtHNzL1OoPA5Lzldtryez2jcRFM0i1cLgVntSU5PeRGhPDl4yg8rC6dUID0TCXhxnQ+f3ekarxeIUUgeFWawlE4gCx2PJAgMBAAECgYAn++czEAtwdVFj1cPE6fimP/aErOd8efecw3rsLMNOgO/M4G73qHB3nE1YngozjDxGv246CdOCJ/ykDIvFG8n6cxXRpEKk6M3STT71E+WjBB0cWtOu0kFMrs0Vt6ChpBTrZjrGnKviVrROX4SWicA/zv3syu3bls3t3wrxln9BMQJBAOyMn6HBKzgUVvQf1PBW0jAGDONre63SVHmqXYYM6lP9fG/YCIHJdLqaL3wl91BV9u1mnexJNN1QJ1hfcSASSisCQQDrdjKCgqXNT2uX04AUcPlLvcLpngTfbBmOZRAKl6inCUAPF060TSVAgOfSJIctKWEwbyt7YmHRlJmGNAiSQlPbAkADsxtYC05ivSazAMBy1djJuX6AHBE+IbkCgiCeVRthHujQUv+nEACMXpb5iRp6Hi4TfnRf2rFB+Nv0rOF3D/IrAkBTHbAKvc+FP8m9GF2X2aFGiT+qc6tIqsICr84PzyUrgWppaVLQ9oJ12Ir8dQR6fqbeEAALV9krwAbJhsiXyG4NAkB2EctXYSNNF4XH4LttBo0FyMdDt8BWB1r1bwnGbpWu3WPJr3W02YqUFP8YqQpBtmOBzIpcGvozYNZJamOddlAj";
            //点击上传按钮
            $("#subBtn").click(function () {
                //1.发送ajax请求从后端获取公钥
                $.ajax({
                    url: "${path}/getPublicKey",
                    type: "post",
                    beforeSend: function(xhr){
                        },//这里设置header
                    success: function (data) {  //成功时的处理。参数表示返回的数据
                        console.log("公钥是："+data);
                        //2.设置随机文本内容
                        var x = 100;
                        var y = 0;
                        var rand1 = parseInt(Math.random() * (x - y + 1) + y);
                        var rand2 = parseInt(Math.random() * (x - y + 1) + y);
                        var sid = rand1.toString()+rand2.toString();
                        //console.log("随机码是："+ sid );
                        $("#content").val(sid);
                        //获取加密jsencrypt对象
                        var encrypt = new JSEncrypt();
                        encrypt.setPublicKey(data);
                        var signature  = encrypt.encrypt(sid);
                        //console.log("加密后是："+signature);
                        //将表单数据存入formData中
                       var formData = new FormData($( "#fileUploadForm" )[0]);
                        $.ajax({
                            //几个参数需要注意一下
                            url: '${path}/fileUpload' ,//url
                            type: "POST",//方法类型
                            dataType: "json",//预期服务器返回的数据类型
                            data: formData,
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            beforeSend: function(xhr){
                                xhr.setRequestHeader('X-Signature',signature);
                                xhr.setRequestHeader('X-SID',sid);
                            },//这里设置header
                            success: function (res) {
                                $("#success").text(res);
                            },
                            error: function () {
                                $("#error").text("失败")
                            }
                        });
                    },
                    dataType:"json"
                });
            });


            //点击查看所有文件按钮
            $("#showButton").click(function () {
                //1.发送ajax请求从后端获取公钥
                $.ajax({
                    url: "${path}/getPublicKey",
                    type: "post",
                    success: function (data) {  //成功时的处理。参数表示返回的数据
                        console.log("公钥是："+data);
                        //2.设置随机文本内容
                        var x = 100;
                        var y = 0;
                        var rand1 = parseInt(Math.random() * (x - y + 1) + y);
                        var rand2 = parseInt(Math.random() * (x - y + 1) + y);
                        var sid = rand1.toString()+rand2.toString();
                        //console.log("随机码是："+ sid );
                        $("#content").val(sid);
                        //获取加密jsencrypt对象
                        var encrypt = new JSEncrypt();
                        encrypt.setPublicKey(data);
                        var signature  = encrypt.encrypt(sid);
                        //console.log("加密后是："+signature);
                        //将表单数据存入formData中
                        $.ajax({
                            //几个参数需要注意一下
                            url: '${path}/queryAllFileServlet' ,//url
                            type: "POST",//方法类型
                            dataType: "json",//预期服务器返回的数据类型
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            beforeSend: function(xhr){
                                xhr.setRequestHeader('X-Signature',signature);
                                xhr.setRequestHeader('X-SID',sid);
                            },//这里设置header
                            success: function (res) {
                                $("#tbody").empty();
                                for (var i=0;i<res.length;i++){
                                    $("#tbody").append("<tr><td>"+res[i].uuFileName+"</td>"+
                                        "<td>"+res[i].size+"</td>"+
                                        "<td>"+res[i].type+"</td>"+
                                        "<td>"+res[i].originFileName+"</td>"+
                                        "<td>"+res[i].createTime+"</td>"+
                                        "<td>"+res[i].fileDirectory+"</td>"
                                    )
                                }
                            }
                        });
                    },
                    dataType:"json"
                });
            });

            //点击文件下载按钮
            $("#fileDownLoad").click(function () {
                //1.发送ajax请求从后端获取公钥
                $.ajax({
                    url: "${path}/getPublicKey",
                    type: "post",
                    success: function (data) {  //成功时的处理。参数表示返回的数据
                        console.log("公钥是："+data);
                        //2.设置随机文本内容
                        var x = 100;
                        var y = 0;
                        var rand1 = parseInt(Math.random() * (x - y + 1) + y);
                        var rand2 = parseInt(Math.random() * (x - y + 1) + y);
                        var sid = rand1.toString()+rand2.toString();
                        //console.log("随机码是："+ sid );
                        $("#content").val(sid);
                        //获取加密jsencrypt对象
                        var encrypt = new JSEncrypt();
                        encrypt.setPublicKey(data);
                        var signature  = encrypt.encrypt(sid);
                        var uuFileName = $("#uuFileName").val();
                        var PrivateKey = $("#PrivateKey").val();
                        $.ajax({
                            url: '${path}/fileDecode' ,//url
                            type: "get",//方法类型
                            dataType: "json",//预期服务器返回的数据类型
                            data: { uuFileName: uuFileName, PrivateKey: PrivateKey },
                            beforeSend: function(xhr){
                                xhr.setRequestHeader('X-Signature',signature);
                                xhr.setRequestHeader('X-SID',sid);
                            },//这里设置header
                            success: function (res) {
                                location.href = "${path}/fileDownLoad";
                                $("#downSuccess").text("下载成功");
                            },
                            error: function () {
                                $("#downError").text("下载失败")
                            }
                        });
                    },
                    dataType:"json"
                });
            })
        })
    </script>


    <title>文件系统</title>
</head>
<body>
<h1 style="text-align: center;">文件上传/下载系统</h1>
<div class="container-fluid">
    <span id="success"></span>
    <span id="error"></span>
    <form id="fileUploadForm">
        <input type="file" name="upFile"  id="file_upload">
        <input type="button" value="上传" id="subBtn" class="btn-default btn">
        <input type="hidden" name="publicKey"  id="publicKey" value="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZkk4HIDD7rhQW8BexZFvT+MocIT8zTIV0+G0pDwmtR3eUMpWj0vDzxfNZ8JYDBw2U85uJJBT//UD1DrRzcy9TqDwOS85Xba8ns9o3ERTNItXC4FZ7UlOT3kRoTw5eMoPKwunVCA9Ewl4cZ0Pn93pGq8XiFFIHhVmsJROIAsdjyQIDAQAB">
    </form>
</div>
<hr/>
<div class="container-fluid">
    <div style="text-align: center">
        <label href="showButton">点击按钮查询所有文件</label>
        <input type="button" value="查看文件" id="showButton" class="btn btn-default">
    </div>
    <table border="1" id="table" class="table table-striped">
        <thead>
            <tr>
                <td>文件名</td>
                <td>文件大小</td>
                <td>文件类型</td>
                <td>原始文件名</td>
                <td>创建时间</td>
                <td>存放目录</td>
            </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
    </table>
</div>
<hr/>
<div class="container-fluid">
    <h3>文件下载</h3>
    <span id="downSuccess"></span>
    <span id="downError"></span>
    <form id="fileDownLoadForm">
        <input id="uuFileName" type="text" name="uuFileName" placeholder="输入文件名(UUFIleName)">
        <input id="PrivateKey" type="hidden" name="PrivateKey" value="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANmSTgcgMPuuFBbwF7FkW9P4yhwhPzNMhXT4bSkPCa1Hd5QylaPS8PPF81nwlgMHDZTzm4kkFP/9QPUOtHNzL1OoPA5Lzldtryez2jcRFM0i1cLgVntSU5PeRGhPDl4yg8rC6dUID0TCXhxnQ+f3ekarxeIUUgeFWawlE4gCx2PJAgMBAAECgYAn++czEAtwdVFj1cPE6fimP/aErOd8efecw3rsLMNOgO/M4G73qHB3nE1YngozjDxGv246CdOCJ/ykDIvFG8n6cxXRpEKk6M3STT71E+WjBB0cWtOu0kFMrs0Vt6ChpBTrZjrGnKviVrROX4SWicA/zv3syu3bls3t3wrxln9BMQJBAOyMn6HBKzgUVvQf1PBW0jAGDONre63SVHmqXYYM6lP9fG/YCIHJdLqaL3wl91BV9u1mnexJNN1QJ1hfcSASSisCQQDrdjKCgqXNT2uX04AUcPlLvcLpngTfbBmOZRAKl6inCUAPF060TSVAgOfSJIctKWEwbyt7YmHRlJmGNAiSQlPbAkADsxtYC05ivSazAMBy1djJuX6AHBE+IbkCgiCeVRthHujQUv+nEACMXpb5iRp6Hi4TfnRf2rFB+Nv0rOF3D/IrAkBTHbAKvc+FP8m9GF2X2aFGiT+qc6tIqsICr84PzyUrgWppaVLQ9oJ12Ir8dQR6fqbeEAALV9krwAbJhsiXyG4NAkB2EctXYSNNF4XH4LttBo0FyMdDt8BWB1r1bwnGbpWu3WPJr3W02YqUFP8YqQpBtmOBzIpcGvozYNZJamOddlAj">
        <input type="button" value="立即下载" id="fileDownLoad" class="btn btn-default" >
    </form>
</div>

</body>
</html>