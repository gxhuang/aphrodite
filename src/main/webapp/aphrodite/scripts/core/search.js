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
            var jqbtn = this.binding.find(".input-group-btn").find("button");
            var jqinput = this.finding.find("input") ;

            $(jqinput).on("change",function(e){

            }) ;
            $(jqbtn).on("click",function(e){

            }) ;
        },
        setData:function(json){

        }
    }


    $.fn.extend ({
        getSearch:function(){
            this.data(this.attr("id")) ;
        }
    });
})();