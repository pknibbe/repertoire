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

    <div id="container">

        <form class="pure-form pure-form-stacked" action="AnalyzeRequest" method="Get">
            <fieldset>
                <p></p><p></p>
                <legend>My Playlists</legend>

                <div class=pure-g">

                    <div class="pure-u-1 pure-u-md-1-5">List Name</div>
                    <div class="pure-u-1 pure-u-md-1-5">List Owner</div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>


                    <c:forEach var="playbill" items="${playlists}">
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.name}</div>
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.owner_name}</div>

                        <c:if test="${playbill.playing == 0}">
                            <div class="pure-u-1 pure-u-md-1-5">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.id}" value="Play" />
                            </div>
                        </c:if>
                        <c:if test="${playbill.playing == 1}">
                            <div class="pure-u-1 pure-u-md-1-5">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.id}" value="Stop" />
                            </div>
                        </c:if>

                        <div class="pure-u-1 pure-u-md-1-5">
                            <input class="pure-input-1" type="submit" name="manage${playbill.id}" value="manage" />
                        </div>
                    </c:forEach>

                    <div class="pure-u-1 pure-u-md-1-4">
                        <input class="pure-input-1" id="listname" type="text" name="listname" placeholder="NewList">
                    </div>
                    <div class="pure-u-1 pure-u-md-1-4">Me</div>
                    <div class="pure-u-1 pure-u-md-1-4">
                        <input class="pure-input-1" type="submit" name="create" value="create" />
                    </div>

                </div>
            </fieldset>
        </form>

  <!--              <c:if test="${songCount > 0}">

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

    <c:import url="/footer.jsp" />

</body>
</html>