//name //字段名，区分不同的字段
//datatype//字段数据类型
//value//from to该如何表示
//format//如果是日期类型，需要有格式化参数
//binding // 双向绑定
//initType，基础数据如何取得,字典类型，dialog类型,combobox类型，需要加载基础数据
//权限  访问权限  无此权限不查询该字段

//按键权限的命名规则  grid.button  form.button  带有action属性的按钮可提交的按键

(function(){
	var Field = function(binding){
		//this.binding = binding ;
		// this.selector = this.binding.selector ;
		this.id = binding.attr("id") ;
		this.name = binding.attr("name");
		this.op = binding.attr("op");//查询操作才有这个
		//用来区分响应事件的
		this.type = binding.attr("type") ;
		this.value = undefined ;
		this.datatype = binding.attr("datatype") ;
		this.format = binding.attr("format") ;//日期及数据精度模式
		this.fromValue = undefined ;//开始日期
		this.toValue = undefined ;//结束日期
		this.minView = undefined ;
		this.isHide = binding.hasClass("hide") ;
		//如果是th则不需要这个初始化动作
		this._init() ;
	};
	Field.prototype = {
		// body...
		_init:function(){
			this._initField();
			//this._initEvent() ;
		},
		_initField:function(){
			
		}
	};
	$.fn.extend({
		_field : function(){
    		var field = new Field(this);
    		//this.data(field.id,field) ;
    		return field ;
    	}
	});
})();
