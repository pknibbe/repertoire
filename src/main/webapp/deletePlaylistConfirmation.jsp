<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->

            <h3>Manage Accounts</h3>
            <form action="DeletePlaylist" method="POST">
                <p>  </p>
                <p>  </p>
                <h1>Are you sure you want to delete playlist ${listName}?</h1>
                 <input type="submit" name="Cancel" value="Cancel" />
                <input type="submit" name="Delete" value="Delete" />
            </form>

<c:import url="/footer.jsp" />


