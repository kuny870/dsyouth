<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>EXCEL DOWNLAOD POPUP</title>
<style> 
/* 마스크 뛰우기 */
#mask {  
    position:absolute;  
    z-index:9000;  
    background-color:#000;  
    display:none;  
    left:0;
    top:0;
} 
/* 팝업으로 뜨는 윈도우 css  */ 
.window{
    position: fixed;
    width: 70%;
    left: 50%;
    margin-left: -35%; /* half of width */
    /* height: 300px; */
    top: 50%;
    margin-top: -150px; /* half of height */
    overflow: auto;

    /* decoration */
    border: 1px solid #000;
    background-color: #eee;
    padding: 1em;
    box-sizing: border-box;
    
    display: none;
    background-color:#FFF;
    z-index:10000;
 }
 
</style>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript"> 
//<![CDATA[
    function wrapWindowByMask(){
 
        //화면의 높이와 너비를 구한다.
        var maskHeight = $(document).height();  
        var maskWidth = $(window).width();  
 
        //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채운다.
        $("#mask").css({"width":maskWidth,"height":maskHeight});  
 
        //애니메이션 효과 - 일단 0초동안 까맣게 됐다가 60% 불투명도로 간다.
 
        $("#mask").fadeIn(0);      
        $("#mask").fadeTo("slow",0.6);    
 
        //윈도우 같은 거 띄운다.
        $(".window").show();
 
    }
 
    $(document).ready(function(){
 
        //닫기 버튼을 눌렀을 때
        $(".window .close").click(function (e) {  
            //링크 기본동작은 작동하지 않도록 한다.
            e.preventDefault();  
            $("#mask, .window").hide();  
        });       
 
        //검은 막을 눌렀을 때
        $("#mask").click(function () {  
            $(this).hide();  
            $(".window").hide();  
 
        });
        
    });
    
 
//]]>
</script>
</head>
<body>
    <div id="wrap"> 
        <div id="container">  
            <div id="mask"></div>
           	<div class="window">
                <div style="width:100%; height:100%; text-align:center; vertical-align:middle;">
                	<div style="font-size: 15px; font-weight: 700">[ 엑셀 다운로드 ]</div>
                	
                	<br/>
                	
						<div class="customer-select-search" style="width: 40%; margin-left: 7%; float: left;">
							<!-- 년 선택 -->
	                   		<select class="select-admin-pop-excel-year" id="yearExcel" name="yearExcel">
		                       	<c:forEach var="year" items="${yearList }">
		                       		<c:set var="selected" value="" />
									<c:if test="${year eq attendanceSearch.year }">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${year}" ${selected} >${year}년</option>
								</c:forEach>
		                    </select>
	                    </div>    
                    
	                    <div class="customer-select-search" style="width: 40%; margin-left: 7%; float: left;">
							<!-- 상반기 / 하반기 선택 -->
	                   		<select class="select-admin-pop-excel-season" id="seasonExcel" name="seasonExcel">
		                       	<c:forEach var="ss" items="${season }">
		                       		<c:set var="selected" value="" />
									<c:if test="${ss eq attendanceSearch.season }">
										<c:set var="selected" value="selected" />
									</c:if>
									<option value="${ss}" ${selected} >${ss}</option>
								</c:forEach>
		                    </select>
	                    </div>
	                    <br/>
	                    
                </div>
                
                <br/><br/>
				
				<div style="text-align: center;">
					<button class="excel-down-btn" onclick="excelDown()" style="font-size: 3.3vw;">다운로드</button>
				</div>
				
				<p style="text-align:center; background:#ffffff; padding:5px; margin-top:20px;">
					<button class="excel-close-btn close">닫기</button>
				</p>
            </div>
        </div>
    </div>
</body>

</html>

