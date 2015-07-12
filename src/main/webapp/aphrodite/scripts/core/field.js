//name //字段名，区分不同的字段
//datatype//字段数据类型
//value//from to该如何表示
//format//如果是日期类型，需要有格式化参数
//binding // 双向绑定
//initType，基础数据如何取得,字典类型，dialog类型,combobox类型，需要加载基础数据
//权限  访问权限  无此权限不查询该字段

//按键权限的命名规则  grid.button  form.button  带有action属性的按钮可提交的按键

(function(){
	//alert("field") ;
	var Field = function(binding){
		this.binding = binding ;

		var selector = this.binding.selector ;

		this.id = this.binding.attr("id") ;
		this.type = this.binding.attr("type") ;
		this.selector= selector ;
		this.value = undefined ;
		this.datatype = this.binding.attr("datatype") ;
		this.format = this.binding.attr("format") ;//日期及数据精度模式
		this.fromValue = undefined ;//开始日期
		this.toValue = undefined ;//结束日期
		this.minView = undefined ;
		//如果是th则不需要这个初始化动作
		if(this.type != "th"){
			this._init() ;
		}

	};
	Field.prototype = {
		// body...
		_init:function(){
			this._initField();
			//this._initEvent() ;
		},
		_initField:function(){
			if(this.datatype == "date"){
				this.binding.closest(".form_date").datetimepicker({
					format : "yyyy-mm-dd",
					autoclose : true,
					todayBtn : true,
					todayHighlight : true,
					minView : 3,
					pickerPosition : "bottom-left"
				}).on("changeDate",function(e){
				    var _jq = $(this).find("input") ;
				    var o = _jq.data(_jq.attr("id"))
					var date = e.date ;
					o.value = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate() ;
					//alert(date);
				});
				//event
			}else if(this.type == "text"){
				this.binding.on("change",function(e){
					var _jq = $(this) ;
					var oField = _jq.data(_jq.attr("id")) ;
					oField.value = _jq.val();
				}) ;
			}else if(this.type=="search"){
				//如果是搜索复合组件 对按钮的响应事件
				this.binding.next("div").find("button").on("click",function(e){
					var _this = $(this) ;
					var _jqul = $(_this.next("ul")) ;
					_jqul.empty();
					_jqul.append("<li><a href=\"#\">Action</a></li><li><a href=\"#\">Another action</a></li><li><a href=\"#\">Something else here</a></li><li class=\"divider\" role=\"separator\"></li><li><a href=\"#\">Separated link</a></li>")

					var _jqlis = _jqul.children("li").children("a").on("click",function(e){
						alert(12345);
					}) ;
//					$.each(_jqlis,function(index,_li){
//						$(_li).find("a").on("click",function(e){
//							alert(12345);
//						});
//					}) ;
				});
			}
		}
	};
	$.fn.extend({
		_field : function(){
    		var field = new Field(this);
    		this.data(field.id,field) ;
    		return field ;
    	},
    	getField:function(){
    		this.data(this.attr("id")) ;
    	}
	});
})();
