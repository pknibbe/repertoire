<%--@elvariable id="UserInfo" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">

        <div id="left">
            <form class="pure-form pure-form-aligned" action="ExternalAction" method="GET">
                <fieldset>
                    <legend>Login</legend>
                    <div class="pure-control-group">
                        <label for="userName">Username</label>
                        <input id="userName" type="text" name="userName" placeholder="UserName">
                    </div>
                    <div class="pure-control-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" name="password" placeholder="Password">
                    </div>
                    <div class="pure-controls">
                        <input type="submit" name="submit" value="submit">
                    </div>
                </fieldset>
            </form>
        </div>

        <div id="request">

            <form class="pure-form pure-form-aligned" action="MessageAdmin" method="POST">
                <fieldset>
                    <legend>Request sign-in assistance</legend>

                    <div class="pure-control-group">
                        <label for="yourname">Your name</label>
                        <input id="yourname" type="text" name="name" placeholder="Your Name">
                    </div>
                    <div class="pure-control-group">
                        <label for="options">Your name</label>
                        <select id="options" name="options">
                            <option value="account">new account</option>
                            <option value="username">username reminder</option>
                            <option value="password">password reset</option>
                        </select>
                    </div>
                    <div class="pure-controls">
                        <input type="submit" name="submit" value="submit"></input>
                    </div>
                </fieldset>
            </form>
        </div>
</div>

<c:import url="/footer.jsp" />


</body>
</html>