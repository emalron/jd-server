<html>
    <head>
        <script>
            var req = function() {
                var xhr = new XMLHttpRequest();

                if(!xhr) {
                    alert("can not create XHR instance");
                    return false;
                }

                var base_url = "http://ec2-13-124-178-78.ap-northeast-2.compute.amazonaws.com:8080/"
                var url = "/jdodge/test?cmd=test";

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

            var result = function(input) {
                var output = document.getElementById("output");
                output.innerHTML = "";

                console.log(input);

                var jsonObj = JSON.parse(input);
                if(Array.isArray(jsonObj)) {
                    for(var i=0; i < jsonObj.length; i++) {
                        console.log(jsonObj[i]);
                        var obj = jsonObj[i];

                        var str = obj.id + " " + obj.name + " " + obj.age + "<br>";
                        output.innerHTML += str;
                    }
                }
                else {
                    var str = "id: " + jsonObj.id + " name: " + jsonObj.name + ", age: " + jsonObj.age + "<br>";
                    output.innerHTML += str;
                }
                
            }

            var postForm = function(event) {
                event.preventDefault();
                var form_id = event.target.form.id;

                var req = new XMLHttpRequest();
                
                req.onreadystatechange = function() {
                    if(req.readyState == req.DONE) {
                        if(req.status == 200 || req.status == 201) {
                            console.log(req.responseText);
                            if(req.responseText != "") {
                                result(req.responseText);
                            }
                        }
                        else {
                            console.error(req.responseText);
                        }
                    }
                }

                var url = "/jdodge/test";
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
        </script>
    </head>
<body>
    <div>
        <h2>Hello World!</h2>
    </div>
    <div>
        <h3>user list</h3>
        <button onclick="req()">show all users</button>
    </div>
    <div>
        <h3>find user</h3>
        <form id="myForm">
            <input type="hidden" name="cmd" value="search">
            <input name="name" type="text" placeholder="name"> <input type="button" onclick="postForm(event)" value="search">
        </form>
    </div>
    <div>
        <h3>user add</h3>
        <form id="addForm">
            <input type="hidden" name="cmd" value="add">
            <input name="name" type="text" placeholder="name">
            <input name="age" type="number" placeholder="age">
            <input type="button" onclick="postForm(event)" value="add">
        </form>
    </div>
    <div id="output"></div>
</body>
</html>
