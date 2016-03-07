
<%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 04.03.2016
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
 <h2>Meal list.</h2>
 <form method="POST" action="meals">
     <table style="border: 1px solid; width: 500px; text-align:center">
         <thead style="background:#888">
         <tr>
             <th>id</th>
             <th>description</th>
             <th>colories</th>
             <th>date</th>
         </tr>
         </thead>
         <tbody>
         <c:forEach items="${meals}" var="meal">

             <tr style="${meal.exceed ? 'color: red':'color: green'}">
                 <td><c:out value="${meal.id}"/></td>
                 <td><c:out value="${meal.description}"/></td>
                 <td><c:out value="${meal.calories}"/></td>
                 <td><c:out value="${meal.dateTime}"/></td>
             </tr>

         </c:forEach>
         </tbody>
     </table>
 </form>
</body>
</html>
