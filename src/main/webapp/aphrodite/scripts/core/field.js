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
		this.binding = binding ;
		this.id = binding.attr("id") ;
		this.type="field" ;
		this.value = undefined ;
		this.datatype = binding.attr("datatype") ;
		this.format = binding.attr("format") ;//日期及数据精度模式
		this.fromValue = undefined ;//开始日期
		this.toValue = undefined ;//结束日期
		this.minView = undefined ;
		_init() ;
	};
	Field.prototype. = function() {
		// body...
		_init:function(){
			_initField();
			_initEvent() ;
		},
		_initField:function(){
			if(this.type == "date"){
				this.binding.datetimepicker({
					language : 'zh-CN',
					format : this.format,
					autoclose : true,
					todayBtn : true,
					todayHighlight : true,
					minView : this.minView,
					pickerPosition : "bottom-left"
				});
				//event
			}else if(this.type == "text"){
				this.binding.on("change",function(e){
					this.value = $(this).val();
				}) ;
			}else if(this.type=="search"){
				//如果是搜索复合组件 对按钮的响应事件
			}
		},
		_initToolbar : function(){
			var jqtoolbar = this.("[dataset=toolbar]") ;
		}
	};
	$.fn._field = function(){
		var _field = new Field(this);
		this.data("aphrodite.field",_field) ;
	},
	$.fn.get = function(){
		return this.data("aphrodite.field") ;
	}
})();
