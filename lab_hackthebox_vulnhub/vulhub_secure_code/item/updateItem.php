<?php 

include "../include/header.php";
include "../include/isAuthenticated.php";

$id = mysqli_real_escape_string($conn, $_POST['id']);
$id_user = mysqli_real_escape_string($conn, $_POST['id_user']);
$name = mysqli_real_escape_string($conn, $_POST['name']);
$imgname = mysqli_real_escape_string($conn, $_FILES['image']['name']);
$description = mysqli_real_escape_string($conn, $_POST['description']);
$price = mysqli_real_escape_string($conn, $_POST['price']);

$blacklisted_exts = array("php", "phtml", "shtml", "cgi", "pl", "php3", "php4", "php5", "php6");

if(isset($id, $id_user, $name, $imgname, $description, $price)){

    $ext = strtolower(pathinfo($imgname)['extension']);
    if(!in_array($ext, $blacklisted_exts)){
        $up = move_uploaded_file($_FILES['image']['tmp_name'], "image/".$imgname);
        $res = mysqli_query($conn, "UPDATE item SET name='$name', imgname='$imgname', description='$description',price='$price' WHERE id='$id'");
        if($res == true AND $up == true){
            $_SESSION['status']=" Item data has been edited";
        }else{
            $_SESSION['danger']=" Failed to edit Item";
        }
        header("Location: index.php");
        die();
    
    }else{
        $_SESSION['danger']=" File is not accepted.";
        header("Location: index.php");
        die();
    }

}else{
    $_SESSION['danger']=" Some Fields are missing.";
    header("Location: index.php");
}

?>