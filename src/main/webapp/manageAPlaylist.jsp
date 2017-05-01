<%--@elvariable id="songs" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="listName" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:import url="/head.jsp" />
<body>
<c:import url="/bodyTop.jsp" />
<div id="container">
        <c:set var="songCount" value="0"></c:set>
        <c:forEach var="song" items="${songs}">
            <c:set var="songCount" value="1"></c:set>
        </c:forEach>

        <!--p>song count is ${songCount} from songs = ${songs}</p-->

    <div class="pure-g">
        <div class="pure-u-1-2">
            <h3>Playlist ${listName}  </h3>
            <c:if test="${songCount > 0}">
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
                    </table>
                    <p></p>>
                    <input type="submit" name="Delete" value="Delete" />
                </form>

                <form action="ControlPlayer" method="POST">
                    <input type="submit" name="Play" value="Play" />
                    <input type="submit" name="Stop" value="Stop" />
                    <input type="submit" name="Skip" value="Skip" />
                    <input type="submit" name="Previous" value="Previous" />
                </form>
            </c:if>
            <c:if test="${songCount < 1}">
                <p>is empty</p>
            </c:if>
        </div>

        <div class="pure-u-1-2">
            <h3> Upload a song to the playlist ${listName}</h3>
            <form action="MultiFileUpload" method="POST" enctype="multipart/form-data">
                <table>
                    <tr>
                        <td>New Song(s): </td>
                        <td><input type="file" name="file" id="file" multiple></td>
                    </tr>
                </table>
                <input type="submit" name="Upload" value="Upload" />
            </form>
            <h3>. </h3>

            <c:if test="${fn:length(potentialSharees) gt 0}">
                <form action="SharePlaylist" method="POST">
                    <table>
                        <c:forEach var="user" items="${potentialSharees}">
                            <tr>
                                <td><input type="radio" name="userID" value=${user.id} /></td>
                                <td>${user.name}</td>
                            </tr>
                        </c:forEach>
                    </table>

                    <input type="submit" name="Share" value="Share" />
                </form>
            </c:if>

            <c:if test="${fn:length(currentSharees) gt 0}">
                <form action="SharePlaylist" method="POST">
                    <table>
                        <c:forEach var="user" items="${currentSharees}">
                            <tr>
                                <td><input type="radio" name="userID" value=${user.id} /></td>
                                <td>${user.name}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <input type="submit" name="UnShare" value="Unshare" />
                </form>
            </c:if>
        </div>
    </div> <!-- pure-g -->
</div>
<c:import url="/footer.jsp" />



</body>
</html>