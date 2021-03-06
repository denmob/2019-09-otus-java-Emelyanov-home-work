<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users list!</title>
    <style type="text/css">
        body {
            padding: 50px;
        }
		.users td {
            border: 1px solid lightgray;
            padding: 5px;
        }
    </style>
</head>
<body>
	<h1>Список пользователей:</h1>
	<table style="width: 400px">
	   <thead>
	   <tr>
		   <td style="width: 150px">Номер</td>
		   <td style="width: 150px">Имя</td>
		   <td style="width: 100px">Логин</td>
		   <td style="width: 100px" >Пароль</td>
	   </tr>
	   </thead>
	  <#list users as user>
	    <tr>
			<td>${user.id}</td>
			<td>${user.name}</td>
			<td>${user.login}</td>
			<td>${user.password}</td>
	    </tr>
	  </#list>
	</table> 
	<p><a href="/admin">Вернуться в админку</a></p>
</body>
</html>