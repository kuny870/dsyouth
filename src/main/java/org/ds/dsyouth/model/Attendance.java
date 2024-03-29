package org.ds.dsyouth.model;

public class Attendance {

	private Integer id;
	private String groupId;
	private String groupGrade;
	private String memState;
	private String memberId;
	private String attYn;
	private String year;
	private String month;
	private String firstWeek;
	private String secondWeek;
	private String thirdWeek;
	private String fourthWeek;
	private String fifthWeek;
	private String sayu1;
	private String sayu2;
	private String sayu3;
	private String sayu4;
	private String sayu5;
	
	private String onYn1;
	private String onYn2;
	private String onYn3;
	private String onYn4;
	private String onYn5;
	
	
	private Integer attCnt1;
	private Integer attCnt2;
	private Integer attCnt3;
	private Integer weekTotalCnt;
	private Integer week1Cnt;
	private Integer week2Cnt;
	
	private String lastFirstWeek;
	private String lastSecondWeek;
	private String lastThirdWeek;
	private String lastFourthWeek;
	private String lastFifthWeek;
	private String lastSayu1;
	private String lastSayu2;
	private String lastSayu3;
	private String lastSayu4;
	private String lastSayu5;
	
	private String lastOnYn1;
	private String lastOnYn2;
	private String lastOnYn3;
	private String lastOnYn4;
	private String lastOnYn5;
	
	private String seasonFlag;
	private Integer totalLastYear;
	private String removeFlag1;
	private String removeFlag2;
	private Integer attOrd;
	
	private Member member;
	private Depart depart;
	private Team team;
	private Group group;
	
