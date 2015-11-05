(function(){
	var Form = function(binding,pageView){
		this.binding = binding ;
		this.id = binding.attr("id") ;
		this.type="form" ;
		this.isSearch = (binding.attr("search") != undefined ? true : false) ;
		//this.fields = new Array();
		this.pageView = pageView ;
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
			//var fields = this.fields ;
			var _fields = this.binding.find(".form-control") ;

			var pv = this.pageView
			$.each(_fields,function(index,field){
				//$()
				var jqField = $(field) ;
				var _field  = jqField.getField() ;
				if(_field == undefined){
					_field = jqField._field() ;
				}
				pv.addField(_field) ;
			}) ;
		},
		_initFunction:function(){
			var jqtoolbar = this.binding.find("[name=toolbar]").find("a").on("click",function(e){
				var _jq = $(e.target);
				var name = _jq.attr("name") ;
				if("cancle" == name){
					_jq.parents("form").addClass("hide").siblings("[name=grid]").removeClass("hide") ;
				}else if("submit" == name){
					var obj = {} ;
					var form =_jq.parents("form") ;

					var fields = form.getForm().pageView.fields ;
					for(var index in fields){
						var _field = fields[index] ;
						obj[_field.id] = _field.value ;
					}

					console.log(JSON.stringify(obj)) ;
					var isSearch = form.getForm().isSearch ;
					if(isSearch){
						function selectCallback(jqgrid,records) {

						}

						aphroditeSelect(form.getForm().pageView.dataset,selectCallback) ;
					}else{
						form.siblings("[name=grid]").removeClass("hide").getGrid().insert(obj);
						//form.addClass("")
						form.addClass("hide") ;
					}
				}
			})
		},
		setData:function(record){
			for(var prop in record){
				var field = this.pageView.fields[prop] ;
				if(field != undefined){
					field.binding.val(record[prop]) ;
					field.value = record[prop] ;
				}
			}
		},
		_initBtnGroup:function(){
			// form 组件 $(".btn.btn-group")
		}

	};

	$.fn.extend ({
		_form:function(pageView){
			var form = new Form(this,pageView) ;
			$(this).data(form.id,form) ;
			return form ;
		},
		getForm:function(){
			return $(this).data(this.attr("id")) ;
		}
	});
})();
