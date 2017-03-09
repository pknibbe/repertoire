<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
    <div id="container">
        <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
        <h2>${message}</h2>
        <h3>Login</h3>
        <form action="ExternalAction" method="GET">
            user name:  <input type="text" name="user_name"  /><br />
            password: <input type="text" name="user_pass" /><br />
            <input type="submit" value="Submit" name="Enter" />
        </form>

        <br /><br />

        <div id="request">

        <h3>Request sign-in help</h3>
        <form action="ExternalAction" method="POST">
            Request an account:  <input type="radio" name="options" value="account" /><br />
            Request reminder of user name: <input type="radio" name="options" value="username" /><br />
            Request password reset: <input type="radio" name="options" value="password" /><br />
            Message text: <input type="text" name="message"/><br />
            <input type="submit" name="Request" value="Enter" />
        </form>
        </div>

        <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

    </div>   <!-- end container -->
</body>
</html>
