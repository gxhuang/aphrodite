
//form 组件有提交按钮 具体form有多种，查看，新增修改，查询 role="form" 
//有查询form 及新增修改form
//form是否应该有search delete add，每个页面是否有多个页面
(function(){
	var Form = function(binding){
		this.id = binding.attr("id") ;
		this.type="form" ;
		this.fields = []
		_init();
	};
	Form.prototype = {
		_init:function(){
			_initFields();
			_initBtnGroup();
		},
		_initFields:function(){
			//initFields
			var jqfields = this.binding.find("role='field'") ;
			$.each(jqfields,function(index,jqfield){
				//$()
				var jqField = $(field) ;
				var _field  = jqField.getField() ;
				if(_field==undefined){
					jqField._field() ;
				}
				this.fields[_field.id] = _field ;
			}) ;
		},
		_initBtnGroup:function(){
			// form 组件 $(".btn.btn-group") 
		}
		
	};
	$.fn._form = function(){
		var _form = new Form(this) ;
		this.data("aphrodite.form",_form) ;
	},
	$.fn.get = function(){
		return this.data("aphrodite.form") ;
	}
})();
