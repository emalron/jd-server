#jdodge api

api url: http://ec2-13-124-178-78.ap-northeast-2.compute.amazonaws.com:8080/jdodge/service
method: post/get

##api list

*showAll
  usage: .../jdodge/service/cmd=showAll
  parameter: none
  return: array JSON [{name: "XXX", score: 123}, {name: "XXX", score: 23} .. ]
  
*addRank
  usage: .../jdodge/service/cmd=addRank?name="jes"&score="123"
  parameter: name, score
  return: none
  
*searchRank
  usage: .../jdodge/service/cmd=addRank?name=jes
  parameter: name
  return: array JSON [{name: "jes", score: 123}, {name: "jes", score: 23} .. ]
