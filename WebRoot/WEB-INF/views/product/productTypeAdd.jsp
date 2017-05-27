<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
    
        $('#productTypeAddForm').form({
            url : '${path }/taomuManager/addProductType',
            onSubmit : function() {
            	var filename = $("#imgFile").val();
            	var str=filename.substring(0,filename.lastIndexOf("."));
            	var regStr=/\.jpg$|\.jpeg$|\.gif$/i;
            	if(filename==null || filename==""){
            		alert("请选择图片!");
            		return false;
            	}
            	if(!regStr.test(filename)){
            		alert("格式不正确");
            		return false;
            	}
            	
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    parent.$.modalDialog.handler.dialog('close');
                } else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        })
      
    }); 
  
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;" >
        <form id="productTypeAddForm" method="post" enctype="multipart/form-data">
            <table class="grid" style="height: 386px; width: 513px; ">
                <tr>
                    <td align="right">商品分类名称：</td>
                    <td><input id="name" name="name" type="text" placeholder="请输入商品分类名称" class="easyui-validatebox span2" data-options="required:true" value=""></td>
                </tr>
                <tr>
                    <td align="right">图片：</td>
                    <td><input type="file" name="imgFile" id="imgFile" multiple="multiple"><br><span style="color:gray;">只支持(.jpg.jpng.png格式)</span></td>
                </tr>
                <tr>
                	<td align="right">父节点：</td>
                    <td >
                        <select id="pid" name="pid" class="easyui-combobox" data-options="width:130,height:29,edittable:false,panelHeight:'auto'">
                        	<option value="0">无</option>
                        <c:forEach items="${typeList}" var="typeList">
                       		<option value="${typeList.id}">${typeList.name}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">备注：</td>	
                    <td colspan="3"><textarea id="description" name="description" rows="5" cols="30" style="resize:none" ></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>