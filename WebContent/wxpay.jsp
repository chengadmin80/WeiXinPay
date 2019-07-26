<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统一下单</title>
<link type="text/css" rel="stylesheet" href="css/wxpay.css">
</head>
<body>
<div id="uo" >
    <form id="ff" method="post">
       <div class="formDiv"><label>应用ID:</label><input type="text" id="name" class="easyui-validatebox" name="name" data-options="required:true"><span class="formSpan">*</span></div>
        <div class="formDiv"><label>商户号:</label><input type="password" id="pass" class="easyui-validatebox" name="pass" data-options="required:true"><span class="formSpan">*</span></div>
        <div class="formDiv"><label>设备号:</label><input type="text" id="time" class="easyui-datebox" name="time" style="width: 70%;height: 26px"></div>
        <div class="formDiv"><label>随机字符串:</label><input type="text" id="part01" /></div>
        <div class="formDiv"><label>所属角色:</label><select id="role"><option>xxxx</option></select></div>
        <div class="forSubmint"> <a href="#" class="easyui-linkbutton"  iconCls="icon-ok" onclick="unifiedorder()" >提交，进行统一下单</a><a href="#" class="easyui-linkbutton"  iconCls="icon-redo" >重置</a> </div>

    </form>
    
</div>
<script type="text/javascript">
function unifiedorder(){
	alert('统一下单');
}
</script>
</body>
</html>