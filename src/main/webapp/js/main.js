var req_by_get = function() {
    tmp = {cmd: "showAllRanks"}
    str = JSON.stringify(tmp);

    ajax(str, result);
}

var postForm = function(form_id, callback) {
    event.preventDefault();

    var fData = new FormData(document.getElementById(form_id));
    var obj = {};
    for(var [key, value] of fData.entries()) {
        obj[key] = value;
    }

    var str = JSON.stringify(obj);

    ajax(str, callback);
}

var slack = function(form_id) {
    var fData = new FormData(document.getElementById(form_id));
    var obj = {};
    for(var [key, value] of fData.entries()) {
        obj[key] = value;
    }

    console.log(obj);

    var token = encodeURIComponent(obj["token"]);
    var channel = encodeURIComponent(obj["channel"]);
    var text = encodeURIComponent(obj["text"]);

    console.log(`token ${token}`);
    console.log(`channel ${channel}`);
    console.log(`text ${token}`);

    var url = "https://slack.com/api/chat.postMessage?token=" + token + "&channel=" + channel + "&text=" + text;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(req.readyState == req.DONE) {
            if(req.status == 200 || req.status == 201) {
                console.log(req.responseText);
            }
            else {
                console.error(req.responseText);
            }
        }
    }

    req.open('get', url);
    req.send();

    console.log(`post body: ${body}`);
}

var shot = function(callback) {
    event.preventDefault();

    str = {cmd: "jsonTest", id: "jes"};
    str = JSON.stringify(str);

    ajax(str, callback);
}

window.onload = function() {
    this.setUrl();
    var str = JSON.stringify({cmd:"loginCheck"});

    this.ajax(str, this.loginResult);
}

var ajax = function(body, callback) {
    var urls = document.getElementById("urls");
    var base_url = urls.options[urls.selectedIndex].value;
    var url = `${base_url}/jdodge/service`;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(req.readyState == req.DONE) {
            if(req.status == 200 || req.status == 201) {
                callback(req.responseText);
            }
            else {
                console.error(req.responseText);
            }
        }
    }

    req.open('post', url);
    req.setRequestHeader("Content-Type", "application/json");
    req.send(body);

    console.log(`post body: ${body}`);
}

var setUrl = function() {
    var index = getCookie("url");

    if(index != null) {
        var target = document.getElementById("urls");
        target.selectedIndex = index;
    }
}

var url_save = function() {
    var target = document.getElementById("urls");
    var index = target.selectedIndex;

    var date = new Date();
    date.setDate(date.getDate() + 1);

    var cookie = `url=${index};expires=${date.toUTCString()}`;

    document.cookie = cookie;
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  }

document.getElementById("urls").onchange = url_save;