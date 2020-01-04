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
> parameter: {cmd: addRank, id: "...", score: x, replay_data: "..."}
> return: {result: 0, message: "..."}  
  
> searchRank  
> parameter: {cmd: searchRank, name: "..."}  
> return: {result: 2, message: [...]}  

> login  
> parameter: {cmd: login, id: "...", name: "..."}  
> return: {result: 0, message: "..."}  
  
> logout  
> parameter: {cmd: logout}  
> return: {result: 0, message: "..."}  
  
> loginCheck  
> parameter: {cmd: logout}  
> return: {result: 0, message: "..."}  
  
> deleteUser  
> parameter: {cmd: deleteUser, id: "..."}  
> return: {result: 0, message: "..."}  
  
> alterUserInfo  
> parameter: {cmd: alterUserInfo, id: "...", name: "...", lang: "..."}  
> return: {result: 0, message: "..."}  