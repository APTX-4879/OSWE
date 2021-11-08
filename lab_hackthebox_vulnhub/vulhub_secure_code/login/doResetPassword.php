<?php

include "../include/header.php";

$p_token = $_GET['token'];

$data = mysqli_query($conn, "SELECT * FROM user");
$tokens = [];
while($result = mysqli_fetch_array($data)){
    array_push($tokens, $result['token']);
}

if(ctype_alnum($p_token) AND in_array($p_token, $tokens)){

?>

<div class="container">
        <br><br><br><br><br>
        <div class="alert alert-success  fade in">    
            <strong>Success! </strong> Valid Token Provided, you can change your password below <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">×</span>
            </button>
        </div>
        <div class="col-md-3">

        </div>
            <form action="doChangePassword.php" method="POST" class="form-horizontal col-md-6">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <center>Change Your Password</center>
                    </div>
                    <div class="panel-body">
                        <input type="hidden" name="token" value="<?php echo htmlentities($p_token); ?>">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="password">New Password</label>
                            <div class="col-sm-10">
                                <input type="password" name="password" class="form-control" id="password" placeholder="Enter password">
                            </div>
                            
                        </div>
                        <input type="submit" value="Change Password" class="btn btn-primary">
                    </div>
                </div>
            </form>
        <div class="col-md-3">
            
        </div>
    </div>

<?php 

}else{

    $_SESSION['danger'] = " Invalid password reset link.";
    header("Location: resetPassword.php");
    die();

}




?>