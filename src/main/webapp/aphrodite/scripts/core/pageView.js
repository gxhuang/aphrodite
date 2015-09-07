(function(){
    var PageView = function(binding){
        this.binding = binding ;
        this.fields = new Array() ;
        this.form = undefined ;
        this.grid = undefined ;
    } ;
    PageView.prototype = {
        setFields:function(fields){
            this.fields = fields ;
        },
        getField:function(field){
        },
        addField:function(field){
            if(this.fields[field.id] == undefined){
                this.fields[field.id] = field ;
            }
        },
        setForm:function(form){
            this.form = form ;
        },
        setGrid:function(grid){
            this.grid = grid ;
        },
        getSearchKeys:function(){
            //获取静态的search控件,区别码
            var searchs = new Array();

            var jqSearch = undefined ;
            for(var key in this.fields){
                if(typeof(key) == "function"){
                    continue ;
                }
                var field = this.fields[key] ;
                if( field.type == "search"){
                    jqSearch = field.binding.getSearch();
                    if( jqSearch.code == undefined ){
                        searchs.push(jqSearch.getKey());
                    }
                }

            }
            return searchs ;
        }
    } ;

    $.fn.extend ({
        _pageView:function(){
            //$(document)
            var pageView = new PageView(this) ;
            this.data("pageView",pageView) ;
            return pageView ;
        },
        getPageView:function(){
            return this.data("pageView") ;
        }
    });
})();