(function(){
	var Grid = function(binding){
		this.binding = binding ;
		this.status = undefined ;//grid的状态 浏览或者修改
		this.fields = [] ;
		this._init();
	};
	Grid.prototype = {
		_init : function() {
		// body...
		var header = binding.find("table") ;
		var ths = header.find("th") ;
		$.each(ths,function(index,th){
			var jqField = $(this) ;
			var _field = jqFiele.getField() ;
			if(_field == undefined){
				_field = jqField._field() ;
			}
			this.fields[_field.id] = _field ;
		});


		//init head toolbar

		//initFields

		//init foot toolbar
		},
		/**
		_initHeadToolbar : function(){

		},
		_initThead : function(){

		},
		initFootToolbar:function(){

		},
		_initEvent:function(){

		},**/
		_loadData:function(records){
			if(records == undefined){
				return ;
			}
			//records 是一个数组
			var htmltbody = "" ;
			$.each(records,function(index,record){
				htmltbody += htmltbody + "<td"+record.value ;

				htmltbody += htmltbody + "></td>"
			});

			var jqTbody = this.binding.find("tbody") ;
			jqTbody.html("");
			jqTnody.append(htmltbody) ;
		}
	};
	$.fn.Grid = function(){
		var _grid = new Grid(this) ;
		this.data("aphrodite.grid",_grid) ;
	},
	$.fn.get = function(){
		this.data("aphrodite.grid") ;
	}
})();