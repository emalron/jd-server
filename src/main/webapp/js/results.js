var result = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = "";

    console.log(input);

    var jsonObj = JSON.parse(input);
    if(Array.isArray(jsonObj)) {
        for(var i=0; i < jsonObj.length; i++) {
            console.log(jsonObj[i]);
            var obj = jsonObj[i];

            var str = `${obj.name} / ${obj.score}<br>`;
            output.innerHTML += str;
        }
    }
    else {
        var str = `${obj.name} / ${obj.score}<br>`;
        output.innerHTML += str;
    }
}

var loginResult = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = input;
}

var usersResult = function(input) {
    var output = document.getElementById("output");
    output.innerHTML = "";

    console.log(input);

    var jsonObj = JSON.parse(input);

    if(Array.isArray(jsonObj)) {
        for(var i=0; i < jsonObj.length; i++) {
            console.log(jsonObj[i]);
            var obj = jsonObj[i];

            var str = `${obj.id} / ${obj.name} / ${obj.lang}<br>`;
            output.innerHTML += str;
        }
    }
    else {
        var str = `${obj.id} / ${obj.name} / ${obj.lang}<br>`;
        output.innerHTML += str;
    }
}