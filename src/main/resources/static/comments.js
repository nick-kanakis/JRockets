var stompCommentClient = null;

function connect2Comments() {
    var socket = new SockJS('/commentStream');
    stompCommentClient = Stomp.over(socket);
    stompCommentClient.connect({}, function (frame) {
        stompCommentClient.subscribe('/topic/comments', function (commentResult) {
            showResult(commentResult.body);
        });
    });
}

function showResult(message) {
    $("#comments").append("<br>" + message +"<br>");
    window.scrollTo(0,document.body.scrollHeight);
}

$(function () {
    connect2Comments();
});