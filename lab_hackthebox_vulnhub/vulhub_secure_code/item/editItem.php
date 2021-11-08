<?php
include "../include/header.php";
include "../include/isAuthenticated.php";
    
$id = mysqli_real_escape_string($conn, $_GET['id']);
$data = mysqli_query($conn, "SELECT * FROM item WHERE id='$id'");
$result = mysqli_fetch_array($data);
?>
</head>
<body>
    <div class="container">
        <div class="col-md-3">

        </div>
            <form action="updateItem.php" method="POST" class="form-horizontal col-md-6" enctype="multipart/form-data">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <center>Edit Item</center>
                    </div>
                    <div class="panel-body">
                    <input type="hidden" name="id" value="<?php echo $id ?>">
                    <input type="hidden" name="id_user" value="<?php echo $_SESSION['id']; ?>">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Name</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control col-md-8" id="name" placeholder="Name" value="<?php echo htmlentities($result['name']); ?>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Image</label>
                            <div class="col-sm-10">
                                <input type="file" name="image" class="form-control col-md-8" id="image" placeholder="File" require>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="decription">Description</label>
                            <div class="col-sm-10">
                                <textarea type="text" name="description" class="form-control" id="description" placeholder="Enter Description"><?php echo htmlentities($result['description']); ?></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="decription">Price</label>
                            <div class="col-sm-10">
                                <input type="text" name="price" class="form-control" id="price" placeholder="Enter Price" value="<?php echo htmlentities($result['price']); ?>">
                            </div>
                        </div>
                        <input type="submit" value="Submit" class="btn btn-primary">
                    </div>
                </div>
            </form>
        <div class="col-md-3">
            
        </div>
    </div>

</body>
</html>