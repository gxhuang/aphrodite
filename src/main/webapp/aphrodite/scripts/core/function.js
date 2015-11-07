/*


*/
function aphroditeSubmit(dataset){
	var request = JSON.stringify(dataset,replace) ;
	var url = "aphroditeServlet" 
	ajax(url , request) ;
}

function aphroditeSelect(dataset,callback,jqgrid){
	var request = JSON.stringify(dataset,replace) ;
	var url = "aphroditeServlet" 
	ajax(url , request,callback,jqgrid) ;
}

function select(){

}

/*
JSON.stringify(obj,replace)
*/
function replace(key,value){
	if(key == "dataset" || key == "codeValue" || key=="forms" || key == "pageView" || key == "binding" || key == "fieldIndexs" || typeof(value) == "function" || value == undefined || value == ""){
		return undefined ;
	}

	return value ;
}


function ajax(url,message,callback,obj){
    console.log("ajax message:"+message) ;
    $.ajax({
        type:"post",
        url:url,
        data:message
    }).done(function(data){
       if(obj != undefined && callback != undefined){
           callback(data,obj) ;
       } else if(callback != undefined){
       	   callback(data) ;
       }else{
       	   alert("submit sucessful!") ;
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

function getData(arr,callback,obj){
    var url = "commonServlet" ;
    var message = JSON.stringify(arr) ;
    ajax(url,message,callback,obj) ;
}

function load(){
}


function toPageView(fields,table){
	var pageView = new Object();
	pageView.fields = fields ;
	pageView.name = table ;	
	pageView.form = new Object();
	return pageView ;
}