function post(dataset,callback){
    var body = JSON.stringify(dataset,replace) ;

    $.ajax({
        type:"post",
        url:"loginServlet",
        data:body
    }).done(function(data){
       if(callback != undefined){
           callback(data) ;
       }
    }).fail(function(data){
        alert("fail") ;
    }) ;
}

function load(){
}

function replace(key,value){
    return typeof(value) == "function" ? undefined:value  ;
}