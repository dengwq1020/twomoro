<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp"%>
<script type="text/javascript">
	$(function() {
		var roleIds = ${roleIds };
		$('#organizationId').combotree({
			url : '${path }/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value : '${user.organizationId}'
		});

		$('#roleIds').combotree({
			url : '${path }/role/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			multiple : true,
			required : true,
			cascadeCheck : false,
			value : roleIds
		});

		$('#userEditForm').form({
			url : '${path }/u/edit',
			onSubmit : function() {
				/* progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				} */
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
		$("#sex").val('${user.sex}');
		$("#userType").val('${user.userType}');
		$("#status").val('${user.status}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;padding: 3px;">
		<form id="userEditForm" method="post">
		<input type="hidden" name="hidd" value="1">
			<table class="grid">
				<tr>
					<td>用户编号</td>
					<td>${id}</td>
				</tr>
				<tr>
					<td>用户类型</td>
					<td><select id="userType" name="userType" value="${type}"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1"
								<c:if test="${type==1}">selected="selected"</c:if>>普通用户</option>
							<option value="2"
								<c:if test="${type==2}">selected="selected"</c:if>>商户</option>
							<option value="3"
								<c:if test="${type==3}">selected="selected"</c:if>>业务员</option>
					</select></td>
				</tr>

				<tr>
					<td>电话</td>
					<td>${phone}</td>
				</tr>
				<tr>
					<td>用户状态</td>
					<td><select id="state" name="status" value="${status}"
						class="easyui-combobox"
						data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="1"
								<c:if test="${status==1}">selected="selected"</c:if>>正常</option>
							<option value="0"
								<c:if test="${status==0}">selected="selected"</c:if>>停用</option>
					</select></td>
				</tr>
			</table>
		</form>
	</div>
</div>