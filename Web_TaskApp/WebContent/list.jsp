<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList" %>
 <%@page import="obj.*" %>
 <%@page import="ui.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="stylesheet" href="resources/css/default.css">
<link rel="stylesheet" href="resources/css/list.css">
<link rel="shortcut icon" href="resources/image/favicon.ico">
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

<form name = "form" action="/Web_TaskApp/TaskAppServlet" method="POST">
<!-- 一覧 -->
<h1 class="title">タスク一覧</h1>

<div class = "button" style="display:inline-flex">
<!-- 新規作成ボタン -->
	<input type= "submit" value="新規作成" onclick="onClickCreateButton();" />
<!-- タスク削除ボタン -->
	<input type="submit" value="削除" onclick="onClickDeleteButton();" />
</div>

	<input id="functionStr" name = "functionStr" type="hidden" />

<label>
    <input type="checkbox" name="all" onClick="AllChecked();" />全て選択
</label>
<table class="table">
<tr>
<th></th>
<th class="nowrap">期限</th>
<th class="nowrap">タスク名</th>
<th class="nowrap">内容</th>
<th class="nowrap">依頼者</th>
</tr>
<%
	@SuppressWarnings("unchecked")
	ArrayList<Task> tasklist = (ArrayList<Task>)session.getAttribute("tasklist");

	int rowNum = 0;

	//ループで一覧表示
	for(int i = 0; i < tasklist.size(); i++) {
		String taskNum = tasklist.get(i).getTaskNum();
		String deadLine = tasklist.get(i).getStringDate();
		String taskName =  tasklist.get(i).getTaskName();
		String content = tasklist.get(i).getContent();
		String client = tasklist.get(i).getClient();

%>

<tr>
 	<td><input id="Checkbox<%=rowNum %>" type="checkbox" name = "task" value = "<%= taskNum%>" onClick="DisChecked();"/></td>
	<td class="nowrap"> <%= deadLine%></td>
	<td> <%= taskName %></td>
	<td> <%= content %></td>
	<td class="nowrap"> <%= client %></td>
</tr>

<%
	rowNum++;
	}
%>

</table>
</form>

<!-- スクリプト -->
<script>
  //新規作成ボタンが押された際は、functionStrに'gocreatetaskui'をセット
  function onClickCreateButton(){
	document.getElementById("functionStr").value = "gocreatetaskui";
  }

  //削除ボタンが押された際はfunctionStrに'deletetask'をセット
  function onClickDeleteButton(){

	//チェックボックスが一つ以上選択されていたら動くようにする（課題）
	window.confirm('選択されたタスクを削除します');
	document.getElementById( "functionStr" ).value = "deletetask";
  }

  //ログアウトボタンが押された際は、functionStrに'logout'をセット
   function onClickLogoutButton(){
	document.getElementById("functionStr").value = "logout";
  }

  // 「全て選択」チェックで全てにチェック付く
  function AllChecked(){
    var all = document.form.all.checked;
    for (var i=0; i<document.form.task.length; i++){
      document.form.task[i].checked = all;
    }
  }

  // 一つでもチェックを外すと「全て選択」のチェック外れる
  function DisChecked(){
    var checks = document.form.task;
    var checksCount = 0;
    for (var i=0; i<checks.length; i++){
      if(checks[i].checked == false){
        document.form.all.checked = false;
      }else{
        checksCount += 1;
        if(checksCount == checks.length){
          document.form.all.checked = true;
        }
      }
    }
  }
</script>
</body>
</html>