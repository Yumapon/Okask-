<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="obj.*" %>
<%@page import="ui.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="resources/image/favicon.ico">
<title>Okask!-新規作成タスク確認画面-</title>
<link rel="stylesheet" href="resources/css/default.css">
<link rel="stylesheet" href="resources/css/createcheck.css">
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
			String user =  "ようこそ" + user_id+ "さん　　　";
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

<%
	Task task = (Task)session.getAttribute("newTask");

	//タスク内容を取得
		String deadLine = task.getStringDate();
		String taskName =  task.getTaskName();
		String content = task.getContent();
		String client = task.getClient();
%>
<ul class="taskcheckform">
	<li>
		<label for="deadline">期限</label><br>
		<%= deadLine%>
	</li>
	<li>
		<label for="taskname">タスク名</label><br>
		<%= taskName %>
	</li>
	<li>
		<label for="content">内容</label><br>
		<%= content %>
	</li>
	<li>
		<label for="client">依頼主</label><br>
		<%=client %>
	</li>
</ul>

<form>
	<p class="registerButton"><input class="btn-square" type="submit" value="登録" onClick="onClickConfirmButton();"></p>
	<input id="functionStr" type="hidden" name="functionStr" />
</form>

<footer>
	<div class="footer-container">
		<p><small>©2020 Okask! Corporation(仮)</small></p>
	</div>
</footer>

<!-- スクリプト -->
<script>
  //確定ボタンが押された際は、functionStrに"confirmtask"をセット
  function onClickConfirmButton(){
	document.getElementById("functionStr").value = "confirmtask";
  }
</script>
</body>
</html>