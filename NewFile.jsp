<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- View 부분   -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

신한: ${b1.bname} | ${b1.balance}원 <br>
국민: ${b2.bname} | ${b2.balance}원

<hr>

<form method="post" action = "NewFile1.jsp">
	이체할 금액: <input type="number" value="0" min="0" name="balance">원
	<input type="submit" value="계좌이체">
</form>

</body>
</html>