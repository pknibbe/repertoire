<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<c:import url="/sidebar.jsp" />
<link href="../propertiesStyle.css" rel="stylesheet" type="text/css"/>

    <body>
        <h4>Search by last name or by ID</h4>
        <form action="/java112/EmployeeSearchResults" method="GET">
            Search by last name:
            <input type="radio" name="options" value="name" /><br />
            Search by ID:
            <input type="radio" name="options" value="ID" />
            <br /><br />
            Search value:
            <input type="text" name="value" /><br /><br />
            <input type="submit" name="" value="Enter" />
        </form>
        <c:import url="/mainpanel.jsp" />
        <c:import url="/footer.jsp" />
    </body>
</html>