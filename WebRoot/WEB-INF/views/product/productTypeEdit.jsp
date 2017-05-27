<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#roleEditForm').form({
            url : '${path }/taomuManager/editProductType',
            onSubmit : function() {
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
        });
        
        
       
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="roleEditForm" method="post" enctype="multipart/form-data">
            <table class="grid">
                <tr>
                    <td align="right">商品分类名称：</td>
                    <td><input name="id" type="hidden"   value="${id }"/>
                    <input name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox" data-options="required:true" value="${name }"></td>
                </tr>
                <tr>
                    <td align="right">父id：</td>
                    <td>
                    	<select id="pid" name="pid" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">      
                        <c:if test="${pid==0}">
                        	<option value="${pid}">无</option>
                        </c:if>
                        <c:forEach items="${typeList}" var="typeList">
                        	<option value="${typeList.id}" <c:if test="${typeList.id==pid}">selected='selected'</c:if>>${typeList.name}</option>
                        </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">状态：</td>
                    <td >
                        <select id="status" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0" <c:if test="${status==0}"> selected="selected"</c:if>>删除</option>
                            <option value="1"<c:if test="${status==1}"> selected="selected"</c:if>>正常</option>
                        </select>
                    </td>
                </tr>
                <tr>
                   <td align="right">备注：</td> 
                    <td colspan="3"><textarea id="description" name="description" rows="" cols="" >${description }</textarea></td>
                </tr>
            	<tr>
                    <td align="right">图片：</td>
                    <c:if test="${imgs!='' && imgs!=null}">
		              	<img style="width:50px;height:50px;margin-left: 10px;" src="${imgs}" >
		            </c:if>
                   <td><input type="file" name="imgs" id="imgs" multiple="multiple"><br><span style="color:gray;">只支持(.jpg.jpng.png格式)</span></td>
                </tr> 
                
            </table>
        </form>
    </div>
</div>