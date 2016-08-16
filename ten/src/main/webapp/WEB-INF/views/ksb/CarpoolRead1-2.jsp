<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/read.css">  
</head>
<body>
<form action="../carpool/update_s" method="post">
<h1 style="color: #0099ff;width: 300px;margin: 0px auto">정기 카풀</h1>
	<input type="hidden" value="${carpoolDTO.carpoolno}" name="carpoolno">
	<input type="hidden" value="정기카풀" id="c_type" name="c_type">
	<input type="hidden" value="태워주세요" id="kind" name="kind">
	<input type="hidden" value="${carpoolDTO.c_startv}" id="c_startv" name="c_startv">
	<input type="hidden" value="${carpoolDTO.viav}" id="viav" name="viav">
	<input type="hidden" value="${carpoolDTO.c_endv}" id="c_endv" name="c_endv">
	<input type="hidden" value="${carpoolDTO.c_start}" name="c_start">
	<input type="hidden" value="${carpoolDTO.via}"  name="via">
	<input type="hidden" value="${carpoolDTO.c_end}"  name="c_end">
	<input type="hidden" value="${carpoolDTO.purpose}"  name="purpose">
	<input type="hidden" value="${carpoolDTO.startdate}"  name="startdate">
	<input type="hidden" value="${carpoolDTO.seat}"  name="seat">
	<input type="hidden" value="${carpoolDTO.price}"  name="price">
	<input type="hidden" value="${carpoolDTO.smoke}"  name="smoke">
	<input type="hidden" value="${carpoolDTO.c_comment}"  name="c_comment">
	
	<h2 align="left">태워주세요</h2>	
	<table style="width: 100%;">
		<tr>
			<td width="10%">출발지 :</td>
			<td width="30%" id="c_start">${carpoolDTO.c_start}</td>
			<td width="10%">목적지 :</td>
			<td width="30%" id="c_end">${carpoolDTO.c_end}</td>			
			<td valign="top" rowspan="5" width="20%" style="padding-left: 100px">
				<table border="1" style="width: 200px; border-collapse: collapse;">
					<tr>
						<td align="center"><img alt="사진" src="../images/photo.jpg">
							<br>홍길동 <br>인증내역 1개 <br>-이메일</td>
					</tr>
					<tr>
						<td><a>프로필</a></td>
					</tr>
					<tr>
						<td><a>등록글 1개</a></td>
					</tr>
					<tr>
						<td><a>평가</a></td>
					</tr>
					<tr>
						<td><button style="width: 150px; height: 150px">태우기</button></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<table border="1" style="width: 100%;border-collapse: collapse;">
					<tr>
						<td>경유지</td>
						<td colspan="3" id="via">${carpoolDTO.via}</td>
					</tr>
					<tr>
						<td>목적 : </td>
						<td>${carpoolDTO.purpose}</td>
						<td>출발일시 : </td>
						<td>${carpoolDTO.startdate}</td>
					</tr>
					<tr>
						<td>인원 수 :</td>
						<td>${carpoolDTO.seat}</td>
						<td>금액(1인당)</td>
						<td>${carpoolDTO.price}원</td>
					</tr>
					<tr>
						<td>차내흡연</td>
						<td colspan="3">${carpoolDTO.smoke}</td>
					</tr>
				</table>
		<tr>
			<td colspan="4">
				<input type="button" id="roadfind"
					value="실제 경로찾기" onclick="find()">
				<div id='map' style="width:800px;height:400px;"></div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border-top: 1px solid gray">
			<h3 align="left">추가내용</h3>
				<div style="border:1px solid gray">
					${carpoolDTO.c_comment}
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border-top: 1px solid gray">
			<h3 align="left">평가</h3>
			<div align="center" style="border:0px solid;background-color:gray;border-radius:5px">
			<br>상대방을 평가해 주세요. (예약을 하거나 메시지를 주고 받은 상대방을 평가 할 수 있습니다.)<br>
			<br>
			<img id="star1" alt="별" src="../images/별0.png">
			<img id="star2" alt="별" src="../images/별0.png">
			<img id="star3" alt="별" src="../images/별0.png">
			<img id="star4" alt="별" src="../images/별0.png">
			<img id="star5" alt="별" src="../images/별0.png">
			<br><br>
			</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="center">
			<input type="button" value="목록가기" onclick="location.href='./list'">
			<input type="submit" id='update' value="수정하기" style="margin-left: 100px;width:80px;height: 35px">
			<input type="button" id='delete' value="삭제하기" style="margin-left: 100px;width:80px;height: 35px"
			onclick="adelete(${carpoolDTO.carpoolno});">
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border-top: 1px solid gray">
			<h3 align="left">댓글</h3>
			<c:forEach var="carpool_replyDTO" items="${rlist}">
				  <div class="rlist">
				   ${carpool_replyDTO.id}<br/>
				   <p>${carpool_replyDTO.content}</p>
				   ${carpool_replyDTO.crep_date}
				   <c:if test="${sessionScope.id==carpool_replyDTO.id }">
				   <span style="float: right;">
				   <a href="javascript:rupdate('${carpool_replyDTO.crep_no}','${carpool_replyDTO.content }')">
				   수정</a>|<a href="javascript:rdelete('${carpool_replyDTO.crep_no}')">삭제</a>
				   </span>
				   </c:if>
				  </div>
				  </c:forEach>
				  <div class="rcreate">
				 <form name="rform" action="./rcreate" method="post" onsubmit="return input(this)">
				  <textarea rows="3" cols="28" name="content" onclick="rcheck(this)"></textarea>
				  <input type="button" name="rsubmit" id="rsubmit" value="등록">
				  <input type="hidden" name="carpoolno" id='carpoolno' value="${carpoolDTO.carpoolno}">
				  <input type="hidden" name="id" id="id" value="${sessionScope.id}">
				  <input type="hidden" name="nowPage" id='nowPage' value="${param.nowPage}">
				  <input type="hidden" name="nPage" id="nPage" value="${nPage}">
				  <input type="hidden" name="kind1" id="kind1" value="${param.kind1}">
				  <input type="hidden" name="kind2" id="kind2" value="${param.kind2}">
				  <input type="hidden" name="word1" id="word1" value="${param.word1}">
				  <input type="hidden" name="word2" id="word2" value="${param.word2}">
				  <input type="hidden" name="crep_no" id="crep_no" value="0">
				  
				  
				  </form>
				  </div>
				  <div class="bottom">
				  ${paging}
				  </div>
			</td>
		</tr>
	</table>
	</form>
	<!-- Jquery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/css/read.js"></script>
	<!-- 다음 지도 -->
	<script type="text/javascript"
		src="//apis.daum.net/maps/maps3.js?apikey=dfb4b6ea4ce68e78f0cb653cf043d987
		&libraries=services"></script>
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/css/read_map.js"></script>
</body>
</html>