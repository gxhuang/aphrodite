(function(){
	var Grid = function(binding){
		this.id = binding.attr("id") ;
		this.binding = binding ;
		this.status = undefined ;//grid的状态 浏览或者修改
		this.fields = [] ;
		this._init();
	};
	Grid.prototype = {
		_init : function() {
			// body...
			var jqtable = $(this.binding) ;
			var header = jqtable.find("thead") ;
			var ths = header.find("th") ;
			$.each(ths,function(index,th){
				var jqField = $(this) ;
				var _field = jqField.getField() ;
				if(_field == undefined){
					_field = jqField._field() ;
				}
				//this.fields[_field.id] = _field ;
			});


			//init head toolbar
			this._initHeadToolbar();

			//init foot toolbar
			this.initFootToolbar() ;
		},
		/****/
		_initHeadToolbar : function(){
			var _jqtoolbar = this.binding.find(".btn-group").find("button").on("click",function(e){
				//alert("button") ;
				var _jq = $(e.target);
				var _jqgrid = _jq.parents("[name=grid]") ;
				var op = _jq.attr("name") ;
				if(op == "new"){
					_jqgrid.addClass("hide").prev("br").prev("form").removeClass("hide") ;
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

						_jqgrid.addClass("hide").prev("br").prev("form").removeClass("hide") ;
						var form = _jqgrid.prev("br").prev("form").getForm().setData(obj) ;

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
		_loadData:function(records){
			if(records == undefined){
				return ;
			}
			//records 是一个数组
			var htmltbody = "" ;
			$.each(records,function(index,record){
				htmltbody+="<tr>"
				for(var field in record){
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
				_jqthis.attr("class","success") ;
			});
		}
	};

	$.fn.extend ({
		_grid:function(){
			var _grid = new Grid(this) ;
			this.data("aphrodite.grid",_grid) ;
			return _grid ;
		},
		getGrid:function(){
			this.data(aphrodite.grid) ;
		}
	});
})();