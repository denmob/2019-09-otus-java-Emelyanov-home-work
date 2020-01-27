<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Создание нового пользователя</title>
	</head>
	<body>
		<form id="create-form" action="/admin/userCreate" method="post" accept-charset="utf-8">
		    <h1>Новый пользователь:</h1>
		
		    <div class="row">
		        <h4>Имя:</h4>
		        <input id="userNameTextBox" name="userName" type="text" value="Vasya"/>
				<input id="userLoginTextBox" name="userLogin" type="text" value="god"/>
				<input id="userPasswordTextBox" name="userPassword" type="password" value="123"/>
		    </div>
		
		    <div class="row">
		        <button type="submit">Сохранить</button>
		    </div>
		</form>
	</body>
</html>