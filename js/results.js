var result = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = "";

    console.log(`response: ${input}`);
    var obj = JSON.parse(input);
    var type = obj.result;

    if(!isEmpty(obj.jwt)) {
        console.log(`jwt: ${obj.jwt}`);
        data.isLogin = true;
        data.jwt_token = obj.jwt;
    }

    var data = obj.message;
    if(type == -1 || type == 0 || type == 1) {
        output.innerHTML = obj.message;
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
    var res = result(input);
    var btn_logout = document.getElementById("logoutForm");

    if(res == "bye" || res == "no login") {
        btn_logout.style="display:none";
    }
    else {
        btn_logout.style="display:inline-block";
    }
    
}