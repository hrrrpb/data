var jade = require('jade'),
    fs = require('fs');

var data = {
    title : "Practical node.js",
    author : {
         twitter : "@azat_co",
         name : "Azat"
    },
    tags : ['express', 'node', 'javascript']
   }

data.body = "this is the body"

fs.readFile('jade-example.jade', 'utf-8', function(error, source) {
   var html = jade.render(source, data)
   console.log(html)
   fs.writeFile("index.html", html, "utf-8",function(err) {
      if (err) {
       console.error(err); }
      else {
       console.log("data written successfully");
     }
   });
});


