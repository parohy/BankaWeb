<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>

<spring:url value="/resources/css/homePage.css" var="homeStyle"/>
<link rel="stylesheet" type="text/css" href="${homeStyle}"/>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<spring:url value="/resources/js/navigation.js" var="navScript"/>
<script src="${navScript}"></script>

</head>

<body>
	<div id="header">
		<h2>Welcome ${client.name} !</h2>	
	</div>
	
	<div id="center">
		<ul class="tab">
			<li><a href="#" class="tablink" data="accounts">My accounts</a></li>
			<li><a href="#" class="tablink" data="cards">My cards</a></li>
			<li><a href="#" class="tablink" data="loans">My loans</a></li>
		</ul>
		
		<div class="content-container">		
		<div id="accounts" class="tab-content" style="display: block;">
			<c:forEach items="${client.accounts}" var="account">
				<table>
					<tr>
						<td>Account number:</td>
						<td>${account.number}</td>
					</tr>
					<tr>
						<td>Balance:</td>
						<td>${account.balance}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
		
		<div id="cards" class="tab-content">
			<c:forEach items="${client.cards}" var="card">
				<table>
					<tr>
						<td>Account number:</td>
						<td>${card.accountID}</td>
					</tr>
					<tr>
						<td>Active:</td>
						<td>${card.block}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
		
		<div id="loans" class="tab-content">
		<c:forEach items="${client.loans}" var="loan">
				<table>
					<tr>
						<td>Amount:</td>
						<td>${loan.amount}</td>
					</tr>
					<tr>
						<td>Interest:</td>
						<td>${loan.interest}</td>
					</tr>
					<tr>
						<td>Repaid amount:</td>
						<td>${loan.paid}</td>
					</tr>
				</table>
			</c:forEach>
		</div>
	</div>
	</div>
	
	<div>
		<ul id="footer">
			<li><a>About</a></li>
			<li><a>FAQ</a></li>
			<li><a>Help/Support</a></li>
			<li><a href="indexLogin">Log out</a></li>	
		</ul>
	</div>
</body>

</html>