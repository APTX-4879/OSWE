<?php 

include "../include/header.php";
include "../include/isAuthenticated.php";

$id_user = mysqli_real_escape_string($conn, $_POST['id_user']);
$name = mysqli_real_escape_string($conn, $_POST['name']);
$imgname = mysqli_real_escape_string($conn, $_FILES['image']['name']);
$description = mysqli_real_escape_string($conn, $_POST['description']);
$price = mysqli_real_escape_string($conn, $_POST['price']);

$blacklisted_exts = array("php", "phtml", "shtml", "cgi", "pl", "php3", "php4", "php5", "php6");
$mimes = array("image/jpeg", "image/png", "image/gif");

if(isset($id_user, $name, $imgname, $description, $price)){

    $ext = strtolower(pathinfo($_FILES['image']['name'])['extension']);
    $mime = mime_content_type($_FILES['image']['tmp_name']);
    if(!in_array($ext, $blacklisted_exts) AND in_array($mime, $mimes)){

        $up = move_uploaded_file($_FILES['image']['tmp_name'], "image/".$_FILES['image']['name']);
        $res = mysqli_query($conn,"INSERT INTO item VALUES('','$id_user','$name','$description','$imgname','$price')");
        if($res == true AND $up == true){
            $_SESSION['status'] = " Item data has been Added";
        }else{
            $_SESSION['danger'] = " Failed to add Item";
        }
        header("Location: index.php");

    }else{
        $_SESSION['danger'] = " This file is not allowed.";
        header("Location: index.php");
    }

}else{
    $_SESSION['danger'] = " Some Fields are missing.";
    header("Location: index.php");
}
?>