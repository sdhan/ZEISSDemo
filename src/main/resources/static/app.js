var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {
    stompClient = Stomp.client('ws://localhost:8080/getData');
    stompClient.debug = null;
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/syncing', function(syncing){
            showMessage(JSON.parse(syncing.body).content);
        });
         stompClient.subscribe('/topic/errors', function(syncing){
           showMessage(JSON.parse(syncing.body).content);
           stompClient.unsubscribe({});
       });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
     var name =   JSON.stringify({
                 "topic": "events",
                 "ref": null,
                 "payload": {
                   "machine_id": "59d9f4b4-018f-43d8-92d0-c51de7d987e5",
                   "id": "41bb0908-15ba-4039-8c4f-8b7b99260eb2",
                   "timestamp": "2017-04-16T19:42:26.542614Z",
                   "status": "running"
                 },
                 "event": "new"
               });
       stompClient.send("/app/getData", {}, JSON.stringify({ 'name':  name }));

}

function sendError() {
     var name =   JSON.stringify({
                 "topic": "events",
                 "ref": null,
                 "payload": {
                   "machine_id": "59d9f4b4-018f-43d8-92d0-c51de7d987e5",
                   "id": "41bb0908-15ba-4039-8c4f-8b7b99260eb2",
                   "timestamp": "2017-04-16T19:42:26.542614Z",
                   "status": "errored"
                 },
                 "event": "new"
               });
       stompClient.send("/app/getData", {}, JSON.stringify({ 'name':  name }));
}

function sendFinish() {
     var name =   JSON.stringify({
                 "topic": "events",
                 "ref": null,
                 "payload": {
                   "machine_id": "59d9f4b4-018f-43d8-92d0-c51de7d987e5",
                   "id": "41bb0908-15ba-4039-8c4f-8b7b99260eb2",
                   "timestamp": "2017-04-16T19:42:26.542614Z",
                   "status": "finished"
                 },
                 "event": "new"
               });
               disconnect()
      // stompClient.send("/topic/finished", {}, JSON.stringify({ 'name':  name }));
}

function showMessage(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}

