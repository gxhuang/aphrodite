(function(){
	alert("form")
	var Form = function(binding){
	    this.binding = binding ;
		this.id = this.binding.attr("id") ;
		this.type="form" ;
		this.fields = new Array();
		this._init();
	};
	Form.prototype = {
		_init:function(){
			this._initFields();
			//_initBtnGroup();
		},
		_initFields:function(){
			//initFields
			var fields = this.fields ;

			var _fields = this.binding.find(".form-control") ;


			$.each(_fields,function(index,field){
				//$()
				var jqField = $(field) ;
				var _field  = jqField.getField() ;
				if(_field == undefined){
					_field = jqField._field() ;
				}
				fields[_field.id]= _field ;
			}) ;
		},
		_initBtnGroup:function(){
			// form 组件 $(".btn.btn-group")
		}

	};

	$.fn.extend ({
		_form:function(){
			var form = new Form(this) ;
			this.data(form.id,form) ;
			return form ;
		},
		getForm:function(){
			this.data(this.attr("id")) ;
		}
	});
})();
