<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/head.jsp" /> <!-- opens body and container -->
<div id="container">

    <form action="MultiFileUpload" method="POST" enctype="multipart/form-data">
        New Song(s): <input type="file" name="file" id="file" multiple>
        <input type="submit" name="Upload" value="Upload" />
    </form>
</div>
<c:import url="/footer.jsp" />


