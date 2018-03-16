<%@page import="org.openshift.ExecScript"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cron Job Execution Template</title>
</head>
<body>
<%
String aScriptName = request.getParameter("script");
if (aScriptName == null)
{
	out.println(new ExecScript().runScript("/scripts/myscript.sh")));
}
else
	out.println(new ExecScript().runScript(aScriptName));
%>

</body>
</html>