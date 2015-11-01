//闭包是很重要的，能改成闭包吗？


//--------------------------dataset start-------------------------
(function(){
	var Dataset = function(){
		this.datasets = [] ;
		this.grids=[];
		this.forms= []
		this.activeGridBinding = undefined ;
	}

	Dataset.prototype={
		addGrid:function(grid){
			//return this.grids[name] ;
			this.grids[grid.id] = grid ;
		},
		addForm:function(form){
			this.forms[form.id] = form ;
		},
		addDataset:function(dataset){
			this.datasets[dataset.id]=dataset ;
		},
		getGrid:function(id){
			return this.grids[id] ;
		},
		getDataset:function(id){
			return this.datasets[id] ;
		},
		getForm:function(id){
			return this.forms[id] ;
		}
	}
});

//--------------------------dataset end----------------------------



//--------------------------grid start-------------------------
var Grid = function(id){
	this.id = id ;
	this.fields = [];
	this.records = [] ;
	this.binding = undefined ;
	this.pagenation = new Pagenation();
	this.selected = [];
	
}

Grid.prototype = {
	insert:function(record){
		this.records[this.records.length] = record ;
	},
	addField:function(field){
		this.fields[field.name] = field ;
	},
	getField:function(name){
		return this.fields[name];
	},
	selectAll:function(){
		for(var i=0,max=this.records.length;i<max;i++){
			this.selected[i] =i ;
		}
	},
	unSelectAll:function(){
		for(var i=0,max=this.records.length;i<max;i++){
			this.selected.splice(i,1) ; ;
		}
	},
	select:function(index){
		this.selected[this.selected.length]=index;
	},
	unselect:function(index){
		for(var i=0,len=this.selected.length;i<len;i++){
			if(this.selected[i] == index){
				this.selected.splice(i,1) ;
				break;
			}
		}
		
	},
	clean:function(){
		for(var key in this.fields){
			this.fields[key].value = undefined ;
		}
	},
	toJSON:function(){
		var obj = {};
		for(var key in this){
			if(typeof(this[key]) === "function" || key == "binding"){
				continue ;
			}else if(typeof(this[key]) === "object"){
				if(key === "fields" || key==="records"){
					var index = 0 ;
					var fields=new Array() ;
					for(var field in this[key]){
						fields[index++] = this[key][field] ;
					}
					obj.fields=fields ;
					continue ;
				}
				
			}

			obj[key] = this[key] ;
		}

		return obj ;
	}
}

//--------------------------grid end----------------------------

//--------------------------record start-------------------------
var Pagenation = function(){
	this.pageSize = 20 ;
	this.currentPage = 0 ;
	this.total = undefined ;
}

Pagenation.prototype={
	toJSON:function(){
		var obj = new Object();
		for(var key in this){
			if(key === "binding"){
				continue ;
			}
			obj[key] = this[key] ;
		}
		return obj ;
	}
}
//--------------------------record end----------------------------

//--------------------------record start-------------------------

//--------------------------record end----------------------------

//--------------------------form start-------------------------

//--------------------------form end----------------------------

//--------------------------field start-------------------------
var Field = function(){
	this.value = undefined  
}
Field.prototype={
	toJSON:function(){
		var obj = new Object();
		for(var key in this){
			if(key === "binding"){
				continue ;
			}
			obj[key] = this[key] ;
		}
		return obj ;
	}
}
//--------------------------field end----------------------------