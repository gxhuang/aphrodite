(function(){
	//数据获取方式分为ajax和非ajax
    //如果是取表数据，全通过ajax方式获取
    var Search = function(binding){
        this.binding = binding ;
        // this.ajax = binding.attr("ajax")
        //存储数据
        this.records = undefined ;
        //search的tableName如果不为空，则从表里查，如果为空，则从数据字典查,正常情况下一般都是从数据字典查
        this.tableName = this.binding.attr("tableName") ;
        this.grid = (binding.attr("grid") != undefined? true:false) ;
        //key取对的
        this.key = binding.attr("key") ;
        //根据code取标准代码
        this.code = binding.attr("code") ;
        this.conditionName = binding.attr("cname")
        this.condition = undefined ;
        this.fields = new Array();
        this._init();

    } ;
    Search.prototype = {
        _init:function(){
            if(this.grid ){
                var jqfields = this.binding.next(".input-group-btn").find("ul").find("th") ;
                this.tablename = this.binding.next(".input-group-btn").find("ul").find("table").attr("tablename") ;
                var _fields = this.fields  ;
                $.each(jqfields,function(index,jqfield){
                    var _this = $(jqfield) ;
                    var field = _this.getField();
                    if(field == undefined) field = _this._field() ;
                    //是否可以直接这样子使用
                    _fields[_fields.length] = field ;
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

                    var _jq = $(this) ;
                    var search = _jq.getSearch();
                    search.condition = _jq.val() ;
                    if( search.grid ){
                        function searchFilter(key,value){
                            if(key == "binding"){
                                return undefined
                            }
                            return value ;
                        }

                        function searchCallback(search,data){
                            var obj = JSON.parse(data) ;
                            var records = obj.records ;
                            search.setData(records) ;
                        }

                        //前台组成一个dataset

                        var searchJson = JSON.stringify(_jq.getSearch(),searchFilter) ;
                        var result = aphroditeSelect("searchControlServlet",searchJson,searchCallback,search) ;
                        //console.log(JSON.stringify(result)) ;
                        // _jq.getSearch.setGridData(data.grid) ;
                    }else{
                        _jq.getSearch().filter(_jq.val()) ;
                    }                  
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
        setData:function(records){
//            this.records = records ;
            if(records == undefined || records.length == 0){
                return ;
            }

            if(this.grid){
                var datahtml = "" ;
                for(var i = 0 ,max = records.length ;i<max ;i++){
                    datahtml += "<tr>" ;
                    for(var j = 0 ,len = this.fields.length ;j<len ;j++){
                        if(!this.fields[j].isHide){
                            datahtml += "<td>"+records[i].recordVal[this.fields[j].name]+"</td>" ;
                        }                        
                    }
                    datahtml += "</tr>" ;
                }
                var jqtbody = this.binding.next("div").find("tbody").empty() ;
                jqtbody.append(datahtml) ;
                jqtbody.find("tr").on("click",function(e){
                    alert("click") ;
                }) ; 

            }else {
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

            this.binding.getSearch().binding.next("div").find("ul").dropdown("toggle")            
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