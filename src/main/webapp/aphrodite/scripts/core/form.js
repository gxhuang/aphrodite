(function(){
	var Form = function(binding,pageView){
		this.binding = binding ;
		//this.id = binding.attr("id") ;
		this.type="form" ;
		this.isSearch = (binding.attr("search") != undefined ? true : false) ;
		this.fieldbindings = new Array();

		this.pageView = pageView ;
		this._init();
	};
	Form.prototype = {
		_init:function(){
			this._initFields();
			this._initFunction();
			//_initBtnGroup();
		},
		clean:function(){
			for(var prop in this.fieldbindings){
				if(prop == undefined || typeof(prop) == "function"){
					continue ;
				}
				this.fieldbindings[prop].val("");
			}
		} ,
		_initFields:function(){
			var _fields = this.binding.find(".form-control") ;

			var pageView = this.pageView ;
			var bindings = this.fieldbindings ;
			$.each(_fields,function(index,_field){
				// alert(index)
				var jqField = $(_field) ;
				bindings[jqField.attr("name")] = jqField ;
				var field = pageView.getField(jqField.attr("name")) ;
				if(field == undefined ){
					field = jqField._field();
				}else {
					field.type = jqField.attr("type") ;
					field.format = jqField.attr("format") ;
				}

				if(field.datatype == "date"){
					jqField.closest(".form_date").datetimepicker({
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
					});
					//event
				}else if(this.type == "text"){
					jqField.on("keyup",function(e){
						var _jq = $(this) ;
						var oField = pageView.getField(_jq.attr("name")) ;
						oField.value = _jq.val();
					}) ;
				}else if(this.type=="search"){
					//如果是搜索复合组件 对按钮的响应事件
					//alert(this.id) ;
					var search = jqField._search();
					// if(search.tableName == undefined || search.tableName == ""){
					// 	if(pageView.codeValue[search.key] == undefined){
					// 		pageView.codeValue[search.key] = "" ;
					// 	}						
					// }
				}
			}) ;
		},
		_initFunction:function(){
			var jqtoolbar = this.binding.find("[name=toolbar]").find("a").on("click",function(e){
				var _jq = $(e.target);
				var name = _jq.attr("name") ;
				if("cancle" == name){
					_jq.parents("form").addClass("hide").siblings("[name=grid]").removeClass("hide") ;
				}else if("submit" == name){
					//区分新增或者是修改
					var obj = {} ;
					var jqForm = _jq.parents("form") ;
					var form =jqForm.getForm() ;					
					var isSearch = form.isSearch ;
					var pageView = form.pageView ;

					jqForm.addClass("hide") ;
					var grid = jqForm.siblings("[name=grid]").removeClass("hide").getGrid() ;

					var fields = pageView.fields ;
					for(var index =0 ,max = fields.length ;index < max ;index++){
						var _field = fields[index] ;
						obj[_field.name] = _field.value ;
					}					
					
					if(isSearch){
						function _callback(_data,obj) {
							var dataset= JSON.parse(_data) ;
							obj.binding.find("tbody").empty();
							obj.insert(dataset.pageViews[0].grid.records)  ;
						}
						grid.clean();
						aphroditeSelect(pageView.dataset,_callback,grid) ;
					}else{
						if("INSERT" == pageView.grid.status){
							grid.append(obj);	
						}else{
							grid.update(obj);	
						}
										
					}
				}
			})
		},
		setData:function(record){
			var values = record.recordVal ;
			for(var prop in values){
				var binding = this.fieldbindings[prop];
				if(binding != undefined){
					var value = values[prop] ;
					this.pageView.getField(binding.attr("name")).value = value
					if(binding.attr("type") == "search" && (binding.attr("tableName") == undefined ||binding.attr("tableName") == "")){
						value = this.pageView.codeValue[binding.attr("key")][value]
					}
					binding.val(value) ;
					// binding.value = values[prop] ;

				}
			}
		}
	};

	$.fn.extend ({
		_form:function(pageView){
			var form = new Form(this,pageView) ;
			$(this).data("aphrodite.form",form) ;
			return form ;
		},
		getForm:function(){
			return $(this).data("aphrodite.form") ;
		}
	});
})();
