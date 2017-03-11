<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->

    <h3>Update a User</h3>

    <form action="/UpdateUserInformation" method="POST">
        <table>
            <thead><tr><th>Field</th><th>Value</th></tr></thead>
            <tbody>
            <tr><td>id</td><td><input type="hidden" name="id" value=${UserInfo.id}></td></tr>
            <tr><td>name</td><td><input type="text" name="Name" value=${UserInfo.name} /></td></tr>
            <tr><td>user name</td><td><input type="text" name="UserName" value=${UserInfo.user_name} /></td></tr>
            <tr><td>password</td><td><input type="text" name="NewPassword" value=${UserInfo.user_pass}  /></td></tr>
            <tr><td>role</td><td>
                <select name="Role">
                    <option value=${UserInfo.role_name}>${UserInfo.role_name}</option>
                    <option value="administrator">administrator</option>
                    <option value="registered-user">registered-user</option>
                </select></td></tr>
        </table>

        <input type="submit" value="Submit" name="Enter" />
    </form>

    <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

