<?php 
include "../include/header.php";
include "../include/isAuthenticated.php";

$id = mysqli_real_escape_string($conn, $_POST['id']);
$username = mysqli_real_escape_string($conn, $_POST['username']);
$gender = mysqli_real_escape_string($conn, $_POST['gender']);

$res = mysqli_query($conn,"UPDATE user SET username='$username', gender='$gender' WHERE id='$id'");

if($res){
    $_SESSION['status']="Employee data has been edited";
}else{
    $_SESSION['danger']="Failed to edit employee data";
}
header("location: index.php");

?>