	public String getRemoveFlag1() {
		return removeFlag1;
	}
	public void setRemoveFlag1(String removeFlag1) {
		this.removeFlag1 = removeFlag1;
	}
	public String getRemoveFlag2() {
		return removeFlag2;
	}
	public void setRemoveFlag2(String removeFlag2) {
		this.removeFlag2 = removeFlag2;
	}
	public Integer getTotalLastYear() {
		return totalLastYear;
	}
	public void setTotalLastYear(Integer totalLastYear) {
		this.totalLastYear = totalLastYear;
	}
	public String getSeasonFlag() {
		return seasonFlag;
	}
	public void setSeasonFlag(String seasonFlag) {
		this.seasonFlag = seasonFlag;
	}
	public String getGroupGrade() {
		return groupGrade;
	}
	public void setGroupGrade(String groupGrade) {
		this.groupGrade = groupGrade;
	}
	public String getMemState() {
		return memState;
	}
	public void setMemState(String memState) {
		this.memState = memState;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Depart getDepart() {
		return depart;
	}
	public void setDepart(Depart depart) {
		this.depart = depart;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	private SamePeriod samePeriod;
	
	public SamePeriod getSamePeriod() {
		return samePeriod;
	}
	public void setSamePeriod(SamePeriod samePeriod) {
		this.samePeriod = samePeriod;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAttYn() {
		return attYn;
	}
	public void setAttYn(String attYn) {
		this.attYn = attYn;
	}
	public String getFirstWeek() {
		return firstWeek;
	}
	public void setFirstWeek(String firstWeek) {
		this.firstWeek = firstWeek;
	}
	public String getSecondWeek() {
		return secondWeek;
	}
	public void setSecondWeek(String secondWeek) {
		this.secondWeek = secondWeek;
	}
	public String getThirdWeek() {
		return thirdWeek;
	}
	public void setThirdWeek(String thirdWeek) {
		this.thirdWeek = thirdWeek;
	}
	public String getFourthWeek() {
		return fourthWeek;
	}
	public void setFourthWeek(String fourthWeek) {
		this.fourthWeek = fourthWeek;
	}
	public String getFifthWeek() {
		return fifthWeek;
	}
	public void setFifthWeek(String fifthWeek) {
		this.fifthWeek = fifthWeek;
	}
	public String getSayu1() {
		return sayu1;
	}
	public void setSayu1(String sayu1) {
		this.sayu1 = sayu1;
	}
	public String getSayu2() {
		return sayu2;
	}
	public void setSayu2(String sayu2) {
		this.sayu2 = sayu2;
	}
	public String getSayu3() {
		return sayu3;
	}
	public void setSayu3(String sayu3) {
		this.sayu3 = sayu3;
	}
	public String getSayu4() {
		return sayu4;
	}
	public void setSayu4(String sayu4) {
		this.sayu4 = sayu4;
	}
	public String getSayu5() {
		return sayu5;
	}
	public void setSayu5(String sayu5) {
		this.sayu5 = sayu5;
	}
	public Integer getAttOrd() {
		return attOrd;
	}
	public void setAttOrd(Integer attOrd) {
		this.attOrd = attOrd;
	}
	
	public String getLastFirstWeek() {
		return lastFirstWeek;
	}
	public void setLastFirstWeek(String lastFirstWeek) {
		this.lastFirstWeek = lastFirstWeek;
	}
	public String getLastSecondWeek() {
		return lastSecondWeek;
	}
	public void setLastSecondWeek(String lastSecondWeek) {
		this.lastSecondWeek = lastSecondWeek;
	}
	public String getLastThirdWeek() {
		return lastThirdWeek;
	}
	public void setLastThirdWeek(String lastThirdWeek) {
		this.lastThirdWeek = lastThirdWeek;
	}
	public String getLastFourthWeek() {
		return lastFourthWeek;
	}
	public void setLastFourthWeek(String lastFourthWeek) {
		this.lastFourthWeek = lastFourthWeek;
	}
	public String getLastFifthWeek() {
		return lastFifthWeek;
	}
	public void setLastFifthWeek(String lastFifthWeek) {
		this.lastFifthWeek = lastFifthWeek;
	}
	public String getLastSayu1() {
		return lastSayu1;
	}
	public void setLastSayu1(String lastSayu1) {
		this.lastSayu1 = lastSayu1;
	}
	public String getLastSayu2() {
		return lastSayu2;
	}
	public void setLastSayu2(String lastSayu2) {
		this.lastSayu2 = lastSayu2;
	}
	public String getLastSayu3() {
		return lastSayu3;
	}
	public void setLastSayu3(String lastSayu3) {
		this.lastSayu3 = lastSayu3;
	}
	public String getLastSayu4() {
		return lastSayu4;
	}
	public void setLastSayu4(String lastSayu4) {
		this.lastSayu4 = lastSayu4;
	}
	public String getLastSayu5() {
		return lastSayu5;
	}
	public void setLastSayu5(String lastSayu5) {
		this.lastSayu5 = lastSayu5;
	}
	public Integer getAttCnt1() {
		return attCnt1;
	}
	public void setAttCnt1(Integer attCnt1) {
		this.attCnt1 = attCnt1;
	}
	
	public Integer getAttCnt2() {
		return attCnt2;
	}
	public void setAttCnt2(Integer attCnt2) {
		this.attCnt2 = attCnt2;
	}
	public Integer getAttCnt3() {
		return attCnt3;
	}
	public void setAttCnt3(Integer attCnt3) {
		this.attCnt3 = attCnt3;
	}
	public Integer getWeekTotalCnt() {
		return weekTotalCnt;
	}
	public void setWeekTotalCnt(Integer weekTotalCnt) {
		this.weekTotalCnt = weekTotalCnt;
	}
	public Integer getWeek1Cnt() {
		return week1Cnt;
	}
	public void setWeek1Cnt(Integer week1Cnt) {
		this.week1Cnt = week1Cnt;
	}
	public Integer getWeek2Cnt() {
		return week2Cnt;
	}
	public void setWeek2Cnt(Integer week2Cnt) {
		this.week2Cnt = week2Cnt;
	}
	public String getOnYn1() {
		return onYn1;
	}
	public void setOnYn1(String onYn1) {
		this.onYn1 = onYn1;
	}
	public String getOnYn2() {
		return onYn2;
	}
	public void setOnYn2(String onYn2) {
		this.onYn2 = onYn2;
	}
	public String getOnYn3() {
		return onYn3;
	}
	public void setOnYn3(String onYn3) {
		this.onYn3 = onYn3;
	}
	public String getOnYn4() {
		return onYn4;
	}
	public void setOnYn4(String onYn4) {
		this.onYn4 = onYn4;
	}
	public String getOnYn5() {
		return onYn5;
	}
	public void setOnYn5(String onYn5) {
		this.onYn5 = onYn5;
	}
	public String getLastOnYn1() {
		return lastOnYn1;
	}
	public void setLastOnYn1(String lastOnYn1) {
		this.lastOnYn1 = lastOnYn1;
	}
	public String getLastOnYn2() {
		return lastOnYn2;
	}
	public void setLastOnYn2(String lastOnYn2) {
		this.lastOnYn2 = lastOnYn2;
	}
	public String getLastOnYn3() {
		return lastOnYn3;
	}
	public void setLastOnYn3(String lastOnYn3) {
		this.lastOnYn3 = lastOnYn3;
	}
	public String getLastOnYn4() {
		return lastOnYn4;
	}
	public void setLastOnYn4(String lastOnYn4) {
		this.lastOnYn4 = lastOnYn4;
	}
	public String getLastOnYn5() {
		return lastOnYn5;
	}
	public void setLastOnYn5(String lastOnYn5) {
		this.lastOnYn5 = lastOnYn5;
	}
	
}
