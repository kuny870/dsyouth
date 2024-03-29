$(document).ready(function(){
	var team = document.getElementById('teamId');
	if(team.value == 4 || team.value == 8) {
		var newMember = document.getElementById('new-member');
		newMember.style.display = 'block';
	}
	
});


$('#name').blur(function() {
	
	var url = contextPath + "/rest/member/regist/nameCheck";
	
    $.ajax({
        type: "POST",
        url: url,
        data: {
        	name : $('#name').val()
        },
        success: function(res)
        {
        	
            if(res.success) { // show response from the php script.
            	
            	Swal.fire({
            	    title: '중복 지체 불러오기',
            	    html: "중복된 이름의 지체정보가 있습니다. 불러오시겠습니까?",
            	    showCancelButton: true,
            	    cancelButtonText: '취소',
            	    confirmButtonText: '확인',
            	    allowOutsideClick: true,
            	    reverseButtons: true
            	}).then(function (result) {
            		
            		if(result.value) {

//	            			$('#originImg').val(res.data.originProfileImg);
//	            			$('#replaceProfileImg').val(res.data.replaceProfileImg);
            			if(res.data.memState != null){$('#memState').val(res.data.memState);}
            			if(res.data.dateOfBirth != null){$('#dateOfBirth').val(res.data.dateOfBirth);}	
            			if(res.data.htel != null){$('#htel').val(res.data.htel);}	
            			if(res.data.samePeriodId != null){$('#samePeriodId').val(res.data.samePeriodId);}
            			if(res.data.memo != null){$('#memo').val(res.data.memo);}
            			if(res.data.gender != null) {
            				$("input:radio[name='gender']:radio[value=" + res.data.gender + "]").prop('checked', true);	            				
            			}
            			
            			$('#registFlag').val("1");
            			if(res.data.originProfileImg != null){$('#originProfileImg2').val(res.data.originProfileImg);}
            			if(res.data.replaceProfileImg != null){$('#replaceProfileImg2').val(res.data.replaceProfileImg);}
            			
            		}
            		
            	});
            	
            }else {

            }
        }, error:function(xhr){
            console.log(xhr.responseText);
            Swal.fire({
                text: "정보를 불러오는데 실패 했습니다",
                confirmButtonText: '확인',
                allowOutsideClick: true
            });
            return;
        }
     });
	    
});
	



function getTeam(val){
	var newMember = document.getElementById('new-member');
	newMember.style.display = 'none';
	
    var $target = $("select[name='teamId']");
    $target.empty();
    if(val == ""){
    	$target.append("<option value=''>팀선택</option>");
        return;
    } else if (val != "") {
    	
	    $.ajax({
	        type: "POST",
	        url: contextPath + "/rest/select/depart",
	        async: false,
	        data:{ departId : val },
	        dataType: "json",
	        success: function(result) {
	            if(result.data.length == 0){
	                $target.append("<option value=''>팀선택</option>");
	            }else{
	                $(result.data).each(function(i){
	                    $target.append("<option value=" + result.data[i].id + ">"+ result.data[i].tShortName +"</option>");
	                });
	                
	                // 새가족 전용 데이터 초기화
                	var guider = document.getElementById('guider');
                	guider.value = '';
                	var memberRegDate = document.getElementById('memberRegDate');
                	memberRegDate.value = '';
                	var memberGradDate = document.getElementById('memberGradDate');
                	memberGradDate.value = '';
	                
	            }
	        }, error:function(xhr){
	            console.log(xhr.responseText);
	            Swal.fire({
	                text: "팀 정보를 불러오는데 실패 했습니다",
	                confirmButtonText: '확인',
	                allowOutsideClick: true
	            });
	            return;
	        }
	    });
	    
    }
    
}


function getNewMemberDetail(val){
	var newMember = document.getElementById('new-member');
	var guider = document.getElementById('guider');
	var memberRegDate = document.getElementById('memberRegDate');
	var memberGradDate = document.getElementById('memberGradDate');
	
	 if(val == 4 || val == 8) {
		newMember.style.display = 'block';
     }else {
    	newMember.style.display = 'none';
    	
    	// 새가족 전용 데이터 초기화
     	guider.value = '';
     	memberRegDate.value = '';
     	memberGradDate.value = '';
     }
}




// 멤버 등록
$("#memberRegistForm").submit(function(e) {

	if(doubleSubmitCheck()) return;
	
	var dateOfBirthRegex=/^[0-9]{6}$/;
	var htelRegex=/^[0-9]{10,11}$/;
	
	var $name = $('#name');
	var $dateOfBirth = $('#dateOfBirth');
	var $htel = $('#htel');

	var validateMessage = null;
	var validateFocus = null;

	// input 데이터 체크 및 팝업text 입력, 포커스 입력
	if ($name.val() == "") {
		validateMessage = '이름을 입력해 주세요';
		validateFocus = $name;
	} else if ($dateOfBirth.val() != "" && dateOfBirthRegex.test($dateOfBirth.val())===false) {
		validateMessage = '생년월일을 다시 확인해 주세요';
		validateFocus = $dateOfBirth;
	} else if ($htel.val() != "" && htelRegex.test($htel.val())===false) {
		validateMessage = '휴대폰 번호를 다시 확인해 주세요';
		validateFocus = $htel;
	}

	// input 데이터 체크 및 팝업창 띄워주고 포커스
	if(validateMessage != null) {
		validateFocus.focus();
		Swal.fire({
            text: validateMessage,
            confirmButtonText: '확인',
            allowOutsideClick: true
        });
		doubleSubmitFlag = false;
		return false;
	}
	
	var form = $(this);
	var url = contextPath + "/rest/member/regist";
   
	$.ajax({
          type: "POST",
          url: url,
          data: form.serialize(), // serializes the form’s elements.
          success: function(result)
          {
              if(result.text != "" && $('#replaceProfileImg2').val() == "" ) {
            	  
            	  $('#memberId').val(result.text);
            	  
            	  var form = $(this);
        		  var form = $('#registProfileImgForm')[0];
        	      var formData = new FormData(form);
            	  
        	      
        	      var url = contextPath + "/rest/profileImg/regist";
      		    
      		    $.ajax({
      		        type: "post",
      		        url: url,
      		        enctype: "multipart/form-data",
      		        contentType: false,
      		        processData: false,
      		        data: formData, // serializes the form’s elements.
      		        success: function(result)
      		        {
      		            if(result == "SUCCESS") { // show response from the php script.
      		            	
      	            	  Swal.fire({
      	                      text: "팀원이 등록 되었습니다",
      	                      confirmButtonText: '확인',
      	                      allowOutsideClick: true
      	                  }).then(function() {
      	                	  location.reload();
      	                  });
      		            	
      		            }
      		        },
      		 		  fail: function(result) {
      		 			 Swal.fire({
      		 	            text: "프로필 사진 등록에 실패 했습니다",
      		 	            confirmButtonText: '확인',
      		 	            allowOutsideClick: true
      		 	        });
      		 		  }
      			});
      		    
              }else {
            	  Swal.fire({
            		    text: result.message,
            		    confirmButtonText: '확인',
            		    allowOutsideClick: true
            	  });

              }
          },
   		  fail: function(result) {
   			Swal.fire({
   			    text: "멤버 등록에 실패 했습니다",
   			    confirmButtonText: '확인',
   			    allowOutsideClick: true
   			});
   		  }
    });

	e.preventDefault(); // avoid to execute the actual submit of the form.
   
});


/*function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("address").value = addr;
            // 커서를 상세주소 필드로 이동한다.
            document.getElementById("detailAddress").focus();
        }
    }).open();
}*/