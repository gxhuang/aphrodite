function post(dataset){
    var body = JSON.stringify(dataset,replace) ;

    $.ajax({
        type:"post",
        url:"",
        data:body
    }).done(function(data){

    }).fail(function(data){

    }) ;
}

function load(){
}

function replace(key,value){
    return typeof(value) == "function" ? undefined:value  ;
}