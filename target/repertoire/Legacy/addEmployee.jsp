<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<c:import url="/sidebar.jsp" />
    <body>
        <h3>${project4AddMessage}</h3><br/>
        <h4>Add an Employee</h4>
        <form action="/java112/EmployeeAddAction" method="POST">
            Last name:  <input type="text" name="lastName"  /><br />
            First name: <input type="text" name="firstName"  /><br />
            Social:     <input type="text" name="social" /><br />
            Department: <input type="text" name="dept" /><br />
            Room:       <input type="text" name="room" /><br />
            Phone:      <input type="text" name="phone" /><br /><br /><br />
            <input type="submit" name="Enter" />
        </form>
        <br /><br />
        <c:import url="/mainpanel.jsp" />
        <c:import url="/footer.jsp" />
    </body>
</html>