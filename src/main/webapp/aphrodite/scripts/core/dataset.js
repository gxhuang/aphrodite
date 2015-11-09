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

			this.initPageView(href) ;

            tablist.on("click",function(e){
            	var jqtarget = $(e.target) ;
            	var href = jqtarget.attr("href") ;
            	jqtarget.parents(".page-content").getDataset().initPageView(href) ;            	
            }) ;
			
		},
		initPageView:function(pageViewhref){
			if(!this.exists(pageViewhref.substring(1,pageViewhref.length))){
				//alert(111)
				var jqtab = $(pageViewhref) ;

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
			}			
		},
		addPageView:function(pageView){

			this.pageViews[this.pageViews.length] = pageView ;
		},
		exists:function(pageViewId){
			var isExists = false ;
			for(var i =0 ,max = this.pageViews.length ;i < max ;i++){
				if(this.pageViews[i].id == pageViewId){
					isExists = true ;
					break ;
				}
			}
			return isExists ;
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





