(function(){
    var PageView = function(binding,dataset){
        this.binding = binding ;
        //form标签中含有的field字段，全都包含于grid里的field，只需要将form中的field的多余的属性补充到grid中对应的field即可。
        //这样可以避免重复定义
        //如果grid隐藏的信息需要在form表示时如何表示,因为grid读到的field是hide状态，而form中则是非hide状态
        this.fields = new Array() ;
        this.fieldIndexs = new Array();
        this.name = binding.attr("id")
        this.forms = new Array() ;
        this.grid = undefined ;
        //使用规则codevalue->codevalue object -> code table当中不一定在form中出现
        this.codeValue = new Object();
        this.dataset = dataset ;
        //this._init() ;
    } ;
    PageView.prototype = {
        init:function(){
           //alert(JSON.stringify(this.codeValue)) ;
           var keys = new Array() ;
           for(var i = 0,max = this.forms.length ;i<max ;i++){
                var formbindings = this.forms[i].fieldbindings ;
                for(var j in formbindings){
                    if(formbindings[j].attr("type") == "search" && (formbindings[j].attr("tableName") == undefined || formbindings[j].attr("tableName")=="")){
                        var value = this.codeValue[formbindings[j].attr("key")] ;
                        if(value == undefined){
                            this.codeValue[formbindings[j].attr("key")] = "" ;
                            keys[keys.length] = formbindings[j].attr("key") ;
                        }
                    }
                }
           }
           function callback(data,pageView){
                var obj = JSON.parse(data) ;
                for(var prop in obj){
                    pageView.codeValue[prop] = obj[prop] ;
                }
                for(var i = 0,max = pageView.forms.length ;i<max ;i++){
                var formbindings = pageView.forms[i].fieldbindings ;
                for(var j in formbindings){
                    if(formbindings[j].attr("type") == "search" && (formbindings[j].attr("tableName") == undefined || formbindings[j].attr("tableName")=="")){
                        formbindings[j].getSearch().setData(obj[formbindings[j].attr("key")]) ;
                    }
                }
           }
           }
           getData(keys,callback,this) ;

        },
        setFields:function(fields){
            this.fields = fields ;
        },
        getField:function(name){
            return this.fields[this.fieldIndexs[name]] ;
        },
        addField:function(field){
            if(this.fieldIndexs[field.name] == undefined){                
                this.fieldIndexs[field.name] = this.fields.length
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
            //»ñÈ¡¾²Ì¬µÄsearch¿Ø¼þ,Çø±ðÂë
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