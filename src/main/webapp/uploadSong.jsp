<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<body>
<div id="container">
    <c:import url="/titlebar.jsp" /> <!-- opens body and container -->
    <p>   </p>

    <div id="songs">
        <h3>Available Songs</h3>
        <table>
            <thead><tr><th>name</th><th>performer</th><th>duration</th></tr></thead>
            <tbody>
                <c:forEach var="song" items="${songs}">
                    <tr>
                        <td>${song.name}</td>
                        <td>${song.performer}</td>
                        <td>${song.duration}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div>
        <h3>Upload Song</h3>
        <form method="POST" action="UploadSong" enctype="multipart/form-data">
            File:
            <input type="file" name="file" id="file" /> <br/>
            Destination:
            <input type="text" value="Data/" name="destination"/> <br/>
            Performer:
            <input type="text" name="performer"/> <br/>
            Duration:
            <input type="text" name="duration"/> <br/>
            <input type="submit" value="Upload" name="upload" id="upload" />
        </form>
    </div>

    <div id="main">
        <c:import url="/mainpanel.jsp" /> <!-- links to other places -->
    </div>   <!-- end main -->

    <c:import url="/footer.jsp" /> <!-- just a copywrite statement -->

</div>   <!-- end container -->
</body>
</html>
