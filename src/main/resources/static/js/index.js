$(document).ready(function() {
    connect();
});

var stompClient = null;
function connect() {
    var socket = new SockJS('/hello');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting){
            //console.log(JSON.parse(greeting.body).content);
        });
    },function(message) {
        console.log(message);
        setTimeout("connect()", 2000);
    });
}
function send() {
    stompClient.send("/app/hello", {}, JSON.stringify({ 'name': "world" }));
    stompClient.send("/app/hello",{},"hello world!");
}
function disconnect() {
    try {
        stompClient.disconnect();
        console.log("Disconnected");
    }catch(err) {
        console.log("disconnect ws failed")
    }
}
