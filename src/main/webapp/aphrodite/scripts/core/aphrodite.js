(function(){
	
})();

var dataset= new Dataset();

//control init start
//todo
//control init end

var jqdatasets = $("[dataset]") ;
jqdatasets.each(function(index){
	var jq = $(this) ;
	var type = dataset.attr("type") ;
	if("grid" == type){
		//var grid = new Grid(jq.attr("id"));
		init(jq) ;
	}else if("dataset" == type){
		var nav = jq.find(".nav .nav-tabs") ;
		var href = nav.find("li .active").find("a").attr("href") ;
		var jqnav = $("#"+href) ;
		init(jqnav);

		//
		nav.find("a").click(function (e) {
			e.preventDefault()
			var _this = $(this) ;
			_this.tab('show') ;
			var _href = _this.attr("href");
			var _jqnav = $("#"+_href) ;
			init(_jqnav) ;
		})
	}else{

	}
});

var jqtabs = $("[dataset='dataset']") ;

$(".dropdown-toggle").html("请选择<span class=\"caret\"></span>") ;
$('.form_date').datetimepicker({
	language : 'zh-CN',
	format : "yyyy-mm-dd",
	autoclose : true,
	todayBtn : true,
	todayHighlight : true,
	minView : 2,
	pickerPosition : "bottom-left"
});

/**
 根据ID得到绑定控件  递归
**/
function getBinding(obj,id){
	var jq = undefined ;
	//hasProperty
	for(var p in obj){
		if(typeof(obj[p]) == 'function'){
			continue ;
		}else{
			if(obj[p].hasOwnProperty("id") && obj[p].id = id ){
				jq = obj[p].binding ;
			}else if(typeof(obj[p]) == "dataset" || typeof(obj[p]) == "grid"){
				jq = getBinding(obj[p],id) ;
			}
		}
	}

}

function init(jq){
	var dsattr = jq.attr("dataset") ;
	var jqid = jq.attr("id") ;
	var type = jq.attr("type") ;
	if(dsattr == undefined){
		var jqds = jq.closet("[dataset]") ;
		var  dsbinding = dataset.getDataset(jqds.attr("id")) ;
		//如果dataset==undefined,需要先定义dataset
		if(dsbinding == undefined){
			var _dataset = new Dataset(jqds.attr("id")) ;
			dataset.addDataset(_dataset) ;
		}

		if(type == "form"){
			var form = new Form(jqid) ;
			dataset.getDataset(jqds.attr("id")).addForm(form) ;
		}else if(type == "grid"){
			var grid = new Grid(jqid) ;
			dataset.getDataset(jqds.attr("id")).addGrid(grid) ;
		}

	}else{
		var grid = new Grid(jqid) ;
		dataset.addGrid(grid) ;
	}
  	//如果没绑定dataset，应该先绑定dataset
  	
	
	if("grid" == type){
		gridInit(jq);
	}else if("form" == type){
		formInit(jq) ;
	}
	return dataset ;
}

function gridInit(grid){
	var toolbar = grid.find("[name='toolbar']") ;
	toolbarInit(toolbar);
	var table = grid.find("[name='table']");
	tableInit(table) ;
	var pagenation = grid.find("[name='pagenation']");
	pagenationInit(pagenation)  ;

	//var collapse = grid.find("div.panel-collapse") ;
	//collapseInit(collapse) ;
}

function collapseInit(collapse){
	var form = collapse.find("form");
	var controls = form.find(".form-control");
	$.each(controls,function(index,control){
		$(control).on("change",function(e){
			var jqtmp = $(this) ;
			// alert(jqtmp.val());
			var objgrid = dataset.getGrid(jqtmp.closest("[dataset='grid']").attr("name"));
			objgrid.getField(jqtmp.attr("name")).value = jqtmp.val();
		}) ;
	});

	var buttons = form.find("button.btn") ;
	$.each(buttons,function(index,button){		
		$(button).on("click",function(e){
			var jqbtn =  $(this) ;
			var name = jqbtn.attr("name") ;
			if("cancle" == name){
				jqbtn.closest("div.panel-collapse").removeClass("in");
			}else{
				var objgrid = dataset.getGrid(jqbtn.closest("[dataset='grid']").attr("name"));
				objgrid.records = undefined ;
				var json = aphroditeStringify(objgrid) ;
				// alert(json) ;
				aphroditeAjax("data.json","GET",json,function(data){
					var jqgrid = objgrid.binding ;
					var jqtbody = jqgrid.find("[name=table]").find("tbody") ;
					jqtbody.empty();
					objgrid.records=data ;
					var tbodyHtml = getTbodyHtml(data) ;
					jqtbody.append(tbodyHtml);
					jqtbody.find("input[type=checkbox]").on("click",function(e){
						var tr = $(this).parent().parent() ;
						var tmpgrid = dataset.getGrid(jqbtn.closest("[dataset='grid']").attr("name"));

						//alert(tr.attr("index")) ;
						var check = this.checked ;
						if(check){
							tr.addClass("active");
							tmpgrid.select(tr.attr("index")) ;
						}else{
							tr.removeClass("active");
							tmpgrid.unselect(tr.attr("index")) ;
						}
						
					}) ;

				}) ;
				jqbtn.closest("div.panel-collapse").removeClass("in");
			}

		});
	});

}

