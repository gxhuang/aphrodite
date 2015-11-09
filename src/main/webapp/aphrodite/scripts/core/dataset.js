//闭包是很重要的，能改成闭包吗？


//--------------------------dataset start-------------------------
(function(){
	var Dataset = function(binding){
		// this.datasets =  ;
		this.binding = binding ;
		this.pageViews = new Array();
		this.action = "update" ;
		this.service = "jdbcService" ;
		this._init();
	} ;

	Dataset.prototype={
		_init:function(){
			var tablist = this.binding.find("[role=tablist]") ;
			
			var tab = tablist.find("li.active") ;
			var href = tab.find("a").attr("href") ;
			var jqtab = $(href) ;

			var pageView = jqtab._pageView(this) ;
			// dataset.addPageView(pageView) ;

			var jqgrid = pageView.binding.find("div[name=grid]") ;
            var grid = jqgrid._grid(pageView);
            pageView.grid = grid 

            // var _forms = pageView.forms ;
            var jqforms = pageView.binding.find("form") ;
            $.each(jqforms,function(index,jqform){
              var form = $(jqform)._form(pageView) ;
                pageView.forms[pageView.forms.length] = form ;
            }) ;
            this.addPageView(pageView) ;

            pageView.init();

            tablist.on("click",function(e){
            	alert("click")
            }) ;
			
		},
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





