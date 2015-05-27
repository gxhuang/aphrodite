<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/pages/common/header.jsp"%><!--引入权限系统该页面需无须引用header.jsp -->
<%@ page import="java.util.Date"%>
<% String formId = "form_" + new Date().getTime();
   String gridId = "grid_" + new Date().getTime();
   String path = request.getContextPath()+request.getServletPath().substring(0,request.getServletPath().lastIndexOf("/")+1);
%>
<script type="text/javascript">
var grid;
var form;
var _dialog;

var customers = new Array();
var customerMap = new Array();
$(function (){
	$.ajaxSetup({cache:false});
    grid = $("#<%=gridId%>");
    form = $("#<%=formId%>");
    
    
   
    
	PageLoader = {
	   //
	    initSearchPanel:function(){
	    	
	    		 //var contents = [{title:'请选择', value: ''}];
                  form.find('#custId_SELECT').select({
		                title: '请选择',
		                contents: customers
		           }).on('change',function(){
		               form.find('#custIdID_').val($(this).getValue());
		           });
	    		//$("#custId_SELECT").select({title:'选择客户',contents:contents});
	    		
	        	            	                	                    
	                	            	        	     },
	    initGridPanel: function(){
	         var self = this;
	         var width = 180;
	         return grid.grid({
	                identity:"id",
	                buttons: [
	                        {content: '<button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-plus"><span>添加</button>', action: 'add'},
	                        {content: '<button class="btn btn-success" type="button"><span class="glyphicon glyphicon-edit"><span>修改</button>', action: 'modify'},
	                        {content: '<button class="btn btn-danger" type="button"><span class="glyphicon glyphicon-remove"><span>删除</button>', action: 'delete'}
	                    ],
	                url:"${pageContext.request.contextPath}/Acct/pageJson.koala",
	                columns: [
	                     	                         	                         { title: '客户名称', name: 'custId', width: width,render:function(item,name,index){
	                     	                         	                        	 return customerMap[item[name]];
	                     	                         	                         }},
	                         	                         	                         	                         { title: '总金额', name: 'total', width: width},
	                         	                         	                         	                         
	                         	                         	                         	                         { title: '收款金额', name: 'recAmount', width: width},
	                         	                         	                         	                         { title: '欠款金额', name: 'oweAmount', width: width},
	                         	                         	                         	                      	 { title: '开票金额', name: 'invoiceAmount', width: width},
	                         	                         	                         	                         { title: '欠票金额', name: 'oweInvoiceAmount', width: width},
	                         	                         	                             { title: '操作', width: 120, render: function (rowdata, name, index)
	                                 {
	                                     var param = '"' + rowdata.id + '"';
	                                     var h = "<a href='javascript:openDetailsPage(" + param + ")'>查看</a> ";
	                                     return h;
	                                 }
	                             }
	                ]
	         }).on({
	                   'add': function(){
	                       self.add($(this));
	                   },
	                   'modify': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                type: 'warning',
	                                content: '请选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                        if(indexs.length > 1){
	                            $this.message({
	                                type: 'warning',
	                                content: '只能选择一条记录进行修改'
	                            })
	                            return;
	                        }
	                       self.modify(indexs[0], $this);
	                    },
	                   'delete': function(event, data){
	                        var indexs = data.data;
	                        var $this = $(this);
	                        if(indexs.length == 0){
	                            $this.message({
	                                   type: 'warning',
	                                   content: '请选择要删除的记录'
	                            });
	                            return;
	                        }
	                        var remove = function(){
	                            self.remove(indexs, $this);
	                        };
	                        $this.confirm({
	                            content: '确定要删除所选记录吗?',
	                            callBack: remove
	                        });
	                   }
	         });
	    },
	    add: function(grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog">'
	        	+'<div class="modal-content"><div class="modal-header"><button type="button" class="close" '
	        	+'data-dismiss="modal" aria-hidden="true">&times;</button>'
	        	+'<h4 class="modal-title">新增</h4></div><div class="modal-body">'
	        	+'<p>One fine body&hellip;</p></div><div class="modal-footer">'
	        	+'<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
	        	+'<button type="button" class="btn btn-success" id="save">保存</button></div></div>'
	        	+'</div></div>');
	        $.get('<%=path%>/Acct-add.jsp').done(function(html){
	        	dialog.modal({
	                keyboard:false
	            }).on({
	                'hidden.bs.modal': function(){
	                    $(this).remove();
	                }
	            }).find('.modal-body').html(html);
	        	selectItems["custIdID"] = customers ;
	            self.initPage(dialog.find('form'));
	        	
	            
	        });
	        dialog.find('#save').on('click',{grid: grid}, function(e){
	              if(!Validator.Validate(dialog.find('form')[0],3))return;
	              $.post('${pageContext.request.contextPath}/Acct/add.koala', dialog.find('form').serialize()).done(function(result){
	                   if(result.result == 'success'){
	                        dialog.modal('hide');
	                        e.data.grid.data('koala.grid').refresh();
	                        e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                         });
	                    }else{
	                        dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                        });
	                     }
	              });
	        });
	    },
	    modify: function(id, grid){
	        var self = this;
	        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">修改</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-default" data-dismiss="modal">取消</button><button type="button" class="btn btn-success" id="save">保存</button></div></div></div></div>');
	        $.get('<%=path%>/Acct-update.jsp').done(function(html){
	               dialog.find('.modal-body').html(html);
	               
	               
	               $.get( '${pageContext.request.contextPath}/Acct/get/' + id + '.koala').done(function(json){
	            	   selectItems["custIdID"]=customers ;
	            	   self.initPage(dialog.find('form'));
	                       json = json.data;
	                        var elm;
	                        for(var index in json){
	                            elm = dialog.find('#'+ index + 'ID');
	                            if(elm.hasClass('select')){
	                                elm.setValue(json[index]);
	                            }else{
	                                elm.val(json[index]);
	                            }
	                        }
	                });
	                dialog.modal({
	                    keyboard:false
	                }).on({
	                    'hidden.bs.modal': function(){
	                        $(this).remove();
	                    }
	                });
	                dialog.find('#save').on('click',{grid: grid}, function(e){
	                    if(!Validator.Validate(dialog.find('form')[0],3))return;
	                    $.post('${pageContext.request.contextPath}/Acct/update.koala?id='+id, dialog.find('form').serialize()).done(function(result){
	                        if(result.result == 'success'){
	                            dialog.modal('hide');
	                            e.data.grid.data('koala.grid').refresh();
	                            e.data.grid.message({
	                            type: 'success',
	                            content: '保存成功'
	                            });
	                        }else{
	                            dialog.find('.modal-content').message({
	                            type: 'error',
	                            content: result.actionError
	                            });
	                        }
	                    });
	                });
	        });
	    },
	    initPage: function(form){
	    	
	    	
        		
        		form.find('.form_datetime').datetimepicker({
	                   language: 'zh-CN',
	                   format: "yyyy-mm-dd",
	                   autoclose: true,
	                   todayBtn: true,
	                   minView: 2,
	                   pickerPosition: 'bottom-left'
	               }).datetimepicker('setDate', new Date());//加载日期选择器
	               form.find('.select').each(function(){
	                    var select = $(this);
	                    var idAttr = select.attr('id');
	                    select.select({
	                        title: '请选择',
	                        contents: selectItems[idAttr]
	                    }).on('change', function(){
	                        form.find('#'+ idAttr + '_').val($(this).getValue());
	                    });
	               });
        		
	              
	    },
	    remove: function(ids, grid){
	    	var data = [{ name: 'ids', value: ids.join(',') }];
	    	$.post('${pageContext.request.contextPath}/Acct/delete.koala', data).done(function(result){
	                        if(result.result == 'success'){
	                            grid.data('koala.grid').refresh();
	                            grid.message({
	                                type: 'success',
	                                content: '删除成功'
	                            });
	                        }else{
	                            grid.message({
	                                type: 'error',
	                                content: result.result
	                            });
	                        }
	    	});
	    }
	};
	
	$.get( '${pageContext.request.contextPath}/Customer/query-all.koala').done(function(data){
    	var items = data.data ;
    	
    	customers.push({title:'请选择',value:''});
    	for(var i =0 ,len=items.length;i<len ;i++){
    		customerMap[items[i]["id"]]=items[i]["name"] ;
    		customers.push({title:items[i]["name"],value:items[i]["id"]});
    	}
    	
    	//selectItems['custIdID']=contents ;
    	
    	PageLoader.initSearchPanel();
    	PageLoader.initGridPanel();
    	form.find('#search_ACCT').on('click', function(){
                if(!Validator.Validate(document.getElementById("<%=formId%>"),3))return;
                var params = {};
                form.find('input').each(function(){
                    var $this = $(this);
                    var name = $this.attr('name');
                    if(name){
                        params[name] = $this.val();
                    }
                });
                grid.getGrid().search(params);
            });
    	
});
	
});

