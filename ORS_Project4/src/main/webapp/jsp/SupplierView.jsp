<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.rays.pro4.controller.SupplierCtl"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16*16" />
<title>Supplier Registration Page</title>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
	$(function() {
		$("#udate4").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2020',

		});
	});
</script>


</head>
<body>
	<jsp:useBean id="bean" class="com.rays.pro4.Bean.SupplierBean"
		scope="request"></jsp:useBean>

	<form action="<%=ORSView.SUPPLIER_CTL%>" method="post">
		<%@include file="Header.jsp"%>




		<center>
			<h1>
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				<tr>
					<th><font>Update Supplier</font></th>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th><font>Add Supplier</font></th>
				</tr>
				<%
					}
				%>
			</h1>

			<div>
				<h3>
					<font style="color: green"><%=ServletUtility.getSuccessMessage(request)%></font>
					</h1>
					<h3>
						<font style="color: red"><%=ServletUtility.getErrorMessage(request)%></font>
						</h1>
			</div>

			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdby" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedby"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createddatetime" value="<%=bean.getCreatedDatetime()%>">
			<input type="hidden" name="modifieddatetime"
				value="<%=bean.getModifiedDatetime()%>">

			<table>



				<tr>
					<th align="left">Client Name <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="name" placeholder="Enter Name"
						size="26" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Category <span style="color: red">*</span> :
					</th>
					<td>
						<%
							HashMap map = new HashMap();
							map.put("Electronics", "Electronics");
							map.put("Furniture", "Furniture");
							map.put("Clothing", "Clothing");
							map.put("Food", "Food");
							map.put("Stationery", "Stationery");
							map.put("Books", "Books");
							map.put("Tools", "Tools");
						

							String hlist = HTMLUtility.getList("category", String.valueOf(bean.getCategory()), map);
						%> <%=hlist%>
					</td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("category", request)%></font></td>
				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Supplier Date <span style="color: red">*</span>
						:
					</th>
					<td><input type="text" name="supplierDate" id="udate4"
						readonly="readonly" placeholder=" Supplier Date" size="26"
						value="<%=DataUtility.getDateString(bean.getSupplierDate())%>"></td>
					<td style="position: fixed"><font color="red"> <%=ServletUtility.getErrorMessage("supplierDate", request)%></font></td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">Payment <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="payment" maxlength="10"
						placeholder="Enter Payment" size="26"
						value="<%=DataUtility.getStringData(bean.getPayment())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("payment", request)%></font>
					</td>
				</tr>

				<tr>
					<th style="padding: 3px"></th>
				</tr>




				<tr>
					<th></th>
					<%
						if (bean.getId() > 0) {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SupplierCtl.OP_UPDATE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SupplierCtl.OP_CANCEL%>"></td>
					<%
						} else {
					%>
					<td>&nbsp; &emsp; <input type="submit" name="operation"
						value="<%=SupplierCtl.OP_SAVE%>"> &nbsp; &nbsp; <input
						type="submit" name="operation" value="<%=SupplierCtl.OP_RESET%>"></td>

					<%
						}
					%>
				</tr>

			</table>
	</form>
	</center>

	<%@ include file="Footer.jsp"%>
</body>
</html>