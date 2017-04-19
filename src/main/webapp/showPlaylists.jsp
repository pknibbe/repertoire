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

                <div class=pure-g">

                    <div class="pure-u-1 pure-u-md-1-5">List Name</div>
                    <div class="pure-u-1 pure-u-md-1-5">List Owner</div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>
                    <div class="pure-u-1 pure-u-md-1-5"></div>


                    <c:forEach var="playbill" items="${playlists}">
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.name}</div>
                        <div class="pure-u-1 pure-u-md-1-5">${playbill.owner_name}</div>

                        <c:if test="${playbill.playerState != 'playing'}">
                            <div class="pure-u-1 pure-u-md-1-5">
                                <input class="pure-input-1" type="submit" name="toggle${playbill.id}" value="Play" />
                            </div>
                        </c:if>
                        <c:if test="${playbill.playerState == 'playing'}">
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
    </div>

    <c:import url="/footer.jsp" />

</body>
</html>