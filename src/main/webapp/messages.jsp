<%--@elvariable id="messages" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="names" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">
    <div id="a">

            <h3>  </h3>
            <table>
                <thead><tr><th>Sender</th><th>Subject</th><th>Read?</th><th>Content</th></tr></thead>
                <tbody>
                    <c:forEach var="message" items="${messages}">
                        <tr>
                            <td>${message.sender}</td>
                            <td>${message.subject}</td>
                            <td>${message.readFlag}</td>
                            <td>${message.content}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table><br/><br/>

        <h3>Send a Message </h3>
        <form action="SendMessage" method="POST">
            <table>
                <tr> <td>To:</td>
                    <td>
                        <select name="to">
                            <c:forEach var="userName" items="${names}">
                                <option value="${userName}">${userName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr><td>Subject: </td><td><input type="text" name="subject" /></td> </tr>
                <tr><td>Content: </td><td><input type="text" name="content" /></td> </tr>
            </table>
            <input type="submit" name="Send" value="Send" />
        </form>
    </div>
</div>
<c:import url="/footer.jsp" />


</body>
</html>
