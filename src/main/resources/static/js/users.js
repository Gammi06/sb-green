let isUsernameSameCheck = false;

$("#btnJoin").click(() => {
	join();
});

$("#btnUsernameSameCheck").click(() => {
	checkUsername();
});

$("#btnLogin").click(() => {
	login();
});

$("#btnDelete").click(() => {
	resign();
});

$("#btnUpdate").click(() => {
	update();
});

function emailCheck(str) {
	let reg_email = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	if (reg_email.test(str)) {
		return true;
	} else {
		return false;
	}
}

function upperCount(str) {
	let answer = 0;
	for (let x of str) {
		if (x === x.toUpperCase()) answer++;
	}
	return answer;
}

function upperCheck(str) {
	if (upperCount(str) < 1) {
		return true;
	} else {
		return false;
	}
}

function samePassword(password, sPassword) {
	if (password != sPassword) {
		return true;
	} else {
		return false;
	}
}

function koreanCheck(str) {
	let korRule = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
	if (korRule.test(str)) {
		console.log(str + ": " + korRule.test(str));
		return true;
	} else {
		console.log(str + ": " + korRule.test(str));
		return false;
	}
}

function spaceCheck(str) {
	let reg = /\s/g;
	if (str.match(reg)) {
		return true;
	} else {
		return false;
	}
}

function join() {
	if (isUsernameSameCheck == false) {
		alert("유저네임 중복 체크를 진행해주세요");
		return;
	}

	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};
	let sPassword = $("#passwordSame").val();

	if (emailCheck(data.email) == false) {
		alert("올바른 이메일 형식이 아닙니다.");
		return;
	}

	if (upperCheck(data.username) == true) {
		alert("유저네임은 대문자를 포함해야합니다.");
		return;
	}

	if (samePassword(data.password, sPassword)) {
		alert("비밀번호가 다릅니다.");
		return;
	}

	if (koreanCheck(data.username)) {
		alert("한글을 사용할 username 없습니다.");
		return;
	}

	if (koreanCheck(data.password)) {
		alert("한글을 사용할 password 없습니다.");
		return;
	}

	if (koreanCheck(data.email)) {
		alert("한글을 사용할 email 없습니다.");
		return;
	}

	if (spaceCheck(data.username)) {
		alert("공백을 사용할 username 없습니다.");
		return;
	}

	if (spaceCheck(data.password)) {
		alert("공백을 사용할 password 없습니다.");
		return;
	}

	if (spaceCheck(data.email)) {
		alert("공백을 사용할 email 없습니다.");
		return;
	}

	$.ajax("/api/join", {
		type: "POST",
		dataType: "json", // 응답 데이터
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/loginForm";
		} else {
			alert(res.meg);
			history.back();
		}
	});
}

function checkUsername() {
	let username = $("#username").val();

	$.ajax(`/api/users/usernameSameCheck?username=${username}`, {
		type: "GET",
		dataType: "json",
		async: true
	}).done((res) => {
		if (res.code == 1) { // 통신 성공
			if (res.data == false) {
				alert("아이디가 중복되지 않았습니다.");
				isUsernameSameCheck = true;
			} else {
				alert("아이디가 중복되었어요. 다른 아이디를 사용해주세요.");
				isUsernameSameCheck = false;
				$("#username").val("");
			}
		}
	});
}

function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember: $("#remember").prop("checked")
	};
	$.ajax("/api/login", {
		type: "POST",
		dataType: "json", // 응답 데이터
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			location.href = "/";
		} else {
			alert("로그인 실패, 아이디 패스워드를 확인해주세요");
		}
	});
}

function resign() {
	let id = $("#id").val();

	$.ajax("/s/api/users/" + id, {
		type: "DELETE",
		dataType: "json" // 응답 데이터
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴 완료");
			location.href = "/";
		} else {
			alert("회원탈퇴 실패");
		}
	});
}

function update() {
	let data = {
		password: $("#password").val(),
		email: $("#email").val()
	};

	let id = $("#id").val();
	$.ajax("/s/api/users/" + id, {
		type: "PUT",
		dataType: "json", // 응답 데이터
		data: JSON.stringify(data), // http body에 들고갈 요청 데이터
		headers: { // http header에 들고갈 요청 데이터
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("회원 수정 완료");
			location.reload(); // f5
		} else {
			alert("업데이트에 실패하였습니다");
		}
	});
}

