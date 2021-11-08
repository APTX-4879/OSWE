<?php
include "../include/header.php";
include "../include/isAuthenticated.php";
$data = mysqli_query($conn, "SELECT * FROM user where id='$sid'");
$result = mysqli_fetch_array($data);
?>
</head>
<body>
    <div class="container">
    <div class="panel panel-primary">
            <div class="panel-heading">
            <center>Profile</center>
            </div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="username">Username</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" class="form-control col-md-8" id="username" placeholder="Username"value="<?php echo htmlentities($result['username']); ?>" disabled>
                        </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="username">Gender</label>
                        <div class="col-sm-10">
                            <input type="text" name="gender" class="form-control col-md-8" id="gender" placeholder="gender" value="<?php echo htmlentities($result['gender']); ?>" disabled>
                        </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="id_level">Id Level</label>
                        <div class="col-sm-10">
                            <input type="text" name="id_level" class="form-control col-md-8" id="id_level" placeholder="Id level" value="<?php echo htmlentities($result['id_level']); ?>" disabled>
                        </div>
                </div>
                <a class="btn btn-warning" href="edit.php?id=<?php echo $result['id']; ?>">EDIT</a> 
            </div>

        </div>
    </div>
</body>
</html>