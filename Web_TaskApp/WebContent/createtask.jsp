<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="obj.*" %>
 <%@page import="java.util.ArrayList" %>
 <%@page import="ui.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="resources/image/favicon.ico">
<title>Okask!-新規作成画面-</title>
<link rel="stylesheet" href="resources/css/default.css">
<link rel="stylesheet" href="resources/css/createtask.css">
</head>

<%
	//30分に一回リロードする
 	response.setIntHeader("Refresh", 1800);
%>
<body>
<header>
	<div class="header-container">
		<div class="relative">
			<h1>Okask!</h1>
			<%
			//表示するメッセージ
			int user_id = (int)session.getAttribute("user_id");
			String user =  "ようこそ" + user_id + "さん　　　";
			%>
			<div class="welcomeMessage" style="display:inline-flex">
				<div><%=user %></div>
				<!-- ログアウトボタン -->
				<div>
					<form action="/Web_TaskApp/LogoutServlet" method="POST">
						<input type="submit" value="ログアウト" />
						<input type="hidden" name="functionStr" onClick= "onClickLogoutButton();" />
					</form>
				</div>
			</div>
		</div>
	</div>
</header>

	<h3>タスク新規作成</h3>
		<form action="/Web_TaskApp/TaskAppServlet" method="POST">
			<ul class="contents-container">
				<li>
					<label for="deadline">期限<span class="required">必須</span></label><br>
					<input type="date" name="deadline" id="deadline" required><br><br>
				</li>
				<li>
					<label for="taskname">タスク名<span class="required">必須</span></label><br>
					<input type="text" name="taskname" id="task" size="50" required><br><br>
				</li>
				<li>
					<label for="content">内容<span class="required">必須</span></label><br>
					<textarea name="content" id="content" rows="10" cols="100" required></textarea><br><br>
				</li>
				<li>
					<label for="client">依頼主<span class="required">必須</span></label><br>
					<input type="text" name="client" id="client" required><br><br>
				</li>
			</ul>
				<p class="sendButton"><input class="btn-square" type="submit" value="新規作成" onclick="onClickCreateButton();"></p>
				<input id="functionStr" name="functionStr" type="hidden" />
		</form>

<footer>
	<div class="footer-container">
		<p><small>©2020 Okask! Corporation(仮)</small></p>
	</div>
</footer>

<!-- スクリプト -->
<script>
  //新規作成ボタンが押された際は、functionStrに'gocreatetaskui'をセット
  function onClickCreateButton(){
	document.getElementById("functionStr").value = "createtask";
  }
</script>
</body>
</html>