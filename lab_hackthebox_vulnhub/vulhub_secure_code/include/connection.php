<?php
$sv = "localhost";
$db = "hackshop";
$un = "hackshop";
$pw = "";
$conn = mysqli_connect($sv,$un,$pw,$db);
if($conn){
    
} else {
    echo "Failed to Connect to mysql";
}
?>