<?php 
include "../include/header.php";
include "../include/isAuthenticated.php";

$id_level = mysqli_real_escape_string($conn, $_POST['id_level']);
$username = mysqli_real_escape_string($conn, $_POST['username']);
$gender = mysqli_real_escape_string($conn, $_POST['gender']);
$password = md5($_POST['password']);



$res = mysqli_query($conn,"INSERT INTO user VALUES('','$username','$password','$gender','$id_level')");
if($res){
    $_SESSION['status']="New Employee has been added";
}else{
    $_SESSION['danger']="Failed to add new employee";
}

header("location: index.php");

?>