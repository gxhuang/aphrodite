(function(){
    var PageView = function(binding,dataset){
        this.binding = binding ;
        this.fields = new Array() ;
        this.fieldIndexs = new Array();
        this.name = binding.attr("id")
        this.form = undefined ;
        this.grid = undefined ;
        this.dataset = dataset ;
    } ;
    PageView.prototype = {
        setFields:function(fields){
            this.fields = fields ;
        },
        getField:function(id){
            return this.fields[this.fieldIndexs[id]] ;
        },
        addField:function(field){
            if(this.fieldIndexs[field.id] == undefined){                
                this.fieldIndexs[field.id] = this.fields.length
                this.fields[this.fields.length] = field ;
            }
            
        },
        setForm:function(form){
            this.form = form ;
        },
        setGrid:function(grid){
            this.grid = grid ;
        },
        getFieldByKey:function(key){
            var search = undefined ;
            for(var index in this.fields){
                if(typeof(index) == "function"){
                    continue ;
                }
                var field = this.fields[index] ;
                if( field.type == "search"){
                    jqSearch = field.binding.getSearch();
                    if( jqSearch.key == key ){
                       search = jqSearch ;
                    }
                }

            }
            return search ;
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
        _pageView:function(dataset){
            //$(document)
            var pageView = new PageView(this,dataset) ;
            this.data("pageView",pageView) ;
            return pageView ;
        },
        getPageView:function(){
            return this.data("pageView") ;
        }
    });
})();