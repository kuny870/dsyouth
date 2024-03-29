<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
  	<c:set var="memberList" value="${paging.list }" />
  	
    <div class="container">
    
            <div class="header-product">
             <div class="header-teamList">
   				<span class="shop-link-login" onclick="mypage()">
   					<img src="${resourcesPath}/assets/images/back_btn.png" class="back-img">
   				</span>
			    <div class="main-title-teamList-div text-center">
			    	<span class="main-title-teamList-span">팀별 관리</span>
			    	<img src="${resourcesPath }/assets/images/btn_excel.png" class="teamList-img" onclick="excelDownTeamList()">
			    </div>
			    </div>
			    
            
				<div class="div-container">
					<div class="customer-select-search member-list-select1">
						 <select class="select-member-list-team" id="teamId" name="teamId" onchange="fnGetCtgSub(this.value); memberSearch();">
						 	<option value="" >팀 전체</option>
	                       	<c:forEach var="team" items="${teamList }">
	                       		<c:set var="selected" value="" />
	                       		<c:if test="${team.id eq memberSearch.teamId }">
									<c:set var="selected" value="selected" />
								</c:if>
								<option value="${team.id}" ${selected }>${team.tShortName}</option>
							</c:forEach>
	                    </select>   
                    </div>
                    
                    <div class="customer-select-search member-list-select2">
						 <select class="select-member-list-group" id="groupId" name="groupId" onchange="memberSearch();">
						 	<option value="" >순 전체</option>
	                       	<c:forEach var="group" items="${groupList }">
	                       		<c:set var="selected" value="" />
	                       		<c:if test="${group.id eq memberSearch.groupId }">
									<c:set var="selected" value="selected" />
								</c:if>
								<option value="${group.id}" ${selected }>${group.gName}</option>
							</c:forEach>
	                    </select>
                    </div>        
                    
                    <div class="customer-select-search member-list-select3">
						 <select class="select-member-list-memState" id="memStateId" name="memStateId" onchange="memberSearch();">
						 	<option value="" >전체</option>
	                       	<c:forEach var="memState" items="${memStateList }">
	                       		<c:set var="selected" value="" />
	                       		<c:if test="${memState.id eq memberSearch.memStateId }">
									<c:set var="selected" value="selected" />
								</c:if>
								<option value="${memState.id}" ${selected }>${memState.mState}</option>
							</c:forEach>
	                    </select>
                    </div>
                    
                    <div class="customer-select-search member-list-input1">
                         <input type="text" class="md-input4" id="nameKW" name="nameKW" onkeyup="if(window.event.keyCode==13){(enterKeyEvent())}" placeholder="이름" value="${memberSearch.nameKW }" style="border: 1px solid #ccc;">
		            </div>
		            <div>
		            	<button class="basic-btn member-list-btn" onclick="memberSearch()">검색</button>
		            </div>
		            
					<div class="sales-table">
			            <div class="table-wrap">
			                <table>
			                    <thead>
			                        <tr>
			                            <th class="th-67p0">No</th>
			                            <th class="th-120p0">이름 (동기)</th>
			                            <th class="th-90p0">팀</th>
			                            <th>
				                            <c:choose>
							                	<c:when test="${memberSearch.teamId == 4 || memberSearch.teamId == 8}">
							                		인도자 (수료)
							                	</c:when>
							                	<c:otherwise>
							                		순
							                	</c:otherwise>
							                </c:choose>
						                </th>
			                            <th class="th-60p0">성별</th>
			                            <th class="th-80p1">연락처</th>
			                        </tr>
			                    </thead>
			                    <tbody>
			                    	<c:forEach var="mem" items="${memberList}" varStatus="i">
			                    		<tr>
				                            <td>${ ((memberSearch.pageNo - 1) * 10) + (i.index + 1) }</td>
				                            <td>
				                            	<div class="css-team-list-a">
				                            		<a href="javascript:memberModify('${mem.id}','${memberSearch.pageNo}','${memberSearch.teamId}','${memberSearch.groupId}', '${memberSearch.memStateId}', '${memberSearch.nameKW }')">
				                            			${mem.name}
				                            			<c:if test="${mem.samePeriodId != null }">
				                            				<c:set var="yearTmp" value="${year - mem.samePeriod.birthYear}"/>
				                            				<c:choose>
				                            					<c:when test="${yearTmp == 18}">
						                            				(0)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 19}">
						                            				(1)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 20}">
						                            				(2)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 21}">
						                            				(3)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 22}">
						                            				(4)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 23}">
						                            				(5)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 24}">
						                            				(6)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 25}">
						                            				(7)
						                            			</c:when>
						                            			<c:when test="${yearTmp == 26}">
						                            				(8)
						                            			</c:when>
						                            			<c:otherwise>
						                            				(${fn:substring(mem.samePeriod.birthYear,2,4)})
						                            			</c:otherwise>
						                            		</c:choose>
				                            			</c:if>
				                            		</a>
				                            	</div>
				                            </td>
				                            <td>
				                            	<c:choose>
												    <c:when test="${mem.team.tShortName  == '1새가족'}">
					                    				1새
						                            </c:when>
						                            <c:when test="${mem.team.tShortName  == '2새가족'}">
					                    				2새
						                            </c:when>
						                            <c:otherwise>
						                            	${mem.team.tShortName }
						                            </c:otherwise>
						                        </c:choose>
						                    </td>
				                            <td>
				                            	<c:choose>
								                	<c:when test="${memberSearch.teamId == 4 || memberSearch.teamId == 8}">
								                		${mem.guider}
								                			<c:if test="${mem.memberGradDate != null }">(${mem.memberGradDate} 수료)</c:if>
								                	</c:when>
								                	<c:otherwise>
								                		${mem.group.gName}
								                	</c:otherwise>
								                </c:choose>
				                            </td>
				                            <td>${mem.gender}</td>
				                            <td>
				                            	<c:if test="${mem.htel != ''}">
				                            		<a href="tel:${mem.htel}">
				                            			<img src="${resourcesPath}/assets/images/phone_img.png" class="phone-img">
				                            		</a>
				                            	</c:if>
				                            </td>
				                        </tr>
									</c:forEach>
                                    
			                    </tbody>
			                    
			                </table>
			                

							<!-- pagination -->
						  	<nav class="paging text-center">
							    <ul>
							      <c:if test="${paging.startPage - paging.pageSize gt 0}">
							         <li class="paginate_button previous" id="DataTables_Table_0_previous">
							             <a href="javascript:pageModule.movePage(${paging.startPage-1})" onclick="loadingbar()" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0"><</a>
							         </li>
							      </c:if>
							      <c:forEach varStatus="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
							         <c:set var="active" value="${i.index eq paging.pageNo ? 'active' : ''}"/>
							         <li>
							             <a class="${active}" href="javascript:pageModule.movePage(${i.index})" onclick="loadingbar()" aria-controls="DataTables_Table_0" tabindex="0">${i.index}</a>
							         </li>
							       </c:forEach>
							        <c:if test="${paging.totalPage - paging.endPage gt 0}">
							          <li class="paginate_button next" id="DataTables_Table_0_next">
							            <a href="javascript:pageModule.movePage(${paging.startPage+paging.pageSize})" onclick="loadingbar()" aria-controls="DataTables_Table_0" data-dt-idx="7" tabindex="0">></a>
							          </li>
							        </c:if>
							     </ul>
						  	</nav>
			                    
			            </div>
			        </div>
	
				</div>

			<div class="div-temp1"></div>
				    
	    	<!-- 리더 이상 -->
	    	<c:if test="${login.authId <= 6}">
		        <div class="layer_fixed3">
		        	<form>
				        <div class="form-bottom" style="text-align:center;">
				        	<input type="hidden" id="sTeamId" name="sTeamId" value="${memberSearch.teamId }" />
                    		<input type="hidden" id="sGroupId" name="sGroupId" value="${memberSearch.groupId }" />
                    		<input type="hidden" id="sNameKW" name="sNameKW" value="${memberSearch.nameKW }" />
                    		<input type="hidden" id="pageNo" name="pageNo" value="${memberSearch.pageNo }" />
			            	<button class="btn md-button2" type="button" onclick="memberRegist()">팀원 추가</button>
			          	</div>
		          	</form>
				</div>
			</c:if>
			
    	</div>
    </div>    

  </body>
  
  <script src="${resourcesPath}/assets/js/member/list.js?${nowTime}"></script>
  
</html>
