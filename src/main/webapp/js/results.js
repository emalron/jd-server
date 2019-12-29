var result = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = "";

    console.log(`response: ${input}`);
    var obj = JSON.parse(input);
    var type = obj.result;

    var data = obj.message;
    if(type == 0 || type == 1) {
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

var loginResult = function(input) {
    var res = result(input);
    var btn_logout = document.getElementById("logoutForm");

    if(res == "bye") {
        btn_logout.style="display:none";
    }
    else {
        btn_logout.style="display:inline-block";
    }
    
}