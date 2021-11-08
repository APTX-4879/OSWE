<?php 

include "../include/header.php";
include "../include/isAuthenticated.php";

$id = mysqli_real_escape_string($conn, $_GET['id']);

$res = mysqli_query($conn, "DELETE FROM item WHERE id='$id'");

if($res){
    $_SESSION['status']="Item has been removed";
}else{
    $_SESSION['danger']="Failed to Deleted Item";
}
header("location: index.php");

?>