(function(){
	var Form = function(binding,pageView){
		this.binding = binding ;
		//this.id = binding.attr("id") ;
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
			var _fields = this.binding.find(".form-control") ;

			var pageView = this.pageView
			$.each(_fields,function(index,_field){
				//$()
				var jqField = $(_field) ;
				var field = pageView.getField(jqField.attr("name")) ;
				field.type = jqField.attr("type") ;
				field.format = jqField.attr("format") ;
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
					jqField.on("change",function(e){
						var _jq = $(this) ;
						var oField = _jq.data(_jq.attr("id")) ;
						oField.value = _jq.val();
					}) ;
				}else if(this.type=="search"){
					//如果是搜索复合组件 对按钮的响应事件

					var search = jqField._search();

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
					var obj = {} ;
					var form =_jq.parents("form") ;

					var fields = form.getForm().pageView.fields ;
					for(var index in fields){
						var _field = fields[index] ;
						obj[_field.id] = _field.value ;
					}

					console.log(JSON.stringify(obj)) ;
					var isSearch = form.getForm().isSearch ;
					form.addClass("hide") ;
					var jqgrid = form.siblings("[name=grid]").removeClass("hide").getGrid() ;
					if(isSearch){
						function callback(data,jqgrid) {
							var dataset= JSON.parse(data) ;
							jqgrid.insert(dataset.pageViews[0].grid.records)  ;
						}
						aphroditeSelect(form.getForm().pageView.dataset,callback,jqgrid) ;
					}else{
						jqgrid.append(obj);					
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
