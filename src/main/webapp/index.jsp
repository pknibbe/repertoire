<%--@elvariable id="UserInfo" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />

        <!--h4>log in</h4-->
        <p></p>
        <form class="pure-form pure-form-aligned" action="ExternalAction" method="GET">
            <fieldset>
                <div class="pure-g">
                    <div class="pure-u-1-8"></div>
                    <div class="pure-u-1-2">
                    </div>
                </div>
                    <div class="pure-control-group">
                        <label for="userName">Username</label>
                        <input id="userName" type="text" name="userName">
                    </div>
                    <div class="pure-control-group">
                        <label for="password">Password</label>
                        <input id="password" type="password" name="password">
                    </div>
                    <div class="pure-controls">
                        <input type="submit" name="submit" value="submit">
                    </div>
            </fieldset>
        </form>

    <p></p><p></p>

        <form class="pure-form pure-form-aligned" action="MessageAdmin" method="POST">
            <fieldset>
                <div class="pure-g">
                    <div class="pure-u-1-8"></div>
                    <div class="pure-u-1-2">
                        <h3>request log in assistance</h3>
                    </div>
                </div>
                    <div class="pure-control-group">
                        <label for="yourname">Your Name</label>
                        <input id="yourname" type="text" placeholder="Your Name">
                    </div>
                    <div class="pure-control-group">
                        <label for="options">Type of Assistance</label>
                        <select id="options">
                            <option value="account">new account</option>
                            <option value="username">username reminder</option>
                            <option value="password">password reset</option>
                        </select>
                    </div>
                    <div class="pure-controls">
                        <input type="submit" name="submit" value="submit">
                    </div>
            </fieldset>
        </form>

<c:import url="/footer.jsp" />


</body>
</html>