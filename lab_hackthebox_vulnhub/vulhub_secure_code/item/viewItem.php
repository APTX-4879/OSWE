
<?php

// Still under development
session_start();
ini_set("display_errors", 0);
include "../include/connection.php";

// see if user is authenticated, if not then redirect to login page
if($_SESSION['id_level'] != 1){
    $_SESSION['danger'] = " You not have access to visit that page";
    header("Location: ../login/login.php");

}
// only for users with level 1 (admins)
// prevent SQL injection
$id = mysqli_real_escape_string($conn, $_GET['id']);
$data = mysqli_query($conn, "SELECT * FROM item WHERE id = $id");
//SELECT * FROM item WHERE id = $id and 1=1 
//SELECT * FROM item WHERE id = $id and 1=2
//SELECT * FROM item WHERE id = $id and ascii(SUBSTRING((SELECT token FROM user WHERE id=1), 1, 1)) > 0
//query true response 400
//query false response 200
$result = mysqli_fetch_array($data);

//var_dump($result);
if(isset($result['id'])){
    http_response_code(404);
}
?>