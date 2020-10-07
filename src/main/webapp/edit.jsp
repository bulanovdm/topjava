<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<style>
    form {
        display: table;
    }

    p {
        display: table-row;
    }

    label {
        display: table-cell;
    }

    input {
        display: table-cell;
    }
</style>
<head>
    <title>Edit Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit Meal</h2>
<form action="meals" method="post">
    <p>
        <label for="a">Date: </label> <input id="a" name="datetime" type="datetime-local" value="${meal.dateTime}"
                                             required> <br/>
    </p>
    <p>
        <label for="b">Description: </label><textarea id="b" name="description" rows="3"
                                                      required>${meal.description}</textarea> <br/>
    </p>
    <p>
        <label for="c">Calories: </label> <input id="c" name="calories" type="number" value="${meal.calories}" required>
        <br/> <br/>
    </p>
    <input type="hidden" name="id" value="${meal.id}"> <input type="submit" value="Save"> <a href="meals"><input
        type="button" value="Cancel"/></a>
</form>
</body>
</html>
