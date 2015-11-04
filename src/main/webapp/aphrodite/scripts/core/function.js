/*


*/
function aphroditeSubmit(dataset){
	JSON.stringify(grid.pageView,replace) ;
}

function aphroditeSelect(dataset){

}

/*
JSON.stringify(obj,replace)
*/
function replace(key,value){
	if(key == "dataset" || key == "pageView" || key == "binding" || key == "fieldIndexs" || typeof(value) == "function" || value == undefined || value == ""){
		return undefined ;
	}
	return value ;
}


function ajax(url,message,callback,jq){
    console.log("ajax message:"+message) ;
    $.ajax({
        type:"post",
        url:url,
        data:message
    }).done(function(data){
       if(callback != undefined){
           callback(jq,data) ;
       } 
    }).fail(function(data){
        alert("fail") ;
    }) ;
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