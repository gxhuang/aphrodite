(function(){
    var Search = function(binding){
        this.id = binding.attr("id") ;
        this.binding = binding ;
        this.value = binding.val();

    } ;
    Search.Prototype = {
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
            for(var record in records){
                seltml += "<li code="+record.code+">"+record.name+"</li>" ;
            }

            this.binding.next("div").find("ul").append(selhtml) ;
        }
    }


    $.fn.extend ({
        getSearch:function(){
            this.data(this.attr("id")) ;
        }
    });
})();