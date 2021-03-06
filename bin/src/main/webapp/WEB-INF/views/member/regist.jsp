<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
  <head>

    <jsp:include page="/WEB-INF/views/layouts/header.jsp" flush="false" />

	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<c:set var="resourcesPath" value="${contextPath}/resources" />
	<script>
		var contextPath = '${contextPath}';
		var resourcesPath = '${resourcesPath}';
	</script>
	<link href="${resourcesPath}/assets/css/reset.css?${nowTime}" rel="stylesheet">
	<link href="${resourcesPath}/assets/css/common.css?${nowTime}" rel="stylesheet">
	<link href="${resourcesPath}/assets/css/general.css?${nowTime}" rel="stylesheet">
  </head>

<body>

	<div class="container">
		<div class="header-product">
			<span class="shop-link-login" onclick="memberList()">
				<img src="${resourcesPath}/assets/images/back_btn.png" class="back-img">
			</span>
			<p class="head_title text-center">팀원 추가</p>
			
            <div class="form">
                <form id="memberRegistForm">
                    <div class="form-middle">
                    	<input type="hidden" id="regUser" name="regUser" value="${login.id }" />
                    	<input type="hidden" id="sTeamId" name="sTeamId" value="${memberSearch.teamId }" />
                    	<input type="hidden" id="sGroupId" name="sGroupId" value="${memberSearch.groupId }" />
                    	<input type="hidden" id="sNameKW" name="sNameKW" value="${memberSearch.nameKW }" />
                    	<input type="hidden" id="pageNo" name="pageNo" value="${memberSearch.pageNo }" />
                    	
                    	<!-- <label>
                    		<input type="hidden" id="longAbsent" name="longAbsent" value="" />
                        	<input type="checkbox" id="longAbsentChk" name="longAbsentChk">
	                      		장기결석 여부
	            		</label> -->
	            		
                    	<!-- 멤버 상태 값 : 일반 1 , 장기결석 2 , 해외 3 , 군지체 4 , 새가족수료 5 , 졸업 6 -->
                    	<div class="customer-select">
                            <select id="memState" name="memState">
                            	<c:forEach var="memberState" items="${memberStateList}">
									<option value="${memberState.id}">${memberState.mState}</option>
								</c:forEach>
                            </select>                        
	                    </div>
	                    
	                    <label></label>
	                    
	            		<!-- 이름 -->
                        <label>
                            <input type="text" class="md-input" id="name" name="name" placeholder="이름 (필수)" value="" autocomplete="off">
                        </label>   
                        <!-- 생년월일 -->                     
                        <label>
                            <input type="number" pattern="\d*" class="md-input" id="dateOfBirth" name="dateOfBirth" placeholder="생년월일 (ex:870421) (선택)" value="" maxlength="6" oninput="numberMaxLength6(this);" autocomplete="off">
                        </label>
                        <!-- 휴대폰 번호 -->
                        <label>
                            <input type="number" pattern="\d*" class="md-input" id="htel" name="htel" placeholder='휴대폰 "-" 빼고 입력 (선택)' value="" maxlength="11" oninput="numberMaxLength11(this);" autocomplete="off">                        
                        </label>
                        
                       
                        <c:set var="disabled" value=""/>
                        <c:if test="${login.authId > 2}">
                        	<c:set var="disabled" value="disabled"/>
                        	<input type="hidden" id="departId" name="departId" value="${login.departId }">
                        	<input type="hidden" id="teamId" name="teamId" value="${login.teamId }">
                        </c:if>
                        
                        <!-- 부서 -->
                        <div class="customer-select">
                            <select id="departId" name="departId" onchange="getTeam(this.value);" ${disabled}>
                            	<c:forEach var="depart" items="${departList }">
									<c:set var="selected" value="" />
									<c:if test="${depart.id eq login.departId}">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${depart.id}" ${selected} >${depart.dName}</option>
								</c:forEach>
                            </select>                        
	                    </div>
	                    <!-- 팀 -->
	                    <div class="customer-select">
                            <select id="teamId" name="teamId" onchange="getNewMemberDetail(this.value);" ${disabled}>
                            	<c:forEach var="team" items="${teamList }">
									<c:set var="selected" value="" />
									<c:if test="${team.id eq login.teamId}">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${team.id}" ${selected} >${team.tShortName}</option>
								</c:forEach>
                            </select>                        
	                   </div>
	                   
	                   <!-- 새가족일 경우 인도자 -->
	                   <div id="new-member" style="display: none;">
		                   <label>
	                            <input type="text" class="md-input" id="guider" name="guider" placeholder='인도자 (선택)' value="" autocomplete="off" style="margin-top: 6vw;">                        
	                       </label>
	                       <div style="width: 80%; margin-top: 15px;">
		                       <table style="width: 100%; table-layout: fixed;">
			                       	<tr>
				                       	<td>
				                            <span style="margin-left: 10px;">등록일</span>
				                            <input style="margin-right: 5px;" type="date" class="customer-calendar" id="memberRegDate" name="memberRegDate" value="">
				                        </td>
				                        <td>
				                            <span style="margin-left: 15px;">수료일</span>
				                            <input style="margin-left: 5px;" type="date" class="customer-calendar" id="memberGradDate" name="memberGradDate" value="">                        
				                       </td>
			                       </tr>
		                       </table>
	                       </div>
	                   </div>
	                   
                    </div>
                    
                    <!-- 성별 -->
                    <div class="form-bottom-radio">
                        <label class="radio-inline radio-gender"> 
                            <input type="radio" name="gender" value="남">
                            <span class="ico"></span>
                            <span class="text">남자</span>
                        </label>
                        <label class="radio-inline radio-gender"> 
                            <input type="radio" name="gender" value="여"> 
                            <span class="ico"></span>
                            <span class="text">여자</span>
                        </label>
                    </div>
                    <div class="form-bottom form-bottom-fixed">                
                        <button class="btn" type="submit">확인</button>
                    </div>    
                </form>
            </div>
            
            <div class="div-temp1"></div>
            
        </div>
    </div>
   
</body>

<script src="${resourcesPath}/assets/js/member/regist.js?${nowTime}"></script>
	
</html>
