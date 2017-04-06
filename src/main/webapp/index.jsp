<%--@elvariable id="UserInfo" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">

        <div id="left">
            <h3>Login</h3>
            <form action="ExternalAction" method="GET">
                <table>
                    <tr><td>user name:</td><td>  <input type="text" name="user_name"  /> </td></tr>
                    <tr><td>password:</td><td> <input type="text" name="user_pass" /></td></tr>
                </table>
                <input type="submit" value="Submit" name="Enter" />
            </form>
        </div>

        <div id="request">

            <h3>Request sign-in help</h3>
            <form action="MessageAdmin" method="POST">
                Your name: <input type="text" name="name" />
                <select name="options">
                    <option value="account">new account</option>
                    <option value="username">username reminder</option>
                    <option value="password">password reset</option>
                </select>
                <input type="submit" name="Request" value="Enter" />
            </form>
        </div>
</div>

<c:import url="/footer.jsp" />


</body>
</html>