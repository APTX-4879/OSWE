<?php 

include "../include/header.php";
include "../include/isAuthenticated.php";

$id = mysqli_real_escape_string($conn, $sid);
$username = mysqli_real_escape_string($conn, $_POST['username']);
$gender = mysqli_real_escape_string($conn, $_POST['gender']);

mysqli_query($conn,"UPDATE user SET username='$username', gender='$gender' WHERE id='$id'");

header("location: index.php");

?>