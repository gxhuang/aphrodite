(function(){
	//grid 与tree grid
	//如果是树结构，初始化数据时全都带有链接，如果点接连接查询无下级数据时，去除链接
	var Grid = function(binding,pageView){
		this.id = binding.attr("id") ;
		this.binding = binding ;
		this.tree= binding.attr("tree")
		this.status = undefined ;//grid的状态 浏览或者修改
		//this.fields = [] ;
		this.pageView = pageView ;
		this.editRecord = undefined ;
		this.records = new Array() ;
		
		//this.codevalue = new Object();

		//DELETE,INSERT,UPDATE,SELECT
		this.status = undefined ;
		this._init();
	};
	Grid.prototype = {
		_init : function() {
			// body...
			var jqtable = $(this.binding) ;
			var header = jqtable.find("thead") ;
			var ths = header.find("th") ;
			var pv = this.pageView
			$.each(ths,function(index,th){
				var jqField = $(this) ;
				var _field = pv.getField() ;
				if(_field == undefined){
					_field = jqField._field() ;
					pv.addField(_field) ;
				} else{
					_field.datatype = jqField.attr("datatype") ;
				}
				
			});

			//init head toolbar
			this._initHeadToolbar();

			//init foot toolbar
			this.initFootToolbar() ;
		},
		/****/
		_initHeadToolbar : function(){
			var _jqtoolbar = this.binding.find(".btn-group").find("button").on("click",function(e){
				var _jq = $(e.target);
				var _jqgrid = _jq.parents("[name=grid]") ;
				var op = _jq.attr("name") ;
				if(op == "new"){
					//_jqgrid.addClass("hide").prev("br").prev("form").removeClass("hide") ;
					// var jqSForm = _jqgrid.siblings("form[search]") ;
					// if(!jqSForm.hasClass("hide")) jqSForm.addClass("hide")
					_jqgrid.addClass("hide") ;
					_jqgrid.getGrid().status = "INSERT" ;
					_jq.parents(".tab-pane").find("form[neworedit]").removeClass("hide") ;
				}else if(op == "edit"){
					var active = _jqgrid.find("tbody").find("tr").filter(".active") ;
					if(active == undefined || active.length == 0){
						alert("please select!") ;
					}else{
						var obj = active.data("record") ;						
						//如果是grid的时候，search或者neworedit都是hide状态
						// var jqNeForm = _jqgrid.siblings("form[neworedit]") ;
						// if(!jqNeForm.hasClass("hide")) jqNeForm.addClass("hide")
						_jqgrid.addClass("hide").siblings("form[neworedit]").removeClass("hide") ;
						var form = _jqgrid.siblings("form[neworedit]").getForm().setData(obj) ;

					}
					_jqgrid.getGrid().status = "UPDATE" ;
				}else if(op == "submit"){					
					var dataset = _jqgrid.getGrid().pageView.dataset ;
					//提交
					var request = aphroditeSubmit(dataset) ;
					console.log(request)
				}else if(op == "search"){
					_jqgrid.addClass("hide") ;
					_jq.parents(".tab-pane").find("form[search]").removeClass("hide") ;
					var jqNeForm = _jq.siblings("form[neworedit]") ;
					if(!jqNeForm.hasClass("hide")) jqNeForm.addClass("hide") ;


					_jqgrid.getGrid().status = "SELECT" ;
					_jqgrid.getGrid().pageView.dataset.action = "select" ;
				}else{
					var dataset = _jqgrid.getGrid().pageView.dataset;
					var _func = _jq.attr("function") ;
					if(_func != undefined){
						_func(dataset) ;
						//_func实现的功能必须指定action与service操作都是有哪些？
						//根据service获取得到service bean
					}					
				}
			}) ;
		},
		_initThead : function(){

		},
		initFootToolbar:function(){
			var jqfoot = $(this.binding).find(".pager").find("li").find("a").on("click",function(e){
				alert("nl");
			}) ;
		},
		_initEvent:function(){

		},
		append:function(record){
			//追加在最后 last函数
			//添加监听事件

			var jqtbody = this.binding.find("tbody") ;
			//这个能保证顺序与table的一致吗
			var fields = this.pageView.fields ;

			//方便定位每一行，标记不同颜色或者或更新操作
			//新增的数据没有ID的
			function getStatus(status){
				var strClass = "" ;
				if(status == "INSERT") {

				}else if(status == "UPDATE") {

				}else if(status == "DELETE") {

				}
				return strClass ;
			}

			var trhtml = "<tr id="+record["id"]+getStatus(this.status)+">" ;
			var value = undefined ;
			var allUndefined = true ;
			for(var index = 0,len = fields.length ; index < len ;index++){
				value = record[fields[index].name] ;

				if(value != undefined && value != ""){
					allUndefined = false ;
				}

				//code-value转换规则
				if(fields[index] == "search"){

				}
				if(fields[index].isHide){
					trhtml += "<td class=\"hide\">"+(value == undefined ?"":value)+"</td>" ;
				}else{
					trhtml += "<td>"+(value == undefined ?"":value)+"</td>" ;
				}
				
				value = undefined ;

			}

			if(!allUndefined){
				//新增的时候如果所有控件都没有值 ，则无需要新增一行
				trhtml += "</tr>" ;
				var jqtr = this.binding.find("tbody").append(trhtml).find("tr").last() ;
				jqtr.on("click",function(){
					var _jqthis = $(this) ;
					_jqthis.parent("tbody").find("tr[class=active]").removeClass("active") ;
					_jqthis.addClass("active") ;
				}) ;
				

				var r = new Object() ;
				r.recordVal = record ;
				r.status = this.status ;
				this.records[this.records.length] = r ;
				jqtr.data("record",r) ;
				this.status = undefined ;
			}
			

		},
		insert:function(records){
			if(records == undefined || records.length <= 0){
				return ;
			}

			var fields = this.pageView.fields ;
			var _this = this ;

			var dataset = new Object();
			dataset.action = "select" ;
			dataset.service = "jdbcService" ;	
			dataset.pageViews = new Array();
			for(var pos =0 ,limit = fields.length ;pos < limit ;pos++){


				if(fields[pos].type == "search"){
					// 
					var conditionVal = "" ;
					var pfield = fields[pos] ;
					
					// var key = jqsearch.key ;
					for(var index = 0 ,size = records.length ;index < size ;index ++){
						var pVal = records[index].recordVal[pfield.name] 
						if(pVal != undefined && pVal != "" && !conditionVal.contains(pVal)){
							if(pfield.datatype == "string"){
								conditionVal += "'"+pVal+"'" ;
							}
							if(index != size -1){
								conditionVal += "," ;
							}			
						}				
					}
					conditionVal = conditionVal.substring(1,conditionVal.length-2) ;
					pfield.op = "IN" ;

					var jqsearch = pfield.binding.getSearch() ;
					//var obj = new Object() ;
					//obj[jqsearch.conditionName] = conditionVal ;

					var arrfields = new Array();
					arrfields[0] = this.pageView.getField(jqsearch.key) ;
					arrfields[1] = this.pageView.getField(jqsearch.conditionName) ;
					arrfields[1].value = conditionVal ;
					
					var pageView = toPageView(arrfields,jqsearch.tableName)
					dataset.pageViews[dataset.pageViews.length] = pageView ;
				}
			}			

			aphroditeSelect(dataset,callback,this.pageView.grid.binding);	

			function callback(data,jq) {
				//后续data的结构要改成与codevalue结构一致	
				alert(data) ;	
				var dataset = JSON.parse(data) ;				

				//下面这段代码可以改调用append就可以了
				//先清空再调用append
				//
				for(var i = 0 ,max = records.length ;i < max ;i++){
					jq.getGrid().append(records[i].recordVal) ;
				}
					
			}					
		},
		update:function(record){
			//找到相应的记录，更新掉记录的值
			var tr = this.binding.find("tbody").find("tr.active");
			tr.empty() ;
			var fields = this.pageView.fields ;
			var tdhtml = "" ;
			var value = undefined ;
			var allUndefined = true ;
			for(var index = 0,len = fields.length ; index < len ;index++){
				value = record[fields[index].name] ;

				if(value != undefined && value != ""){
					allUndefined = false ;
				}

				//code-value转换规则
				if(fields[index] == "search"){

				}
				if(fields[index].isHide){
					tdhtml += "<td class=\"hide\">"+(value == undefined ?"":value)+"</td>" ;
				}else{
					tdhtml += "<td>"+(value == undefined ?"":value)+"</td>" ;
				}
				
				value = undefined ;

			}
			tr.append(tdhtml).on("click",function(){

				var _jqthis = $(this) ;
				_jqthis.parent("tbody").find("tr[class=active]").removeClass("active") ;
				_jqthis.addClass("active") ;				
			}) ;

			var oldrecord = tr.data("record") ;
			oldrecord.recordVal = record ;
			oldrecord.status = this.status ;
			this.status = undefined ;
		}
	};

	$.fn.extend ({
		_grid:function(pageView){
			var _grid = new Grid(this,pageView) ;
			this.data("aphrodite.grid",_grid) ;
			return _grid ;
		},
		getGrid:function(){
			return 	this.data("aphrodite.grid") ;
		}
	});
})();