function ajax(url,message,callback,setting){
    var result = undefined ;
    $.ajax({
        type:"post",
        url:url,
        data:message
    }).done(function(data){
       if(callback != undefined){
           callback(data) ;
       } else{
           result = data ;
       }
    }).fail(function(data){
        alert("fail") ;
    }) ;
    return result ;
}

function get(url,message){

}

function login(dataset,callback){
    var body = JSON.stringify(dataset,replace) ;
    var url = "loginServlet" ;
    ajax(url,body,callback) ;
}

function getData(arr,callback){
    var url = "commonServlet" ;
    var message = JSON.stringify(arr) ;
    ajax(url,message,callback) ;
}

function load(){
}

function replace(key,value){
    return typeof(value) == "function" ? undefined:value  ;
}