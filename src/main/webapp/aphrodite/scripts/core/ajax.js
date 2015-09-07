function post(dataset){
    var body = JSON.stringify(dataset,replace) ;

    $.ajax({
        type:"post",
        url:"loginServlet",
        data:body
    }).done(function(data){
        alert("done") ;
    }).fail(function(data){
        alert("fail") ;
    }) ;
}

function load(){
}

function replace(key,value){
    return typeof(value) == "function" ? undefined:value  ;
}