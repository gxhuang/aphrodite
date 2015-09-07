(function(){
    var PageView = function(){
        this.fields[] = undefined ;
        this.form = undefined ;
        this.grid = grid ;
    } ;
    PageView.prototype = {
        setFields:function(fields){
            this.fields = fields ;
        },
        addField:function(field){
            if(this.fields[field.id] == undefined){
                this.field[field.id] = field ;
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
            $.each(this.fields,function(index,field){
                if(field.type == "search"){
                    var search = field.binding.getSearch() ;
                    if(search.getCode() == undefined ){
                        searchs[search.getKey()] = search.getKey();
                    }
                }
            }) ;
            return searchs ;
        }
    } ;

    $.fn.extend ({
        _pageView:function(){
            //$(document)
            var pageView = new PageView(this) ;
            this.data("pageView",search) ;
            return search ;
        },
        getPageView:function(){
            return this.data("pageView") ;
        }
    });
})();