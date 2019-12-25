jdodge api
==========
api url: https://jdodge-1203598482.ap-northeast-2.elb.amazonaws.com/jdodge/service  
method: post/get  
test: https://jdodge-1203598482.ap-northeast-2.elb.amazonaws.com/jdodge
  
  
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
