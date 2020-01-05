jdodge api
==========
api url: https://api.emalron.com:8443/jdodge/service  
method: post/get  
test: https://api.emalron.com:8443/jdodge
  

# api list
> showAllUsers
> parameter: {cmd: showAllUsers}  
> return: {result: 2, message: [...]}  
  
> addRank  
> parameter: {"cmd": "addRank", "id": "...", "score": x, "replay_data": "..."}
> return: {result: 0, message: "..."}  

> login  
> parameter: {"cmd": "login", "id": "...", "name": "..."}  
> return: {result: 0, message: "..."}  
  
> logout  
> parameter: {"cmd": "logout"}  
> return: {result: 0, message: "..."}  
  
> loginCheck  
> parameter: {"cmd": "logout"}  
> return: {result: 0, message: "..."}  
  
> deleteUser  
> parameter: {"cmd": "deleteUser", id: "..."}  
> return: {result: 0, message: "..."}  
  
> alterUserInfo  
> parameter: {"cmd": "alterUserInfo", "id": "...", "name": "...", "lang": "..."}  
> return: {result: 0, message: "..."}  
  
> showRanks  
> parameter: {"cmd": "showRanks", "id": "...", "mode": "..."}  
> id를 입력하면 입력한 아이디의 기록을 보여줌 null 이면 없음  
> mode는 정렬할 기준. 다음 중 택1: "score", "date"  
> return: {result: 0, message: "..."}  
  
> whereIam  
> parameter: {"cmd": "whereIam", "id": "...", "rows_number": x}  
> id에 입력된 id 근처를 찾음
> rows_number에 입력된 숫자가 검색 범위이며, id의 최고점 기준으로 위, 아래 rows_number/2 씩 찾음
> return: {result: 0, message: "..."}  