<%--@elvariable id="songToDelete" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">
    <div id="a">

            <h3>   </h3>
            <form action="DeleteSong" method="POST">
                <p>  </p>
                <p>  </p>
                <h1>Are you sure you want to delete song ${songToDelete}?</h1>
                 <input type="submit" name="Cancel" value="Cancel" />
                <input type="submit" name="Delete" value="Delete" />
            </form>
    </div>
</div>
<c:import url="/footer.jsp" />

</body>
</html>

