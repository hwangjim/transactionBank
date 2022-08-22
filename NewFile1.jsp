<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- view가 아니므로 즉 보여지는 부분이 아니므로 HTML 태그 전부 지움  -->
<jsp:useBean id="b1" class="vo.Bank1VO" />
<jsp:useBean id="b2" class="vo.Bank2VO" />
<jsp:useBean id="dao1" class="dao.Bank1DAO" />
<jsp:useBean id="dao2" class="dao.Bank2DAO" />
<%
		// request.getParameter ("balance") == 문자열로 인식되므로 숫자로 형변환 
		if(dao1.transfer(Integer.parseInt(request.getParameter("balance")))){
			// 성공시 이때 성공은 이체가 성공했을때 문자열이 정수로 형변환이 된 것을 의미
			out.print("<script>alert('성공!');location.href = 'NewFile.jsp';</script>");
		} else {
			out.print("<script>alert('실패...');location.href = 'NewFile.jsp';</script>");
		}
	
	// 이들을 request, session, application 중 1개에 넣어야한다.
	// V에서 EL식으로 출력하기 위해, JSP scope 내장객체에 setAttribute()수행함
	b1=dao1.selectOne(b1);
	b2=dao2.selectOne(b2);
	session.setAttribute("b1", b1);
	session.setAttribute("b2", b2);
%>