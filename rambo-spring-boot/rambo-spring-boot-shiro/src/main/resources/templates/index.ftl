<html> 
	<head> 
		<title>welcome jboot shiro!</title>
	</head> 
	<body>
		<h1>welcome jboot shiro!</h1>
		<ul>
	<!--		<#if Session["org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"]?exists>
				<li>Hi,${Session["org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"]}</li>
			<#else>
				<li><a href="${request.contextPath}/login">login</a></li>
			</#if>
	-->		
			<@shiro.guest>  
				<li><a href="${request.contextPath}/login">login</a></li>
			</@shiro.guest> 
			<@shiro.user>  
			<li>Hi [<@shiro.principal/>], <a href="${request.contextPath}/logout">logout</a></li> 
			</@shiro.user>  
			<li><a href="${request.contextPath}/home">home</a></li>
			
			<@shiro.hasRole name="admin">  
				<li><a href="${request.contextPath}/admin">admin</a></li>
			</@shiro.hasRole>   
			
			
		<!--	<#if Session["org.apache.shiro.subject.support.DefaultSubjectContext_PRINCIPALS_SESSION_KEY"]?exists>
				<li><a href="${request.contextPath}/logout">logout</a></li>
			</#if>
			-->

		</ul>
		
		Refer to : <a href="http://www.sojson.com/blog/143.html">http://www.sojson.com/blog/143.html</a> 
	</body>
</html>