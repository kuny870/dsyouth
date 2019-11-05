drop table auth;
drop table depart;
drop table team;
drop table `group`;
drop table same_period;
drop table user;
drop table member;
drop table attendance;
drop table leader_info;
drop table qt;

drop table board_opinion;
drop table board_free;
drop table address;

-- 등급
create table auth (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
    `no` int(10) not null,	-- 권한 번호
    `name` varchar(50) not null,	-- 권한명
    `description` varchar(50) null,	-- 설명
	-- 1:일반(순원) 3:임원(국장) 5:준관리자(순장(리더)) 6:중간 관리자(엘더) 
    -- 7:관리자(팀장) 8:최고관리자(목사) 9:마스터 관리자(admin 계정)
	constraint pk_auth primary key (id)
);

-- 사용자 (일반, 마스터)
create table user (
    id int(50) not null AUTO_INCREMENT,	-- 고유번호
    login_id varchar(50) not null,		-- 아이디
    login_pw varchar(100) not null,		-- 비밀번호
    name varchar(50) not null,	-- 이름
    depart_id int(10) not null,	-- 1,2청년부
    team_id int(10) not null,	-- 팀
    date_of_birth varchar(50) null,		-- 생년월일
    htel varchar(50) null,	-- 핸드폰번호
    gender varchar(10) null,	-- 성별 / male or female
    session_id varchar(100) null,	-- 세션 아이디
    use_cookie varchar(10) not null default 'N', -- 로그인 유지 여부
    auth_id int(10) not null default 7,	-- 회원 등급 default : 일반 순원 7
    auth_exec int(10) not null default 0, -- 임원 여부 / 0:일반 or 1:국장
	reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 가입 시간
    mod_date datetime null,		-- 수정 시간
	del_yn varchar(10) not null default 'N',	-- 탈퇴 여부
    
    constraint pk_user primary key (id)
);


-- 청년부 멤버
create table member (
    id int(50) not null AUTO_INCREMENT,	-- 고유번호
    name varchar(50) not null,	-- 이름
    depart_id int(10) null,	-- 1,2청년부
    team_id int(10) null,	-- 팀
    group_id int(10) null,  -- 순공부명
    group_grade varchar(50) not null default '순원',	-- 순등급 / 순장 or 순원
    date_of_birth varchar(50) null,		-- 생년월일
    htel varchar(50) null,	-- 핸드폰번호
    gender varchar(10) null,	-- 성별 / male or female
    same_period_id int(10) null,	-- 동기
    profile_img varchar(255) null,	-- 프로필 이미지
    reg_user int(10) not null,	-- 등록한 사용자
	reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 가입 시간
    mod_date datetime null,		-- 수정 시간
    long_absent varchar(10) not null default 'N',	-- 장기결석 여부
	del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
    constraint pk_member primary key (id)
);

-- 부서
create table depart (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
	d_name varchar(50) not null,	-- 부서 이름
    reg_user int(10) not null,	-- 등록자
	del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
    constraint pk_depart primary key (id)
);

-- 팀
create table team (
    id int(50) not null AUTO_INCREMENT,	-- 고유번호
    depart_id int(10) null,	-- 팀 소속 청년부
    t_short_name varchar(50) not null,	-- 팀 짧은 이름
    t_full_name varchar(50) null,	-- 팀 전체 이름
    t_theme varchar(50) null,	-- 팀 주제 말씀
    reg_user int(10) not null,	-- 작성자
	del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
    constraint pk_team primary key (id)
);

-- 순명
create table `group` (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
    depart_id int(10) not null,	-- 부서
	team_id int(10) not null,	-- 팀
    g_name varchar(50) not null,	-- 순명 
    reg_user int(10) not null,	-- 등록자
	del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
    constraint pk_group primary key (id)
);

-- 팀별 출석부
create table attendance (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
    member_id int(50) not null,	-- member 고유 id
    att_yn varchar(10) not null default 'Y',	-- 출석 카운트
    `year` int(20) not null,	-- 년
    `month` int(20) not null,	-- 월
    first_week varchar(10) not null default 'N',	-- 1주
    second_week varchar(10) not null default 'N',	-- 2주
    third_week varchar(10) not null default 'N',	-- 3주
    fourth_week varchar(10) not null default 'N',	-- 4주
    fifth_week varchar(10) not null default 'N',	-- 5주
    
    constraint pk_attendance primary key (id)
);


-- 동기
create table same_period (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
    birth_year varchar(20) not null,	-- 탄생년
    reg_user int(10) not null,	-- 등록자
    del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
	constraint pk_same_period primary key (id)
);



-- 리더배포자료 게시판
create table leader_info (
	id int(50) not null AUTO_INCREMENT, -- 고유번호
    title varchar(100) null,	-- 제목
    origin_img varchar(100) not null,	-- 원본 이미지 이름
    replace_img varchar(100) not null,	-- 대체 이미지 이름
    reg_user int(10) not null,	-- 등록자
    reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 등록일
    del_yn varchar(10) not null default 'N',	-- 삭제 여부
    
	constraint pk_leader_info primary key(id)
);


-- QT
create table qt (
	id int(50) not null AUTO_INCREMENT, -- 고유번호
    `year` varchar(20) not null,	-- 년
    `month` varchar(20) not null,	-- 월
    `day` varchar(20) not null,		-- 일
    title varchar(50) not null,		-- 제목
    bible varchar(50) not null,		-- 성경 본문
    content text(2000) not null,	-- 본문 내용

	constraint pk_qt primary key(id)
);


-- 의견 게시판
create table board_opinion (
	id int(50) not null AUTO_INCREMENT, -- 고유번호
    content varchar(255) not null,	-- 내용
    reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 등록일

	constraint pk_board_opinion primary key(id)
);


-- 자유 게시판
create table board_free (
	id int(50) not null AUTO_INCREMENT, -- 고유번호
    content varchar(255) not null,	-- 내용 
    reg_user int(10) not null,	-- 등록자
    reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 등록일
    mod_date datetime null,		-- 수정 시간
    del_yn varchar(10) not null default 'N',	-- 삭제 여부

	constraint pk_board_free primary key(id)
);


-- 주소
create table address (
	id int(50) not null AUTO_INCREMENT,	-- 고유번호
	user_id varchar(50) not null,	-- 회원 ID
	name varchar(50),	-- 주소 이름
    addr1 varchar(255),	-- 주소
    addr2 varchar(255),	-- 상세주소
	zipcode varchar(50),	-- 우편번호
    memo varchar(255),	-- 메모
    reg_date datetime DEFAULT CURRENT_TIMESTAMP,	-- 등록 시간
    del_yn varchar(10) not null default 'N', -- 삭제 여부
    
    constraint pk_address primary key (id)
);

