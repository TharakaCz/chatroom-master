var sock = new SockJS("/endpointChat");
var stomp = Stomp.over(sock);
stomp.connect('guest', 'guest', function (frame) {
    stomp.subscribe("/user/notification", showGetMsg);
});

$('#chat-form').submit(function (e) {
    e.preventDefault();
    var text = $('input')[0].value;
    var receiver = $('input')[2].value;
    showSendMsg(text);
    stomp.send("/chat", {}, JSON.stringify({"text": text, "receiver": receiver}));
});

/**
 * @param message
 */
function showSendMsg(message) {
    var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $('#receive')[0].innerHTML += '<span>'.concat(now).concat("<br>").concat(message).concat('</span><br><hr>');
    $('input')[0].value = "";
}

/**
 * @param message
 */
function showGetMsg(message) {
    var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $('#receive')[0].innerHTML += '<span>'.concat(now).concat("<br>").concat(message.body).concat('</span><br><hr>');
}
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423   
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18   
Date.prototype.Format = function(fmt)
{ //author: meizz   
    var o = {
        "M+" : this.getMonth()+1,                  
        "d+" : this.getDate(),                       
        "h+" : this.getHours(),                      
        "m+" : this.getMinutes(),                
        "s+" : this.getSeconds(),                 
        "q+" : Math.floor((this.getMonth()+3)/3), 
        "S"  : this.getMilliseconds()             
    };
    if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
    return fmt;
}

