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
				}else if("submit" == name){
					alert(123) ;
					var obj = {} ;
					var form =_jq.parents("form") ;
					var fields = form.getForm().fields ;

					for(var index in fields){
						var _field = fields[index] ;
						obj[index] = _field.value ;
					}

					form.next("br").next("[name=grid]").removeClass("hide").getGrid().addData(obj);
					form.addClass("hide") ;

				}
			})
		},
		setData:function(record){
			for(var prop in record){
				var field = this.fields[prop] ;
				if(field != undefined){
					field.binding.val(record[prop]) ;
                	field.value = record[prop] ;
				}

			}
//			alert();
//			alert(record.name);
		},
		_initBtnGroup:function(){
			// form 组件 $(".btn.btn-group")
		}

	};

	$.fn.extend ({
		_form:function(){
			var form = new Form(this) ;
			$(this).data(form.id,form) ;
			return form ;
		},
		getForm:function(){
			return $(this).data(this.attr("id")) ;
		}
	});
})();
