var stompPostClient = null;

function showResult(message) {
    $("#posts").append("<br>" + message +"<br>");
    window.scrollTo(0,document.body.scrollHeight);
}

function connect2Posts() {
    var socket = new SockJS("/postStream');
    stompPostClient = Stomp.over(socket);
    stompPostClient.connect({}, function (frame) {
        stompPostClient.subscribe("/topic/posts", function (commentResult) {
            showResult(commentResult.body);
        });
    });
}

$(function () {
    connect2Posts();
});