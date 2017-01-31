<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<c:import url="/sidebar.jsp" />
    <body>
        <h3>${sessionMessage}</h3><br/>
        <h4>Add a User</h4>
        <form action="/UserAddAction" method="POST">
            user name:  <input type="text" name="userName"  /><br />
            privileges: <input type="text" name="privileges" /><br />
            <input type="submit" name="Enter" />
        </form>
        <br /><br />
        <c:import url="/mainpanel.jsp" />
        <c:import url="/footer.jsp" />
    </body>
</html>