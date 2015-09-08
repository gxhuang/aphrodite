(function(){
    var Search = function(binding){
        this.binding = binding ;
        this.records = undefined ;
        //key取对的
        this.key = binding.attr("key") ;
        //根据code取标准代码
        this.code = binding.attr("code") ;
        this._init();

    } ;
    Search.prototype = {
        _init:function(){
            this._initEvent();
        },
        getKey:function(){
            return this.key ;
        },
        getCode:function(){
            return this.code ;
        },
        _initEvent:function(){
            /**
            this.binding.on("keyup",function(e){

                //alert($(this).val())
                var _jq = $(this) ;
                _jq.getSearch().filter(_jq.val()) ;

               _jq.getSearch().binding.next("div").find("ul").dropdown("toggle")
            }) ;**/
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