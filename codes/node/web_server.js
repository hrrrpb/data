var http = require('http');

var s = http.createServer(function(req, res){
    req.on("data", function(chunk){
      //console.log("received data");
      //console.log("got %d bytes of data", chunk.length);
      //console.log(chunk.toString("utf8"));
      console.log(req);
});
    req.on("end", function(){
    console.log("return reply");
    res.end("hey, thanks for calling!") ;});

});

s.listen(8080);


