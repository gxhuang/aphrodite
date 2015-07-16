(function(){
    var Search = function(binding){
        this.method = binding.attr("id") ;
        this.binding = binding ;
        this._init();

    } ;
    Search.prototype = {
        _init:function(){
        },
        _initEvent:function(){
            var jqbtn = this.binding.next("div").find("button");
            $(jqbtn).on("click",function(e){

            }) ;
        },
        setData:function(records){
            if(records == undefined || records.length == 0){
                return ;
            }

            var selhtml = "" ;
           $.each(records,function(index,record){
                selhtml += "<li><a href=\"#"+record.code+"\">"+record.name+"<a></li>" ;
            });

            this.binding.next("div").find("ul").append(selhtml) ;
            this.binding.next("div").find("ul").find("li").find("a").on("click",function(e){
                var _jq = $(this) ;
                var input=_jq.parents("div.input-group-btn").prev("input[type=search]").getField() ;
                input.binding.val(_jq.text());
                input.binding.value=_jq.attr("code") ;
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

        }
    });
})();