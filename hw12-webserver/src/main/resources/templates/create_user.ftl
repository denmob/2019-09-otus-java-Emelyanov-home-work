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
		        <label for="holder-input">Имя:</label>
		        <input id="holder-input" name="name" type="text" value="Vasya"/>
				<input id="holder-input" name="login" type="text" value="god"/>
				<input id="holder-input" name="password" type="text" value="123"/>
		    </div>
		
		    <div class="row">
		        <button type="submit">Сохранить</button>
		    </div>
		</form>
	</body>
</html>