//闭包是很重要的，能改成闭包吗？


//--------------------------dataset start-------------------------
(function(){
	var Dataset = function(){
		// this.datasets =  ;
		this.binding = undefined ;
		this.pageViews = new Array();
		this.action = "submit" ;
		this.service = "jdbcService" ;
	} ;

	Dataset.prototype={
		addPageView:function(pageView){
			this.pageViews[this.pageViews.length] = pageView ;
		}
	}

	$.fn.extend ({
		_dataset:function(){
			var _dataset = new Dataset(this) ;
			this.data("aphrodite.dataset",_dataset) ;
			return _dataset ;
		},
		getDataset:function(){
			return 	this.data("aphrodite.dataset") ;
		}
	});
})();

//--------------------------dataset end----------------------------





