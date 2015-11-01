(function(){

	//如果是一棵树，record可以嵌套record
	var Record = new function(data){
		this.id = data.id ;
		this.datatype = data.datatype ;
		this.binding = undefined ;

	}

	$.fn._record = function(){
    		var _record = new Record(this);
    		this.data("aphrodite.field",_record) ;
    	},
    	$.fn.getRecord = function(){
    		return this.data("aphrodite.record") ;
    	}
})();