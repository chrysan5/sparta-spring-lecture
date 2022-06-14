
// 사용자가 내용을 올바르게 입력하였는지 확인
function isValidContents(contents) {
    if (contents == '') {
        alert('내용을 입력해주세요');
        return false;
    }
    if (contents.trim().length > 140) {
        alert('공백 포함 140자 이하로 입력해주세요');
        return false;
    }
    return true;
}

// 익명의 username을 만듭니다.
function genRandomName(length) {
    let result = '';
    let characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let charactersLength = characters.length;
    for (let i = 0; i < length; i++) {
        let number = Math.random() * charactersLength;
        let index = Math.floor(number);
        result += characters.charAt(index);
    }
    return result;
}

// 수정 버튼을 눌렀을 때, 기존 작성 내용을 textarea 에 전달합니다.
function editPost(id) {
    $(`#${id}-editarea`).show();
    $(`#${id}-submit`).show();
    $(`#${id}-delete`).show();

    $(`#${id}-contents`).hide();
    $(`#${id}-edit`).hide();

    let contents = $(`#${id}-contents`).text().trim();
    $(`#${id}-textarea`).val(contents);
}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////

$(document).ready(function () {
    getMessages();
})

// 메모를 불러와서 보여줍니다.
function getMessages() {
    $('#cards-box').empty(); // 기존 메모 내용을 지웁니다
    $.ajax({  //  메모 목록을 불러와서 HTML로 붙입니다.
        type: 'GET',
        url: '/api/memos',
        success: function (response) { //목록자체가 response에 담긴다
            for (let i = 0; i < response.length; i++) {
                let message = response[i];
                let id = message['id'];
                let username = message['username'];
                let contents = message['contents'];
                let modifiedAt = message['modifiedAt'];
                addHTML(id, username, contents, modifiedAt);
            }
        }
    })
}

// 메모 하나를 HTML로 만들어서 body 태그 내 원하는 곳에 붙입니다.
function addHTML(id, username, contents, modifiedAt) {
    let tempHtml = `<div class="card">     // HTML 태그를 만듭니다.
        <!-- date/username 영역 -->
        <div class="metadata">
            <div class="date">
                ${modifiedAt}
            </div>
            <div id="${id}-username" class="username">
                ${username}
            </div>
        </div>
        <!-- contents 조회/수정 영역-->
        <div class="contents">
            <div id="${id}-contents" class="text">
                ${contents}
            </div>
            <div id="${id}-editarea" class="edit">
                <textarea id="${id}-textarea" class="te-edit" name="" cols="30" rows="5"></textarea>
            </div>
        </div>
        <!-- 버튼 영역-->
        <div class="footer">
            <img id="${id}-edit" class="icon-start-edit" src="images/edit.png" alt="" onclick="editPost('${id}')">
            <img id="${id}-delete" class="icon-delete" src="images/delete.png" alt="" onclick="deleteOne('${id}')">
            <img id="${id}-submit" class="icon-end-edit" src="images/done.png" alt="" onclick="submitEdit('${id}')">
        </div>
    </div>`;
    $('#cards-box').append(tempHtml); // #cards-box 에 HTML을 붙인다.
}

// 메모 쓰기
function writePost() {
    let contents = $('#contents').val(); // 작성한 메모를 불러옵니다.

    if (isValidContents(contents) == false) { //작성한 메모가 올바른지 isValidContents 함수를 통해 확인
        return;
    }

    let username = genRandomName(10); //genRandomName 함수를 통해 익명의 username을 만듭니다.
    let data = {'username': username, 'contents': contents};  // 전달할 data JSON으로 만듭니다.

    //jquery방식으로 서버에 요청을 보내는 폼
    $.ajax({
        type: "POST", //type은 메소드. 즉, post방식으로 넘긴다
        url: "/api/memos", //  /api/memos에 요청을 한다.
        contentType: "application/json", //json형식으로 전달함을 알림
        data: JSON.stringify(data), //data를 json문자열로 변환함
        success: function (response) { //서버가 성공적으로 응답할경우 결과가 response에 넣어진다
            alert('메시지가 성공적으로 작성되었습니다.');
            window.location.reload();
        }
    });
}

// 메모를 수정합니다.
function submitEdit(id) {
    let username = $(`#${id}-username`).text().trim(); // 작성 대상 메모의 username과 contents 를 확인합니다.
    let contents = $(`#${id}-textarea`).val().trim();

    if (isValidContents(contents) == false) { // 작성한 메모가 올바른지 isValidContents 함수를 통해 확인합니다
        return;
    }

    let data = {'username': username, 'contents': contents}; // 전달할 data JSON으로 만듭니다.

    $.ajax({ // PUT /api/memos/{id} 에 data를 전달합니다.
        type: "PUT",
        url: `/api/memos/${id}`,
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            alert('메시지 변경에 성공하였습니다.');
            window.location.reload();
        }
    });
}

// 메모를 삭제합니다.
function deleteOne(id) {
    $.ajax({
        type: "DELETE",
        url: `/api/memos/${id}`, // DELETE /api/memos/{id} 에 요청해서 메모를 삭제합니다.
        success: function (response) {
            alert('메시지 삭제에 성공하였습니다.');
            window.location.reload();
        }
    })
}