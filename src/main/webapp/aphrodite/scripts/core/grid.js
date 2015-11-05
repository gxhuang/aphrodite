(function(){
	//grid 与tree grid
	//如果是树结构，初始化数据时全都带有链接，如果点接连接查询无下级数据时，去除链接
	var Grid = function(binding,pageView){
		this.id = binding.attr("id") ;
		this.binding = binding ;
		this.tree= binding.attr("tree")
		this.status = undefined ;//grid的状态 浏览或者修改
		//this.fields = [] ;
		this.pageView = pageView ;
		this.editRecord = undefined ;
		this.records = new Array() ;

		//DELETE,INSERT,UPDATE,SELECT
		this.status = undefined ;
		this._init();
	};
	Grid.prototype = {
		_init : function() {
			// body...
			var jqtable = $(this.binding) ;
			var header = jqtable.find("thead") ;
			var ths = header.find("th") ;
			var pv = this.pageView
			$.each(ths,function(index,th){
				var jqField = $(this) ;
				var _field = jqField.getField() ;
				if(_field == undefined){
					_field = jqField._field() ;
				} 
				pv.addField(_field) ;
			});

			//init head toolbar
			this._initHeadToolbar();

			//init foot toolbar
			this.initFootToolbar() ;
		},
		/****/
		_initHeadToolbar : function(){
			var _jqtoolbar = this.binding.find(".btn-group").find("button").on("click",function(e){
				var _jq = $(e.target);
				var _jqgrid = _jq.parents("[name=grid]") ;
				var op = _jq.attr("name") ;
				if(op == "new"){
					//_jqgrid.addClass("hide").prev("br").prev("form").removeClass("hide") ;
					// var jqSForm = _jqgrid.siblings("form[search]") ;
					// if(!jqSForm.hasClass("hide")) jqSForm.addClass("hide")
					_jqgrid.addClass("hide") ;
					_jqgrid.getGrid().status = "INSERT" ;
					_jq.parents(".tab-pane").find("form[neworedit]").removeClass("hide") ;
				}else if(op == "edit"){
					var active = _jqgrid.find("tbody").find("tr").filter(".success") ;
					if(active == undefined || active.length == 0){
						alert("please select!") ;
					}else{
						var obj = {};
						active.find("td").each(function(index,ele){
							var _this = $(ele) ;
							obj[_this.attr("name")] = _this.text();
						}) ;
						//如果是grid的时候，search或者neworedit都是hide状态
						// var jqNeForm = _jqgrid.siblings("form[neworedit]") ;
						// if(!jqNeForm.hasClass("hide")) jqNeForm.addClass("hide")
						_jqgrid.addClass("hide").siblings("form[neworedit]").removeClass("hide") ;
						var form = _jqgrid.siblings("form[neworedit]").getForm().setData(obj) ;

					}
					_jqgrid.getGrid().status = "UPDATE" ;
				}else if(op == "submit"){					
					var dataset = _jqgrid.getGrid().pageView.dataset ;
					//提交
					var request = aphroditeSubmit(dataset) ;
					console.log(request)
				}else if(op == "search"){
					_jqgrid.addClass("hide") ;
					_jq.parents(".tab-pane").find("form[search]").removeClass("hide") ;
					var jqNeForm = _jq.siblings("form[neworedit]") ;
					if(!jqNeForm.hasClass("hide")) jqNeForm.addClass("hide") ;


					_jqgrid.getGrid().status = "SELECT" ;
					_jqgrid.getGrid().pageView.dataset.action = "select" ;
				}else{
					var dataset = _jqgrid.getGrid().pageView.dataset;
					var _func = _jq.attr("function") ;
					if(_func != undefined){
						_func(dataset) ;
						//_func实现的功能必须指定action与service操作都是有哪些？
						//根据service获取得到service bean
					}					
				}
			}) ;
		},
		_initThead : function(){

		},
		initFootToolbar:function(){
			var jqfoot = $(this.binding).find(".pager").find("li").find("a").on("click",function(e){
				alert("nl");
			}) ;
		},
		_initEvent:function(){

		},
		append:function(record){

		},
		insert:function(records){
			if(records == undefined || records.length <= 0){
				return ;
			}

			var htmltbody = "" ;
			for(var i = 0 ,max = records.length ;i < max ;i++){
				var allUndefined = true ;
				htmltbody +="<tr>" ;
				var record = records[i].recordVal ;
				for(var name in record){
					console.log(name)
					var field = this.pageView.getField(name) ;
					if (field.isHide) {
						console.log("hide")
						continue ;
					}

					if(record[name] != undefined && record[name]  != ""){
						allUndefined = false ;
					}

					//ID是后台生成还是前台生成
					htmltbody += "<td name="+name +">" ;				
					if(field.type == "search"){
						htmltbody += field.binding.val();
					}else{
						htmltbody += (record[name] == undefined ?"":record[name]) ;
					}
					htmltbody +="</td>" ;
				}
				htmltbody+="</tr>" ;

				
				record.status = this.status ;
				this.records[this.records.length] = record ;
				this.status = undefined ;
			}
			

			if(!allUndefined){
				this.binding.find("tbody").empty().append(htmltbody).find("tr").last().on("click",function(e){
					var _jqthis = $(this) ;
					_jqthis.parent("tbody").find("tr[class=success]").removeClass("success") ;
					_jqthis.addClass("success") ;
				});
				
			}			
		},
		update:function(record){
			//找到相应的记录，更新掉记录的值
		}
		/*,
		_loadData:function(records){
			this.records = records ;
			if(records == undefined || records.length == 0){
				return ;
			}
			//records 是一个数组
			var htmltbody = "" ;
			$.each(records,function(index,record){
				//应该是record.recordVal
				htmltbody+="<tr>"
				for(var field in record.recordVal){
					htmltbody += "<td name="+field+">" ;
					htmltbody +=record[field] ;
					htmltbody +="</td>"
				}
				htmltbody+="</tr>"
			});

			var jqTbody = this.binding.find("tbody") ;
			jqTbody.find("tr").unbind("click");
			jqTbody.empty();
			jqTbody.append(htmltbody) ;
			jqTbody.find("tr").on("click",function(e){
				var _jqthis = $(this) ;
				_jqthis.parent("tbody").find("tr[class=success]").removeClass("success") ;
				_jqthis.addClass("success") ;
			});
		}*/
	};

	$.fn.extend ({
		_grid:function(pageView){
			var _grid = new Grid(this,pageView) ;
			this.data("aphrodite.grid",_grid) ;
			return _grid ;
		},
		getGrid:function(){
			return 	this.data("aphrodite.grid") ;
		}
	});
})();