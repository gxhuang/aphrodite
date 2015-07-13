(function(){
	//alert("form")
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
			this._initFunction();
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
		_initFunction:function(){
			var jqtoolbar = this.binding.find("[name=toolbar]").find("a").on("click",function(e){
				var _jq = $(e.target);
				var name = _jq.attr("name") ;
				if("cancle" == name){
					_jq.parents("form").addClass("hide").next("br").next("[name=grid]").removeClass("hide") ;
//					var _jqform = _jq.parents("form") ;
//					_jqform.addClass("hide")
//					_jqform.next("br").next("[name=grid]").removeClass("hide") ;
				}
			})
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
