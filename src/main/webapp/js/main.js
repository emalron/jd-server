var req_by_get = function() {
    tmp = {cmd: "showAllRanks"}
    str = JSON.stringify(tmp);

    ajax(str);
}

var postForm = function(form_id, callback) {
    event.preventDefault();

    var fData = new FormData(document.getElementById(form_id));
    var obj = {};
    for(var [key, value] of fData.entries()) {
        obj[key] = value;
    }

    var str = JSON.stringify(obj);

    ajax(str);
}

var shot = function(callback) {
    event.preventDefault();

    str = {cmd: "jsonTest", id: "jes"};
    str = JSON.stringify(str);

    ajax(str);
}

window.onload = function() {
    this.setUrl();
    var str = JSON.stringify({cmd:"loginCheck"});

    this.ajax(str);
}

var ajax = function(body) {
    var urls = document.getElementById("urls");
    var base_url = urls.options[urls.selectedIndex].value;
    var url = `${base_url}/jdodge/service`;

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(req.readyState == req.DONE) {
            if(req.status == 200 || req.status == 201) {
                var btn_logout = document.getElementById("logoutForm");
                if(req.responseText != "") {
                    loginResult(req.responseText);
                }
                else {
                    btn_logout.style="display:none";
                }
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