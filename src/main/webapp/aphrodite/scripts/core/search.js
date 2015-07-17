(function(){
    var Search = function(binding){
        this.method = binding.attr("id") ;
        this.binding = binding ;
        this.records = undefined ;
        this._init();

    } ;
    Search.prototype = {
        _init:function(){
        this._initEvent();
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
//            $.each(this.records,function(index, record){
//                if(value == record.name){
//                    arr[arr.length] = record ;
//                }
//            }) ;
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
            $.each(records,function(index,record){
                selhtml += "<li><a href=\"#"+record.code+"\">"+record.name+"<a></li>" ;
            });

            this.binding.next("div").find("ul").empty().append(selhtml) ;
            this.binding.next("div").find("ul").find("li").find("a").on("click",function(e){
                var _jq = $(this) ;
                var input=_jq.parents("div.input-group-btn").prev("input[type=search]").getField() ;
                //alert(_jq.attr("href"))
                input.binding.val(_jq.text());
                input.binding.value=_jq.attr("href") ;
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