var openDetailsPage = function(id){
        var dialog = $('<div class="modal fade"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button><h4 class="modal-title">查看</h4></div><div class="modal-body"><p>One fine body&hellip;</p></div><div class="modal-footer"><button type="button" class="btn btn-info" data-dismiss="modal">返回</button></div></div></div></div>');
        $.get('<%=path%>/Acct-view.jsp').done(
				function(html) {
					dialog.find('.modal-body').html(html);
					$.get(
							'${pageContext.request.contextPath}/Acct/get/' + id
									+ '.koala').done(function(json) {
						json = json.data;
						var elm;
						for ( var index in json) {
							dialog.find('#' + index + 'ID').html(json[index]);
						}
					});
					dialog.modal({
						keyboard : false
					}).on({
						'hidden.bs.modal' : function() {
							$(this).remove();
						}
					});
				});
	}
</script>
</head>
<body>
	<div
		style="width: 98%; margin-right: auto; margin-left: auto; padding-top: 15px;">
		<!-- search form -->
		<form name=<%=formId%> id=<%=formId%> target="_self"
			class="form-horizontal">
			<input type="hidden" name="page" value="1"> <input
				type="hidden" name="pagesize" value="10">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<div class="form-group">
							<label class="control-label" style="width: 100px; float: left;">客户名称:&nbsp;</label>
							<div style="margin-left: 15px; float: left;">
								<div class="btn-group select" id="custId_SELECT"></div>
								<input type="hidden" id="custIdID_" name="custId" />
							</div>
						</div>
					</td>
					<td style="vertical-align: bottom;"><button id="search_ACCT"
							type="button"
							style="position: relative; margin-left: 35px; top: -15px"
							class="btn btn-primary">
							<span class="glyphicon glyphicon-search"></span>&nbsp;查询
						</button></td>
				</tr>
			</table>
		</form>
		<!-- grid -->
		<div id=<%=gridId%>></div>
	</div>
</body>
</html>
