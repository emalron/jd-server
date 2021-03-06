var result = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = "";

    console.log(`response: ${input}`);
    var obj = JSON.parse(input);
    var type = obj.result;

    var data = obj.data;
    if(!data) {
        output.innerHTML = obj.message;
        return obj.message;
    }

    if(type == -1 || type == 0 || type == 1) {
        if(data.constructor == Object) {
            for(var d in data) {
                var str = `${d}: ${data[d]} `;
                output.innerHTML += str;
                output.innerHTML += "<br>";
            }
        }
        else if (data.constructor == Array) {
            for(var i=0; i < data.length; i++) {
                for(var key in data[i]) {
                    var str = `${data[i][key]} `;
                    output.innerHTML += str;
                }
                output.innerHTML += "<br>";
            }
        }
        else {
            output.innerHTML = obj.message;
        }
        return obj.message;
    }

    else if(type == 2) {
        for(var i=0; i < data.length; i++) {
            for(var key in data[i]) {
                var str = `${data[i][key]} `;
                output.innerHTML += str;
            }
            output.innerHTML += "<br>";
        }
    }
}

var isEmpty = function(value) {
    if(value == "" || value == null || value == undefined || ( value != null && typeof value == "object" && !Object.keys(value).length)) {
         return true;
    }
    else {
        return false
    }
};

var loginResult = function(input) {
    var obj = JSON.parse(input);

    if(obj.data && !isEmpty(obj.data.jwt)) {
        console.log(`jwt: ${obj.data.jwt}`);
        this.data.isLogin = true;
        this.data.jwt_token = obj.data.jwt;
    }

    var res = result(input);
    var btn_logout = document.getElementById("logoutForm");

    if(res == "bye" || res == "no login") {
        btn_logout.style="display:none";
    }
    else {
        btn_logout.style="display:inline-block";
    }
    
}