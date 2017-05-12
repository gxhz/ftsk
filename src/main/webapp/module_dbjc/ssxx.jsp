<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<%@include file="../head1.jsp" %> 

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时信息</title>

</head>

<body>
    <div style="position:absolute;width:1670px;height:1178px;background:url(../images/dmaq.jpg);overflow:auto;">
    
        <div style="position:absolute;left:692px;top:155px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:705px;top:145px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA1-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA1-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA1-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:600px;top:237px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:613px;top:227px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA2-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA2-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA2-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:562px;top:302px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:577px;top:292px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA3-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA3-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA3-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:420px;top:433px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA4-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:433px;top:423px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PA4-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PA4-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PA4-2">0m</span>
            </div>
        </div>
        
        
        
        
        
        
        
        
        <div style="position:absolute;left:822px;top:270px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB1-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB1-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB1-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:835px;top:260px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:728px;top:355px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB2-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB2-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB2-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:742px;top:345px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:675px;top:405px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB3-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB3-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB3-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:687px;top:395px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:601px;top:476px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB4-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB4-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB4-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:614px;top:466px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PB4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PB4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PB4-1">0m</span>
            </div>
        </div>
        
        
        
        
        
        <div style="position:absolute;left:973px;top:390px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:961px;top:400px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC1-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC1-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC1-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:878px;top:470px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:866px;top:480px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC2-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC2-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC2-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:828px;top:520px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:816px;top:530px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC3-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC3-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC3-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:748px;top:590px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC4-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:736px;top:600px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PC4-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PC4-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PC4-2">0m</span>
            </div>
        </div>
        
        
        
        
        
        <div style="position:absolute;left:1113px;top:506px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1123px;top:560px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD1-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD1-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD1-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1020px;top:589px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1030px;top:643px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD2-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD2-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD2-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:966px;top:636px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:978px;top:690px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD3-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD3-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD3-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:886px;top:708px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD4-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:900px;top:762px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PD4-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PD4-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PD4-2">0m</span>
            </div>
        </div>
        
        
        
        
        
        
        <div style="position:absolute;left:1258px;top:650px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1243px;top:660px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE1-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE1-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE1-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1160px;top:730px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1148px;top:740px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE2-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE2-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE2-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1105px;top:780px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1093px;top:790px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE3-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE3-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE3-2">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1000px;top:820px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:12px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:12px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE4-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:988px;top:830px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PE4-2');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PE4-2</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PE4-2">0m</span>
            </div>
        </div>
        
        
        
        
        
        
        <div style="position:absolute;left:1383px;top:877px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PY1-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PY1-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PY1-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1258px;top:927px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PY2-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PY2-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PY2-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:1075px;top:982px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PY3-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PY3-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PY3-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:885px;top:1037px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PY4-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PY4-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PY4-1">0m</span>
            </div>
        </div>
        
        <div style="position:absolute;left:702px;top:1095px;width:19px;height:13px;cursor:pointer;" onclick="showMsg('PY5-1');">
            <div style="border:1px solid #0099ff;position:absolute;width:45px;height:19px;left:0px;top:-20px;background:#0099ff;" align="center">
                <span style="color:#ffffff;text-align:center; ">PY5-1</span>
            </div>
            <div style="border:1px solid #0099ff;position:absolute;width:60px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="PY5-1">0m</span>
            </div>
        </div>
        
        
        
        
        <div style="position:absolute;left:605px;top:927px;width:19px;height:13px;cursor:pointer;" onclick="showSLLMsg('SLL-2');">
            <div style="border:1px solid #E9967A;position:absolute;width:55px;height:19px;left:-10px;top:-20px;background:#E9967A;" align="center">
                <span style="color:#ffffff;text-align:center; ">渗流量</span>
            </div>
            <div style="border:1px solid #E9967A;position:absolute;width:65px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="SLL-2">0L/s</span>
            </div>
        </div>
        
        <div style="position:absolute;left:547px;top:665px;width:19px;height:13px;cursor:pointer;" onclick="showSLLMsg('SLL-1');">
            <div style="border:1px solid #E9967A;position:absolute;width:55px;height:19px;left:-10px;top:-20px;background:#E9967A;" align="center">
                <span style="color:#ffffff;text-align:center; ">渗流量</span>
            </div>
            <div style="border:1px solid #E9967A;position:absolute;width:65px;height:19px;left:45px;top:-20px;background:#ffffff;" align="center">
                <span style="color:#000000;text-align:center; " id ="SLL-1">0L/s</span>
            </div>
        </div>
        
    </div> 
    
    <div style="position: fixed;top: 220px;left: 40px;z-index: 888; border:#000000 1px dashed; padding: 5px;">
        <p style="color:#000000" id="sysw">上游水位：0.0 m</p>
        <p style="color:#000000" id="xysw">下游水位：0.0 m</p>
        <p style="color:#000000" id="krl">库容量：0.0 万m3</p>
        <!-- 
        <a href="#" class="easyui-linkbutton" style="width:100%;height:35px" onclick="showDetailList()" >传感器详细列表</a>
         -->
    </div>
    
    <div id="w" class="easyui-window" title="传感器信息" 
        data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false"
        style="width:500px;height:200px;padding:10px;">
    </div>
    
    <div id="w_detail" class="easyui-window" title="详细信息列表" 
        data-options="modal:true,closed:true,collapsible:false,minimizable:false,maximizable:false"
        style="width:1600px;height:800px;padding:10px;">
        
        <div class="easyui-layout" style="width:100%;height:100%">
        
            <div data-options="region:'center',title:'渗压水位'">
                <table id="syj" class="easyui-datagrid" pagination="false" fit="true" fitColumns="true"
                     url="GetDBRTData" rownumbers="true" singleSelect="true" pageSize="200">
                    <thead>
                        <tr>
                          <th field="name" width="120">名称</th>
                          <th field="sysw" width="120">渗压水位</th>
                          <th field="msgc" width="120">埋设高程</th>
                          <th field="gkgc" width="120">管口高程</th>
                          <th field="plms" width="120">频率模数</th>
                          <th field="wd" width="120">温度</th>
                          <th field="mcubh" width="120">MCU编号</th>
                          <th field="mcutd" width="120">MCU通道</th>
                        </tr>
                    </thead>
                </table>
            </div>
            <div data-options="region:'south',title:'渗流量'" style="height:120px;">
                <table id="lsy" class="easyui-datagrid" pagination="false" fit="true" fitColumns="true"
                     url="GetDBRTData" rownumbers="true" singleSelect="true" pageSize="200">
                    <thead>
                        <tr>
                          <th field="name" width="120">名称</th>
                          <th field="lsyll" width="120">渗流量</th>
                          <th field="ysst" width="120">堰上水头</th>
                          <th field="plms" width="120">频率模数</th>
                          <th field="wd" width="120">温度</th>
                          <th field="mcubh" width="120">MCU编号</th>
                          <th field="mcutd" width="120">MCU通道</th>
                        </tr>
                    </thead>
                </table>
            </div>
        
        </div>
        
    </div>
          
