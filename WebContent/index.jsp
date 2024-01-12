<%@ page import="my.edu.utem.ftmk.fis9.maintenance.model.Staff" %>
<%@ page import="my.edu.utem.ftmk.fis9.global.controller.mbean.UserManagedBean" %>
<%
	String redirect = "login";
	UserManagedBean userMBean = (UserManagedBean) session.getAttribute("userMBean");
	
	if (userMBean != null)
	{
		Staff user = userMBean.getUser();
		
		if (user != null)
			redirect = "utama";
	}
	
	response.sendRedirect(redirect);
%>