function getTbodyHtml(data){
	var tbodyHtml = "" ;
	var record = undefined ;
	// var records = [] ;
	var tr = ""
	for(var i=0,len= data.length;i<len;i++){
		tr +="<tr index=\""+i+"\"><td><input type=\"checkbox\"></td>" ;
		// tr+="<td>"+(i+1)+"</td>" ;
		for(var field in data[i]){
			if(field == undefined){
				continue ;
			}
			tr+="<td>"+data[i][field]+"</td>";
		}
		tr+="</tr>" ;
		tbodyHtml+=tr ;
		tr="";
	}

	return tbodyHtml ;
}

function tableInit(table){
	var ths = table.find("thead").find("th") ;
	var fields = []
	//var datatype = undefined ;
	var grid = dataset.getGrid(table.closest("[dataset='grid']").attr("name")) ;
	$.each(ths,function(index,th){
		var jqth = $(th) ;
		var field = new Field();
		var name = jqth.attr("name") ;
		if(name == undefined){
			jqth.find("input[type=checkbox]").on("click",function(){
				var check = $(this).prop("checked") ;
				// alert() ;
				var tmpgrid = dataset.getGrid(table.closest("[dataset='grid']").attr("name")) ;
				var trs = tmpgrid.binding.find("tbody tr") ;
				$.each(trs,function(index,tr){
					$(tr).find("td").find("input[type=checkbox]").prop("checked",check);
					if(check){
						tmpgrid.selectAll();
					}else{
						tmpgrid.unSelectAll();
					}
				});
			});
			return ;
		}
		field.binding = jqth ;
		field.name = name ;
		field.datatype = jqth.attr("datatype");
		if("date" == field.datatype){
			field.format = jqth.attr("format");
		}else if("" == field.datatype){

		}
		//field.type=th.attr()
		grid.addField(field) ;
	});
	
}

function toolbarInit(toolbar){
	var buttons = toolbar.find(".btn-group").find(".btn") ;
	$.each(buttons,function(index,button){
		$(button).on("click",function(e){
			var jqbtn = $(this) ;
			var attr = jqbtn.attr("name") ;
			var datarget = jqbtn.attr("data-target") ;
			if("new" == attr){
				//hide
				var collapses = $(".container-fluid collapse") ;
				$.each(collapses,function(index,collapse){
					if($(collpase).hasClass("in") &&)
				});
				//show

			}else if("edit" == attr){
				//hide

				//show

			}else if("delete" == attr){

				//message alter are you sure delete

			}else if("search" == attr){
				var collapse = $(".panel-collapse.collapse");
				if(collapse.hasClass("in")){
					collapse.removeClass("in");
				}else{
					collapse.addClass("in");
				}
			}else{
				var action = jqbtn.attr("action") ;
				action(jqbtn) ;
			}
		});
	});

	//search
	var dropdown = toolbar.find(".input-group") ;

	//if not found drop down ;
	dropdown.find("input.form-control").on("change",function(e){
		var input = $(this) ;
		var fieldName = input.attr("field") ;
		var value = input.val();
		//alert(value)
	}) ;

	dropdown.find("span.input-group-btn").find("button").on("click",function(e){
		
	});

	var lis = toolbar.find(".dropdown-menu").find("li");
	$.each(lis,function(index,li){
		$(li).find("a").on("click",function(e){
			var href = $(this) ;
			var parent = href.parent().parent().siblings("button.dropdown-toggle");
			var data = parent.data("data") ;
			var oldKey = (data==undefined?undefined:data.key) ;
			var oldValue = (data==undefined?undefined:data.value) ;
			var text = href.text();
			var newKey = href.attr("name") ;
			parent.html(text+"<span class=\"caret\"></span>") ;
			var obj = {};
			obj.key=newKey ;
			parent.data("data",obj);

			var input = href.parent("div.input-group").find("input.form-control") ;
			input.val("") ;
			input.attr("field",newKey) ;
			//href.getParent()
		});
	});

}

function pagenationInit(pagenation){
	var span = pagenation.find("span").data();

	var lis = pagenation.find("li")
	$.each(lis,function(li){
		$(li).find("a").on("click",function(e){
			var href = $(this) ;
			var name = href.attr("name") ;
			if("preview" == name){

			}else if("next" == name){

			}else {
				//提示不按规矩出牌
			}
		});
	}) ;
}

function formInit(form){
	var controls = form.find(".form-control") ;
	controls.each(function(index){
		var control = $(this) ;
		var type = control.attr("type") ;
		var field = new Field(control.attr("id"));
		field.binding = control ;

		if("search" == type){
			control.closest(".input-group").find(".input-group-btn").find("button").click(function(e){
				var _id = $(this).closest(".input-group").find(input).attr("id") ;
			});

		}else if("date" == type){
			field.format = control.attr("") ;
		}

		control.on("change",function(e)){
			var _this = $(this) ;
			var _type = _this.closest("[dataset]").attr("type") ;
			var _value = _this.val();
			//find the field and set value
		}
	});
}


function aphroditeAjax(url,method,param,callback){
	// $.ajax({
	// 	url:url,
	// 	type:method,
	// 	data:param,
	// 	error:function(){
	// 		alert(1234);
	// 	},
	// 	success:callback
	// }).done(function(){

	// }) ;
	// var str = "[";

	
	$.getJSON("data.json",callback) ;
	
}


function aphroditeStringify(obj){
	var result = JSON.stringify(obj);
	return result ;
}