<html>
<body>
    <div>
        <h2>Hello World!</h2><br>
        select api url
        <select id="urls">
            <option value="https://api.emalron.com:8443">EC2</option>
            <option value="http://localhost:8080" selected>localhost</option>
        </select>        
    </div>
    <div style="float:left; width:50%">
        <div style="float:left; width:50%">
            <h3>leader board</h3>
            <button onclick="req_by_get()">show all ranks</button>
        </div>
        <div style="float:left; width:50%">
            <h3>show all users</h3>
            <form id="suserForm">
                <input type="hidden" name="cmd" value="showAllUsers">
                <input type="button" onclick="postForm('suserForm', result)" value="show all users">
            </form>
        </div>
        <div>
            <h3>find user</h3>
            <form id="myForm">
                <input type="hidden" name="cmd" value="searchRank">
                <input name="name" type="text" placeholder="name"> <input type="button" onclick="postForm('myForm', result)" value="search">
            </form>
        </div>
        <div>
            <h3>rank add</h3>
            <form id="addForm">
                <input type="hidden" name="cmd" value="addRank">
                <input name="id" type="text" placeholder="id">
                <input name="score" type="number">
                <input name="replay_data" type="text" placeholder="replay_data">
                <input type="button" onclick="postForm('addForm', result)" value="add">
            </form>
        </div>
        <div>
            <h3>delete rank record</h3>
            <form id="deleteRankForm">
                <input type="hidden" name="cmd" value="deleteRank">
                <input name="id" type="text" placeholder="id">
                <input type="button" onclick="postForm('deleteRankForm', result)" value="delete">
            </form>
        </div>
        <div>
            <h3>login</h3> login or register id
            <form id="loginForm">
                <input type="hidden" name="cmd" value="login">
                <input name="id" type="text" placeholder="id">
                <input name="name" type="text" placeholder="name">
                <input type="button" onclick="postForm('loginForm', loginResult)" value="login">
            </form>
            <form id="logoutForm">
                <input type="hidden" name="cmd" value="logout">
                <input type="button" onclick="postForm('logoutForm', loginResult)" value="logout">
            </form>
        </div>
        <div>
            <h3>Delete user</h3>
            <form id="deleteForm">
                <input type="hidden" name="cmd" value="deleteUser">
                <input name="id" type="text" placeholder="id">
                <input type="button" onclick="postForm('deleteForm', result)" value="delete me">
            </form>
        </div>
        <div>
            <h3>Alter user info</h3>
            <form id="alterForm">
                <input type="hidden" name="cmd" value="alterUserInfo">
                <input name="id" type="text" placeholder="id">
                <input name="name" type="text" placeholder="name">
                <input name="lang" type="text" value="ko">
                <input type="button" onclick="postForm('alterForm', result)" value="change me">
            </form>
        </div>
        <div>
            <h3>slack test</h3>
            <form id="slackForm">
                <input name="token" type="text" placeholder="token">
                <input name="channel" type="text" value="#requestlog">
                <input name="text" type="text" value="text">
                <input type="button" onclick="slack('slackForm')" value="push">
            </form>
        </div>
        <div>
            json test <input type="button" onclick="shot(result)" value="shot!"></input>
        </div>
    </div>
    <div id="output" style="float:left; width:50%"></div>
    <script src="js/results.js"></script>
    <script src="js/main.js"></script>
</body>
</html>
