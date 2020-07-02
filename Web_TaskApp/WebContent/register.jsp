<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ui.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

<link rel="shortcut icon" href="resources/image/favicon.ico">
<title>Okask! ユーザ登録画面</title>
</head>
<body>

<!-- ナビゲーションバー  -->
<nav class="navbar navbar-expand-lg navbar-light bg-light ">
  <a class="navbar-brand" href="#">Okask!</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
  <ul class="navbar-nav mr-auto">
  </ul>
    <form class="form-inline my-2 my-lg-0" action="login.jsp">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Login</button>
    </form>
  </div>
</nav>

<!--見出し -->
<div class="container my-5">
<h1>Okaskへようこそ！</h1>
<h2>新規登録して利用を開始しましょう</h2>

<!-- 登録フォーム -->
<div class="container col-4 mx-auto bg-light py-4" >
<span class="label label-danger" ><font color="red">${message}</font></span>
   <form action="/Web_TaskApp/RegisterServlet" method="POST" onsubmit="return check();">
  <div class="form-group">
    <label for="UserID">ユーザID</label>
    <input type="text" name="user_id" class="form-control" id="UserID" aria-describedby="userHelp" placeholder="Enter userID" required autofocus maxlength='11'>
    <small id="userHelp" class="form-text text-muted">他で使用されているユーザIDは登録できません</small>
  </div>
  <div class="form-group">
    <label for="exampleInputPassword1">Password</label>
    <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password" required maxlength='20'>
    <small id="uexampleInputPassword1" class="form-text text-muted">パスワードは推測されにくいものを使用してください。</small>
  </div>
  <button type="submit" class="btn btn-primary" onClick = "onRegisterButton();">登録する</button>
  <input id ="functionStr" type="hidden" name="functionStr" />
</form>
</div>
</div>

  <!-- フッタ -->
  <footer class="footer mt-auto py-3 bg-light fixed-bottom">
    <div class="container text-right">
      <span class="text-muted"><small>©2020 Okask! Corporation(仮)</small></span>
    </div>
  </footer>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

</body>

<script>
//登録ボタンが押された際はfunctionStrに'register'をセット
function onRegisterButton(){
	document.getElementById( "functionStr" ).value = "register";
}

function check() {
	  var num = document.getElementById('UserID').value;
	  if(!isNaN(num)){
	    return true;
	  } else {
	    alert("ユーザIDには数値のみ入力してください。");
	    return false;
	  }
	}
</script>

</html>