<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">




<script src="<c:url value='/resources/jslib/jquery-1.7.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jslib/extjs-4.0.7/ext-all-debug.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/jslib/twitter-bootstrap-1.4/js/bootstrap-dropdown.js'/>" type="text/javascript"></script>


<link rel="stylesheet" type="text/css" href="<c:url value='/resources/jslib/bootstrap-2.0/css/bootstrap.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/jslib/extjs-4.0.7/resources/css/ext-all.css'/>"  />

<style type="text/css">
/* Override some defaults */
html,body {
	background-color: #eee;
}

body {
	padding-top: 40px;
	/* 40px to make the container go all the way to the bottom of the topbar */
}
/* Page header tweaks */
.page-header {
	background-color: #f5f5f5;
	padding: 20px 20px 10px;
	margin: -20px -20px 20px;
}

.container {
	width: 820px;
	/* downsize our container to make the content feel a bit tighter and more cohesive. NOTE: this removes two full columns from the grid, meaning you only go to 14 columns and not 16. */
}

/* The white background content wrapper */
.content {
	background-color: #fff;
	padding: 20px;
	margin: 0 -20px;
	/* negative indent the amount of the padding to maintain the grid system */
	-webkit-border-radius: 0 0 6px 6px;
	-moz-border-radius: 0 0 6px 6px;
	border-radius: 0 0 6px 6px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .15);
}

/* Give a quick and non-cross-browser friendly divider */
.content .span4 {
	margin-left: 0;
	padding-left: 19px;
	border-left: 1px solid #eee;
}

.topbar .btn {
	border: 0;
}

table th, table td {
	padding: 0;
}

input[type="button"] {
	width: 13px;
	height: 13px;
} 

label {
	display: inline;
}
</style>



<title>ระบบบริหารงานทดสอบความชำนาญ</title>
</head>
<body>
 	
	<div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="brand" href='<c:url value="/"/>'>ระบบ OLP</a>

          <ul class="nav">
            <li class="active"><a href='<c:url value="/"/>'>Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
          </ul>
         
         <div class="pull-right">
	         <ul class="nav secondary-nav">
	         	 <li class="dropdown" data-dropdown="dropdown">
	         	 	<a href="#" class="dropdown-toggle"><sec:authentication property="name" /></a>
	         	 	
	         	 	<ul class="dropdown-menu">
						<li><a href="<c:url value='logout'/>">logout</a></li>
					</ul>
	         	 	
	         	 </li>
	         </ul>
         </div>
         	
         	
         
        </div>
      </div>
    </div>
    
	<div class="container">

      	<div class="content">
	        <div class="page-header">
				 <h1>ระบบพิมพ์รายงาน</h1>
			</div>

		
			<div class="row">
				<div class="span14">
					<ul>
						<li><a href="printInvoice">พิมพ์ใบแจ้งหนี้ชำระค่าธรรมเนียมเลือกจากรหัสลูกค้า</a></li>
						<li><a href="printInvoice2">พิมพ์ใบแจ้งหนี้ชำระค่าธรรมเนียมเลือกจากหมายเลขลงทะเบียน</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>