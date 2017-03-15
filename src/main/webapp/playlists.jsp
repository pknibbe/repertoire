<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->

        <h3>Select a Playlist</h3>
        <form action="ManagePlaylist" method="Post">
            <table>
                <thead><tr><th>Select</th><th>Name</th><th>Action</th></tr></thead>
                <tbody>
                   <c:forEach var="playbill" items="${playlists}">
                       <tr>
                            <td><input type="radio" name="listID" value=${playbill.id} /></td>
                            <td>${playbill.name}</td>
                           <td><select name="Action">
                               <option value="AddSong">AddSong</option>
                               <option value="Share">Share</option>
                               <option value="Delete">Delete</option>
                           </select></td>                       </tr>
                    </c:forEach>
                    <tr>
                        <td><input type="radio" name="listID" value=0 /></td>
                        <td>NewPlaylist: <input type="text" name="name"></td>
                    </tr>
                </tbody>
            </table>
            <p>   </p>
            <input type="submit" name="Enter" value="Enter" />
        </form>

    <c:import url="/footer.jsp" />


