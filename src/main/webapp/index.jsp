<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
 

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>File Upload Example</title>
    </head>
    <body>
        <form action="uploadJSP" method="post"   enctype="multipart/form-data">
            <h3>Choose files to upload.</h3>
            First name: <input type="text" name="fname"><br>
            Last name: <input type="text" name="lname"><br>
            <input name="myFile" type="file"/> <br/>
            <input name="myFile" type="file"/> <br/> <br/>
            <input value="upload" type="submit"/>
            <input type="reset"/> <br/>
        </form>
    </body>
</html>
