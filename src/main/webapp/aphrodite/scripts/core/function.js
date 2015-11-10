/*


*/
function aphroditeSubmit(dataset){
	var request = JSON.stringify(dataset,replace) ;
	var url = "aphroditeServlet" 
	ajaxFunction(url , request) ;
}

function aphroditeSelect(dataset,_callback,jqgrid){
	var request = JSON.stringify(dataset,replace) ;
	var url = "aphroditeServlet" 
	ajaxFunction(url , request,_callback,jqgrid) ;
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


function ajaxFunction(url,message,_callback,obj){
    console.log("ajax message:"+message) ;
    $.ajax({
        type:"post",
        url:url,
        data:message
    }).done(function(_data){
       if(obj != undefined && _callback != undefined){
           _callback(_data,obj) ;
       } else if(_callback != undefined){
       	   _callback(_data) ;
       }else{
       	   alert("submit sucessful!") ;
       }
    }).fail(function(_data){
        alert("fail") ;
    }) ;
}

function get(url,message){

}

/*function login(dataset,_callback){
    var body = JSON.stringify(dataset,replace) ;
    var url = "loginServlet" ;
    ajax(url,body,callback) ;
}*/

function getData(arr,_callback,obj){
    var url = "commonServlet" ;
    var message = JSON.stringify(arr) ;
    ajaxFunction(url,message,_callback,obj) ;
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