</body>

<script type="text/javascript">
	//显示详细信息列表
	function showDetailList() {
		$('#syj').datagrid('load',{
			dbdm: ''
		});
		
		$('#lsy').datagrid('load',{
			dbdm: 'sl'
		});
		
		$('#w_detail').window('open');
	}

	//获取所有渗压实时数据
	$.ajax({
		type: 'post',
		url: 'GetDBRTData',
		dataType: 'json',
	    ContentType: 'application/json; charset=utf-8',
		cache: true,
		async: true,
		data: {'dbdm':''},
		success:function(data){
			for(var i = 0;i < data.length;i++){
				var datadiv=document.getElementById(data[i].name);
				if(datadiv != null) {
					datadiv.innerText = data[i].sysw +"m";
				}
			}
		}
  	});
	
	//显示渗压详细参数
	function showMsg(name) {
		load('正在获取数据，请稍候...');
		
		$.ajax({
			type: 'post',
			url: 'GetDBRTDataDetails',
			dataType: 'json',
		    ContentType: 'application/json; charset=utf-8',
			cache: true,
			async: true,
			data: {'names': name},
			success:function(data){	
				disload();  //关闭等待框
				
				if(data.length > 0){
				    var content = 
						'<style type="text/css">'+
							'table.gridtable {'+
								'font-family: verdana,arial,sans-serif;'+
								'font-size:15px;'+
								'color:#333333;'+
								'border-width: 1px;'+
								'border-color: #666666;'+
								'border-collapse: collapse;'+
								'width:460px;'+
							'}'+
							'table.gridtable td {'+
								'border-width: 1px;'+
								'padding: 5px;'+
								'border-style: solid;'+
								'border-color: #666666;'+
								'background-color: #ffffff;'+
							'}'+
						'</style>'+
						'<div style="margin:0;line-height:20px;padding:2px;font-size:18px;color:#3A5FCD;">'+
							'<table class="gridtable">'+
								'<tr>'+
									'<td>名称：'+ data[0].name +'</td>'+
									'<td>渗压水位：'+  data[0].sysw +' m</td>'+
								'</tr>'+
								'<tr>'+
									'<td>埋设高程：'+ data[0].msgc +' m</td>'+
									'<td>管口高程：'+ data[0].gkgc +' m</td>'+
								'</tr>'+
								'<tr>'+
									'<td>频率模数：' + data[0].plms +'</td>'+
									'<td>温度：'+ data[0].wd +'℃</td>'+
								'</tr>'+
								'<tr>'+
									'<td>MCU编号：'+ data[0].mcubh +'</td>'+
									'<td>MCU通道：'+ data[0].mcutd +'</td>'+
								'</tr>'+
							'</table>'+
						'</div>';
					document.getElementById('w').innerHTML = content;
				}
				
				$('#w').panel({title: name+" 渗压计"});
				$('#w').window('open');
			}, 
			error: function(e) { 
				disload();  //关闭等待框
			} 
	  	});
	}
	
	
	//获取所有渗流实时数据
	$.ajax({
		type: 'post',
		url: 'GetDBRTData',
		dataType: 'json',
	    ContentType: 'application/json; charset=utf-8',
		cache: true,
		async: true,
		data: {'dbdm':'sl'},
		success:function(data){
			for(var i = 0;i < data.length;i++){
				var datadiv=document.getElementById(data[i].name);
				if(datadiv != null) {
					datadiv.innerText = data[i].lsyll +"L/s";
				}
			}
		}
  	});
	
	//显示渗流详细参数
	function showSLLMsg(name) {		
		$.ajax({
			type: 'post',
			url: 'GetSLRTDataDetails',
			dataType: 'json',
		    ContentType: 'application/json; charset=utf-8',
			cache: true,
			async: true,
			data: {'names': name},
			success:function(data){	
				
				if(data.length > 0){
				    var content = 
						'<style type="text/css">'+
							'table.gridtable {'+
								'font-family: verdana,arial,sans-serif;'+
								'font-size:15px;'+
								'color:#333333;'+
								'border-width: 1px;'+
								'border-color: #666666;'+
								'border-collapse: collapse;'+
								'width:460px;'+
							'}'+
							'table.gridtable td {'+
								'border-width: 1px;'+
								'padding: 5px;'+
								'border-style: solid;'+
								'border-color: #666666;'+
								'background-color: #ffffff;'+
							'}'+
						'</style>'+
						'<div style="margin:0;line-height:20px;padding:2px;font-size:18px;color:#3A5FCD;">'+
							'<table class="gridtable">'+
								'<tr>'+
									'<td>名称：'+ data[0].name +'</td>'+
									'<td>渗流量：'+  data[0].lsyll +' L/s</td>'+
								'</tr>'+
								'<tr>'+
									'<td>堰上水头：'+ data[0].ysst +' m</td>'+
									'<td></td>'+
								'</tr>'+	
								'<tr>'+
									'<td>频率模数：' + data[0].plms +'</td>'+
									'<td>温度：'+ data[0].wd +'℃</td>'+
								'</tr>'+
								'<tr>'+
									'<td>MCU编号：'+ data[0].mcubh +'</td>'+
									'<td>MCU通道：'+ data[0].mcutd +'</td>'+
								'</tr>'+
							'</table>'+
						'</div>';					
					document.getElementById('w').innerHTML = content;
				}
				
				$('#w').panel({title: name+" 渗流量"});
				$('#w').window('open');
			}
	  	});
	}
	
	//获取库水位、库容量
	$.ajax({
		type: 'post',
		url: 'GetDBRTData',
		dataType: 'json',
	    ContentType: 'application/json; charset=utf-8',
		cache: true,
		async: true,
		data: {'dbdm':'ksw'},
		success:function(data){
			if(data.length > 0) {
				document.getElementById('sysw').innerText = '上游水位：'+data[0].sysw+' m';
				document.getElementById('xysw').innerText = '下游水位：'+data[0].xysw+' m';
				document.getElementById('krl').innerText = '库容量：'+data[0].krl+' 万m3';
			}
		}
  	});
	
</script>

</html>