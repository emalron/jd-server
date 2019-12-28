var req_by_get = function() {
    var xhr = new XMLHttpRequest();

    if(!xhr) {
        alert("can not create XHR instance");
        return false;
    }

    var urls = document.getElementById("urls");
    var base_url = urls.options[urls.selectedIndex].value;
    var url = `${base_url}/jdodge/service?cmd=showAllRanks`;

    xhr.onreadystatechange = function() {
        if(xhr.readyState == xhr.DONE) {
            if(xhr.status == 200 || xhr.status == 201) {
                console.log(xhr.responseText);
                result(xhr.responseText);
            }
            else {
                console.error(xhr.responseText);
            }
        }
    };

    xhr.open('get', url);
    xhr.send();
}

var postForm = function(form_id, callback) {
    event.preventDefault();
    // var form_id = event.target.form.id;
    console.log(`form_id: ${form_id}`);

    var req = new XMLHttpRequest();
    
    req.onreadystatechange = function() {
        if(req.readyState == req.DONE) {
            if(req.status == 200 || req.status == 201) {
                console.log(req.responseText);
                if(req.responseText != "") {
                    callback(req.responseText);
                }
            }
            else {
                console.error(req.responseText);
            }
        }
    }

    var urls = document.getElementById("urls");
    var base_url = urls.options[urls.selectedIndex].value;
    var url = `${base_url}/jdodge/service`;

    var fData = new FormData(document.getElementById(form_id));
    var temp = [];
    for(var [key, value] of fData.entries()) {
        temp.push({key: key, value: value});
    }

    var str = temp[0].key + "=" + temp[0].value;
    for(var i=1; i<temp.length; i++) {
        str += "&" + temp[i].key + "=" + temp[i].value;
    }

    req.open('post', url);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send(str);
}

window.onload = function() {
    this.setUrl();

    var req = new XMLHttpRequest();
    req.onreadystatechange = function() {
        if(req.readyState == req.DONE) {
            if(req.status == 200 || req.status == 201) {
                console.log(req.responseText);
                if(req.responseText != "") {
                    loginResult(req.responseText);
                }
            }
            else {
                console.error(req.responseText);
            }
        }
    }

    var urls = document.getElementById("urls");
    var base_url = urls.options[urls.selectedIndex].value;
    var url = `${base_url}/jdodge/service?cmd=loginCheck`;

    req.open('get', url);
    req.send();
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