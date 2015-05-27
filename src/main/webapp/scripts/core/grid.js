(function(){
	var Grid = function(binding){
		this.binding = binding ;
		this.status = undefined ;//grid的状态 浏览或者修改
		this._init();
	};
	Grid..prototype = {
		_init : function() {
		// body...


		//init head toolbar

		//initFields

		//init foot toolbar
		},
		_initHeadToolbar : function(){

		},
		_initThead : function(){

		},
		initFootToolbar:function(){

		},
		_initEvent:function(){

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