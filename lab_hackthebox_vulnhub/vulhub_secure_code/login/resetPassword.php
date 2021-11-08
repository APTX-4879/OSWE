<?php

include "../include/header.php";
$username = mysqli_real_escape_string($conn, @$_POST['username']);

if(isset($username) and ctype_alnum($username)){

    $data = mysqli_query($conn, "SELECT * FROM user");
    $users = [];
    while($result= mysqli_fetch_array($data)){
        array_push($users, $result['username']);
    }

    if(in_array($username, $users)){

        $token = generateToken();
        mysqli_query($conn,"UPDATE user SET token = '$token' WHERE username = '$username'");
        send_email($username, $token);
        $_SESSION['status']=" Password Reset Link has been sent to you via Email, please check it out.";    
        header("location: login.php");
        die();

    }else{

        $_SESSION['danger']=" Username not found.";
        header("location: resetPassword.php");
        die();

    }

}else{

?>

<div class="container">
        <br><br><br><br><br>
        <div class="col-md-3">

        </div>
            <form action="resetPassword.php" method="POST" class="form-horizontal col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <center>Reset your Password</center>
                    </div>
                    <div class="panel-body">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="username">Username</label>
                            <div class="col-sm-10">
                                <input type="text" name="username" class="form-control col-md-8" id="username" placeholder="Username">
                            </div>
                        </div>
                        <input type="submit" value="Submit" class="btn btn-primary">
                    </div>
                </div>
            </form>
        <div class="col-md-3">

<?php }


function generateToken(){
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < 15; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

function send_email($username, $token){
    
    $message = "Hello ".htmlentities($username).",\n";
    $message .= "Please follow the link below to reset your password: \n";
    $message .= "http://".gethostname()."/doResetPassword.php?token=$token \n";
    $message .= "Thanks.\n";

    // get user email
    $data = mysqli_query($conn, "SELECT * FROM user WHERE username='$username'");
    while($result= mysqli_fetch_array($data)){
        $email = $result['email'];
    }
    @mail($email, "Reset Your Password", $message);

}

?>