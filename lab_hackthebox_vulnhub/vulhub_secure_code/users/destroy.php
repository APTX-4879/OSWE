<?php 

include "../include/header.php";
include "../include/isAuthenticated.php";
$id = mysqli_real_escape_string($conn, $_GET['id']);

$res = mysqli_query($conn,"DELETE FROM user WHERE id='$id'");
if($res){
    $_SESSION['status']="Employee has been removed";
}else{
    $_SESSION['danger']="Failed to remove Employee";
}
header("location: index.php");

?>