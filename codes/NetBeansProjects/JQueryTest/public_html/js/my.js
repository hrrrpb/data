$(document).ready(function(){
         //$("h1").css("color","red");
         $("h1").animate({"color":"red"},1000);
    });

$(document).ready(function(){
    $("button").click(function(event){
        event.preventDefault();
       // var url ="http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
        var url = "http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=3ac9b1f2b20bf90148ad4f887500db15";
    $.getJSON(url+'&callback=?', function(data){
        console.log(data.city.name);
        $("#data").html(data.city.name);
    });
    
//        $.getJSON(url, function(data){
//        
//        console.log("completed");
//        $("#data").html(data.city.name);
//        });
        
        
        
    });
    
 });