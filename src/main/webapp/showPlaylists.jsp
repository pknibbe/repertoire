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

        <form class="pure-form pure-form-stacked" action="PlaylistAction" method="Post">
            <fieldset>
                <p></p><p></p>
                <legend>My Playlists</legend>

                <p>________________________________________</p>

                <div class=pure-g">

                    <div class="pure-u-1 pure-u-md-1-5">List Name</div>
                    <div class="pure-u-1 pure-u-md-1-5">List Owner</div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>


                    <c:forEach var="playbill" items="${myPlaylists}">
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.name}</div>
                        <div class="pure-u-1 pure-u-md-1-5">Me</div>

                        <div class="pure-u-1 pure-u-md-1-8">
                            <input class="pure-input-1" type="submit" name="delete${playbill.playlist_id}" value="delete" />
                        </div>

                        <div class="pure-u-1 pure-u-md-1-8">
                            <input class="pure-input-1" type="submit" name="manage${playbill.playlist_id}" value="manage" />
                        </div>

                        <c:if test="${isPlaying == false}">
                            <div class="pure-u-1 pure-u-md-1-12">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.playlist_id}" value="Play" />
                            </div>
                        </c:if>
                        <c:if test="${isPlaying == true}">
                            <div class="pure-u-1 pure-u-md-1-12">
                                <p></p>
                            </div>
                        </c:if>
                        <div class="pure-u-1 pure-u-md-1-6">
                        </div>
                    </c:forEach>


                    <c:forEach var="playbill" items="${receivedPlaylists}">
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.name}</div>
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.owner.name}</div>

                        <c:if test="${isPlaying == false}">
                            <div class="pure-u-1 pure-u-md-1-12">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.playlist_id}" value="Play" />
                            </div>
                        </c:if>
                        <c:if test="${isPlaying == true}">
                            <div class="pure-u-1 pure-u-md-1-12">
                                <p></p>
                            </div>
                        </c:if>
                        <div class="pure-u-1 pure-u-md-1-2">
                            <p></p>
                        </div>
                    </c:forEach>

                    <p></p>
                    <h2>Create a new List</h2>
                    <div class="pure-u-1 pure-u-md-1-2">
                        Name of new list:
                        <input class="pure-input-1" id="listname" type="text" name="listname" placeholder="NewList">
                    </div>
                    <div class="pure-u-1 pure-u-md-1-8"><p></p>
                        <input class="pure-input-1" type="submit" name="create" value="create" />
                    </div>
                    <div class="pure-u-1 pure-u-md-1-3">
                        <p></p>
                    </div>
                    <div class="pure-u-1 pure-u-md-1-8"><p></p>
                        <c:if test="${isPlaying == true}">
                            <div class="pure-u-1 pure-u-md-1-4">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.playlist_id}" value="Stop" />
                            </div>
                        </c:if>
                    </div>
                </div>  <!-- class pure-g -->
            </fieldset>
        </form>
    </div>

    <c:import url="/footer.jsp" />

</body>
</html>