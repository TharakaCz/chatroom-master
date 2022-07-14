var sock = new SockJS("./endpointChat");
var stomp = Stomp.over(sock);
stomp.connect('guest', 'guest', function (frame) {
    stomp.subscribe("/user/notification", showGetMsg);
});


function sendMessage() {
    var text = $('.write')[0].children[1].value;
    var receiver = $('.receiver-input')[0].value;
    showSendMsg(text);
    stomp.send("/chat", {}, JSON.stringify({"text": text, "receiver": receiver}));
}
/**
 * @param message
 */
function showSendMsg(message) {
    var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $('.active-chat')[0].innerHTML += '<div class="bubble me">'.concat(message).concat('</div>');
    $('.write')[0].children[1].value = "";
}

/**
 * @param message
 */
function showGetMsg(message) {
    var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $('.active-chat')[0].innerHTML += '<div class="bubble you">'.concat(message.body).concat('</div>');
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

function showTime() {
    var now = new Date().Format("yyyy-MM-dd hh:mm:ss");
    $('.active-chat')[0].innerHTML += '<div class="conversation-start">' +
                                            '<span>'.concat(now).concat('</span>') +
                                      '</div>';
}

setInterval(showTime, 1000 * 60 * 2);
