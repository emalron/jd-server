jdodge api
==========
api url: https://api.emalron.com:8443/jdodge/service  
method: post/get  
test: https://api.emalron.com:8443/jdodge
  
  
# api list

* showAll
> usage: {url}/jdodge/service?cmd=showAll  
> parameter: none  
> return: array JSON [{name: "XXX", score: 123}, {name: "XXX", score: 23} .. ]  
  
* addRank  
> usage: {url}/jdodge/service?cmd=addRank&name=jes&score=123&replay_data=ANYTHING
> parameter: name, score, replay_data
> return: none  
  
* searchRank  
> usage: {url}/jdodge/service?cmd=addRank&name=jes  
> parameter: name  
> return: array JSON [{name: "jes", score: 123}, {name: "jes", score: 23} .. ]

* login
> usage: {url}/jdodge/service?cmd=login&id=abc&name=jes
> parameter: id, name
> return: plain text "Welcome, {name}"

* logout
> usage: {url}/jdodge/service?cmd=logout
> parameter: -
> return: plain text "bye"

* searchRank  
> usage: {url}/jdodge/service?cmd=loginCheck
> parameter: -
> return: plain text "Welcome back, {name}"