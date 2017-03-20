<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->

            <h3>  </h3>
<form action="DeleteOneSongFromAPlaylist" method="POST">
    <table>
        <thead><tr><th>Select</th><th>Song</th></tr></thead>
        <tbody>
            <c:forEach var="song" items="${songs}">
                <tr>
                    <td><input type="radio" name="songID" value=${song.id} /></td>
                    <td>${song.location}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table><br/><br/>
    <input type="submit" name="Delete" value="Delete" />
</form>

<p> </p>
<form action="Upload" method="POST" enctype="multipart/form-data">

    New Song<input type="file" name="file" id="file" />
    <input type="submit" name="Upload" value="Upload" />
</form>

<c:import url="/footer.jsp" />


