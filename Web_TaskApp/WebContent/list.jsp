<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@page import="java.util.ArrayList" %>
 <%@page import="obj.*" %>
 <%@page import="ui.*" %>
<!DOCTYPE html>
<html>
<head>
<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<meta charset="UTF-8">
<title>タスク一覧</title>
<link rel="shortcut icon" href="resources/image/favicon.ico">
</head>
<%
	//30分に一回リロードする
 	response.setIntHeader("Refresh", 1800);
%>

<body>

<!-- ナビゲーションバー  -->
<nav class="navbar navbar-expand-lg navbar-light bg-light ">
  <a class="navbar-brand" href="#">Okask!</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
  <ul class="navbar-nav mr-auto">
        <li class="nav-item">
        <a class="nav-link" href="createtask.jsp">新規タスク <span class="sr-only">(current)</span></a>
      </li>
  </ul>
    <form class="form-inline my-2 my-lg-0" action="/Web_TaskApp/LogoutServlet" method="POST">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Logout</button>
      <input type="hidden" name="functionStr" onClick= "onClickLogoutButton();" />
    </form>
  </div>
</nav>


<div class="mt-auto" >
<form name = "form" action="/Web_TaskApp/TaskAppServlet" method="POST">

<!-- タスク削除ボタン -->
<div class="text-right my-2">
	<input type="submit" value="削除" class="btn btn-primary py-0" onclick="onClickDeleteButton();" />
	<input id="functionStr" name = "functionStr" type="hidden" />
</div>

<!-- 一覧 -->
<input type="checkbox" name="all" onClick="AllChecked();" />全て選択
<table class="table table-bordered">
  <thead>
    <tr>
      <th scope="col"></th>
      <th scope="col">タスク名</th>
      <th scope="col">内容</th>
      <th scope="col">依頼者</th>
      <th scope="col">期限</th>
    </tr>
  </thead>

  <tbody>
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
      <th scope="row"><input id="Checkbox<%=rowNum %>" type="checkbox" name = "task" value = "<%= taskNum%>" onClick="DisChecked();"/></th>
      <td><%= taskName %></td>
      <td><%= content %></td>
      <td><%= client %></td>
      <td><%= deadLine%></td>
    </tr>
<%
	rowNum++;
	}
%>
 </tbody>
</table>
</form>
</div>


<!-- スクリプト -->
<script>
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