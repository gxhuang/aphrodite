(function(){

	//如果是一棵树，record可以嵌套record
	var Record = new function(_data){
		this.id = _data.id ;
		this.datatype = _data.datatype ;
		this.binding = undefined ;
		//记录变更字段，这样对于大表的处理性能会更高,与后台的相应变更要全局考虑
		this.changeFields = new Array();
	}

	$.fn._record = function(){
    		var _record = new Record(this);
    		this.data("aphrodite.field",_record) ;
    	},
    	$.fn.getRecord = function(){
    		return this.data("aphrodite.record") ;
    	}
})();