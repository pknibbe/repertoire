<%--@elvariable id="playlists" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="songs" type="javax.servlet.http.HttpSession"--%>
<%--@elvariable id="listName" type="javax.servlet.http.HttpSession"--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:import url="/head.jsp" />
<body>
    <c:import url="/bodyTop.jsp" />

    <c:set var="songCount" value="0"></c:set>
    <c:forEach var="song" items="${songs}">
        <c:set var="songCount" value="1"></c:set>
    </c:forEach>

    <div id="container">
        <div id="a">
            <div class=pure-g">
                <div class="pure-u-1-3">
                    <h3>Your Playlists</h3>
                    <form action="ManagePlaylists" method="Post">
                        <table>
                            <thead><tr><th>Select</th><th>Name</th></tr></thead>
                            <tbody>
                               <c:forEach var="playbill" items="${playlists}">
                                   <tr>
                                        <td><input type="radio" name="listID" value=${playbill.playlist_id} /></td>
                                        <td><input type="text" name="name" value=${playbill.name}></td>
                                   </tr>
                                </c:forEach>
                                <tr>
                                    <td><input type="radio" name="listID" value=0 /></td>
                                    <td>name: <input type="text" name="newName"></td>
                                </tr>
                            </tbody>
                        </table>
                        <p>   </p>

                        <input type="submit" name="Manage" value="Manage" />
                        <input type="submit" name="Share" value="Share" />
                        <input type="submit" name="Delete" value="Delete" />
                        <input type="submit" name="Play" value="Play" />
                        <input type="submit" name="Stop" value="Stop" />

                    </form>
                </div>

                <c:if test="${songCount > 0}">

                    <div class="pure-u-1-3" id="playlistSongs">
                        <h3>Playlist ${listName}  </h3>
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

                        <form action="ControlPlayer" method="POST">
                            <input type="submit" name="Play" value="Play" />
                            <input type="submit" name="Stop" value="Stop" />
                            <input type="submit" name="Skip" value="Skip" />
                            <input type="submit" name="Previous" value="Previous" />
                        </form>
                    </div>
                </c:if>

                <c:if test="${fn:length(listName) > 0}">

                    <div class="pure-u-1-3">
                        <h3> Upload a song to the playlist ${listName}</h3>
                        <form action="MultiFileUpload" method="POST" enctype="multipart/form-data">
                            New Song(s): <input type="file" name="file" id="file" multiple>
                            <input type="submit" name="Upload" value="Upload" />
                        </form>
                    </div>
                </c:if>
            </div> <!-- class pure-g -->
        </div>
    </div>

    <c:import url="/footer.jsp" />

</body>
</html>