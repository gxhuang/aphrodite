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
					dataset.action = "update" ;
					dataset.service = "jdbcService" ;

					var pageViews = dataset.pageViews
					for(var index in pageViews){
						var records = pageViews[index].grid.records ;
						var newrecords = new Array();
						for(var i = 0,len = records.length ;i < len ;i++){
							if(records[i].status != undefined && records[i].status != "" ){
								newrecords[newrecords.length] = records[i]
							}
						}
						pageViews[index].grid.records = newrecords ;
					}

					var request = aphroditeSubmit(dataset) ;
					console.log(request)
				}else if(op == "delete"){
					var tr = _jqgrid.find("tbody").find("tr[class=active]") ;
					if(tr == undefined || tr.length == 0){
						alert("please select!") ;
					}else{

						var record = tr.data("record") ;
						record.status = "DELETE" ;
						tr.removeClass().addClass("danger") ;
						//如果是新增记录最终提交的时候，无需提交到后台
					}
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
		getStatus:function (status){
			//方便定位每一行，标记不同颜色或者或更新操作
			//新增的数据没有ID的
			var strClass = "" ;
			if(status == "INSERT") {
				strClass="success"
			}else if(status == "UPDATE") {
				strClass="warning"
			}else if(status == "DELETE") {
				strClass="danger"
			}
			return strClass ;
		},
		append:function(record){
			//判断是否是空记录
			var allUndefined = true ;
			for(var p in record){
				if(p != undefined && p != ""){
					allUndefined = false ;
					break ;
				}
			}
			if(allUndefined) return ;

			var jqtbody = this.binding.find("tbody") ;
			//这个能保证顺序与table的一致吗
			var fields = this.pageView.fields ;		
			var trhtml = "<tr>" ;
			trhtml += this.tdhtml(record) ;
			trhtml += "</tr>" ;

			
			//新增的时候如果所有控件都没有值 ，则无需要新增一行
			
			var jqtr = this.binding.find("tbody").append(trhtml).find("tr").last();
			jqtr.on("click",function(){
				var _jqthis = $(this) ;
				_jqthis.parent("tbody").find("tr[class=active]").removeClass("active") ;
				_jqthis.addClass("active") ;
			}) ;
			jqtr.addClass(this.getStatus(this.status)) ;

			var r = new Object() ;
			r.recordVal = record ;
			r.status = this.status ;
			this.records[this.records.length] = r ;
			jqtr.data("record",r) ;				
			this.status = undefined ;		

		},
		insert:function(records){
			if(records == undefined || records.length <= 0){
				return ;
			}
			for(var i=0,max =records.length ;i<max ;i++){
				this.append(records[i].recordVal) ;			
			}
			
		},
		update:function(record){
			//找到相应的记录，更新掉记录的值
			var tr = this.binding.find("tbody").find("tr.active");
			tr.empty() ;
			var tdhtml = this.tdhtml(record);			
			tr.append(tdhtml) ;


			var oldrecord = tr.data("record") ;
			for(var prop in oldrecord.recordVal){
				if(record[prop] != undefined && record[prop] != ""){
					oldrecord.recordVal[prop] = record[prop] ;
				}
			}
			//新增的数据是否发生变更都是新增		
			if(oldrecord.status != "INSERT"){
				tr.removeClass().addClass(this.getStatus(this.status)) ;	
				oldrecord.status = this.status ;
			}
			
			this.status = undefined ;
		},
		tdhtml:function(record){
			var tdhtml = "" ;
			var value = undefined ;
			var fields = this.pageView.fields ;
			for(var index = 0,len = fields.length ; index < len ;index++){
				value = record[fields[index].name] ;
				//code-value转换规则
				if(fields[index].type == "search" && value != undefined && value != ""){
					value = this.pageView.codeValue[this.pageView.forms[0].fieldbindings[fields[index].name].getSearch().key][value] ;
				}
				if(fields[index].isHide){
					tdhtml += "<td class=\"hide\">"+(value == undefined ?"":value)+"</td>" ;
				}else{
					tdhtml += "<td>"+(value == undefined ?"":value)+"</td>" ;
				}
				
				value = undefined ;

			}
			return tdhtml ;
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