(function(){
	//数据获取方式分为ajax和非ajax
    //如果是取表数据，全通过ajax方式获取
    var Search = function(binding){
        this.binding = binding ;
        // this.ajax = binding.attr("ajax")
        //存储数据
        this.records = undefined ;
        this.tableName = this.binding.attr("tableName") ;
        this.grid = binding.attr("grid") ;
        //key取对的
        this.key = binding.attr("key") ;
        //根据code取标准代码
        this.code = binding.attr("code") ;
        this.fields = new Array();
        this._init();

    } ;
    Search.prototype = {
        _init:function(){
            if(this.grid ){
                var jqfields = this.binding.next(".input-group-btn").find("ul").find("th") ;
                this.tablename = this.binding.next(".input-group-btn").find("ul").find("table").attr("tablename") ;
                $.each(jqfields,function(index,jqfield){
                    var field = $(jqfield).getField();
                    //是否可以直接这样子使用
                    this.fields[field.name] = field ;
                }) ;
            }

            this._initEvent();
        },
        getKey:function(){
            return this.key ;
        },
        getCode:function(){
            return this.code ;
        },
        _initEvent:function(){
            
            this.binding.on("keyup",function(e){

                //回车时候触发查询
                if(e.which == 13) {
                    alert(1111) ;
                    var _jq = $(this) ;
                    var field = _jq.getField();
                    if(field.grid){
                        function searchFilter(key,value){
                            if(key == "binding"){
                                return undefined
                            }
                            return value ;
                        }
                        var search = JSON.stringify(_jq.getSearch(),searchFilter) ;
                        var result = ajax("/searchControlServlet",search) ;
                        console.log(JSON.stringify(result)) ;
                        // _jq.getSearch.setGridData(data.grid) ;
                    }else{
                        _jq.getSearch().filter(_jq.val()) ;
                    }
                    

                   _jq.getSearch().binding.next("div").find("ul").dropdown("toggle")
                }

                //alert($(this).val())
                
            }) ;
        },
        filter:function(value){
              this.binding.next("div").find("ul").find("li").filter(function(index){
                var jq = $(this) ;
                if(jq.find("a").text() != value){
                    jq.addClass("hide") ;
                }
              }) ;

//            this.setData(arr) ;
        },
        setGridData:function(jqGrid,records){
            if(records == undefined || records.length == 0){
                return ;
            }

            jqGrid.find("th")

        },
        setData:function(records){
//            this.records = records ;
            if(records == undefined || records.length == 0){
                return ;
            }

            var selhtml = "" ;
            for(var code in records){

                selhtml += "<li><a href=\"#"+code+"\">"+records[code]+"<a></li>" ;
            }


            this.binding.next("div").find("ul").empty().append(selhtml) ;
            this.binding.next("div").find("ul").find("li").find("a").on("click",function(e){
                var _jq = $(this) ;
                var input=_jq.parents("div.input-group-btn").prev("input[type=search]").getField() ;
                //alert(_jq.attr("href"))
                input.binding.val(_jq.text());
                input.value=_jq.attr("href") ;
            }) ;
        }
    };


    $.fn.extend ({
        _search:function(){
            var search = new Search(this);
            this.data("search",search) ;
            return search ;
        },
        getSearch:function(){
            return this.data("search") ;
        }
    });
})();