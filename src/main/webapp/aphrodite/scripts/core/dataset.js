//闭包是很重要的，能改成闭包吗？


//--------------------------dataset start-------------------------
(function(){
	var Dataset = function(binding){
		// this.datasets =  ;
		this.binding = binding ;
		this.pageViews = new Array();
		this.action = "update" ;
		this.service = "jdbcService" ;
		this.currentPageView = undefined ;
		this._init();
	} ;

	Dataset.prototype={
		_init:function(){
			var tablist = this.binding.find("[role=tablist]") ;
			
			var tab = tablist.find("li.active") ;
			var href = tab.find("a").attr("href") ;
			this.currentPageView = href.substring(1,href.length)
			this.initPageView(href) ;

			// tablist.find("a[href=#sysDictDet]").tab("show") ;

            tablist.on("click",function(e){

            	var jqtarget = $(e.target) ;
            	var href = jqtarget.attr("href") ;
            	var dataset = jqtarget.parents(".page-content").getDataset() ;
            	var flag = dataset.initPageView(href) ;  
            	if(!flag){
            		$(this).find("a[href=#"+dataset.currentPageView+"]").tab("show") ;
            	}else{
            		dataset.currentPageView = href.substring(1,href.length)     ;     	
            	}
            	return flag ;            	
            }) ;
			
		},
		initPageView:function(pageViewhref){
			var flag = true ;			
				
			//alert(111)
			var jqtab = $(pageViewhref) ;
			var key = jqtab.attr("key") ;
			
			var id = jqtab.attr("id") ;

			var firstFlag = false ;


			//判断页是否是第一次点，如果是第一次点则需要做初始化动，如果不是，则是否加载数据
			if(key == undefined){
				if(this.getPageView(id) == undefined){
					firstFlag = true ;
				}
			}else{
				if(this.getPageView(id) == undefined){
					firstFlag = true ;
				}

				if(this.getPageView(key).grid.getCurrentRecord().length == 0){
					alert("请先选中记录") ;
					flag = false ;
					firstFlag = false ;
				}
			}


			if(firstFlag){					
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
	            pageView.init() ;
			}else{
				//查看是否需要加载数据
				if(key != undefined){
					this.getPageView(key).grid.load(key) ;
				}
			}

			return flag ;
		},
		addPageView:function(pageView){
			this.pageViews[this.pageViews.length] = pageView ;
		},
		getPageView:function(name){
			var pageView = undefined ;
			for(var i =0 ,max = this.pageViews.length ;i < max ;i++){
				if(this.pageViews[i].name == name){
					pageView = this.pageViews[i] ;
					break ;
				}
			}
			return pageView ;
		},
		exists:function(pageViewId){
			var isExists = false ;
			for(var i =0 ,max = this.pageViews.length ;i < max ;i++){
				if(this.pageViews[i].name == pageViewId){
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





