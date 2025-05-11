//예약날짜를 기본으로 오늘날짜로 설정하는 스크립트
frm.mdate.value = new Date().toISOString().substring(0, 10);

//예매버튼 클릭 
var reserve = () => {
	if (frm.mname.value == "") {
		alert("고객명을 입력하세요");
		return false;
	} else if (frm.mdate.value == "") {
		alert("날짜를 입력하세요");
		return false;
	} else if (frm.mtime.value == "") {
		alert("시간을 입력하세요");
		return false;
	} else {
		frm.submit();
	}
}

//고객의 고유 번호 난수 생성 

let usercode = "";
var w = 0;
while(w < 4){
	usercode += Math.floor(Math.random()*9);
	w++;
}
//console.log(usercode);
frm.usercode.value=usercode;