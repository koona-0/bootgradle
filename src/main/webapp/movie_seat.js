/* 
//AI 코드 
document.querySelectorAll('.seat').forEach(seat => {
	seat.addEventListener('click', () => {
		if (!seat.classList.contains('occupied')) {
			seat.classList.toggle('selected');
		}
	});
});
*/

let btn = document.querySelectorAll(".seat");
//querySelector 일때만 addEventListener 사용 가능 => 버튼 하나만 적용
//querySelectorAll 일때는 forEach + addEventListener 사용

let seat_arr = new Array();     // 선택된 좌석 번호를 저장할 배열

btn.forEach((a) => {
	//e => Element 의 속성 리스트를 모두 가져옴
	a.addEventListener("click", (e) => {

		//인원수 제한 조건문 
		if (seat_arr.length >= person && e.target.className == "seat") {
			alert("좌석을 더 이상 선택할 수 없습니다.");
		} else {
			var no = e.target.attributes.data.value;
			//기존에 사용되고 있는 클래스 이름을 변경 또는 추가하여 적용하는 코드
			if (e.target.className == "seat") {   //좌석을 선택 
				e.target.className = "seat selected"; // 선택된 좌석에 selected 클래스 추가
				seat_arr.push(no); // 선택된 좌석 번호를 배열에 추가
				console.log(seat_arr);
				//filter : 해당 배열에서 해당 값을 제외한 배열 값만 출력하는 형태
			} else {  //좌석을 선택 해제
				e.target.className = "seat"; // 선택 해제된 좌석에 selected 클래스 제거
				//var redata = seat_arr.filter((a)=> a != no); // 선택 해제된 좌석 번호를 배열에서 제거
				var redata = seat_arr.filter(function(a) {
					return a != no; // 선택 해제된 좌석 번호를 배열에서 제거
				});
				seat_arr = redata; // 제거된 배열을 다시 seat_arr에 저장 즉, 해당 filter함수를 통해서 변경된 배열값을 기존에 있는 배열값으로 이관
				console.log(redata);
			}

		}

	});
});





function movie_ok() {
	if (seat_arr.length == 0) {
		alert("좌석을 선택해주세요");
	} else {
		frm.seat_data.value = seat_arr;
		frm.submit();
	}


}