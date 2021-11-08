<?php
    include "../include/header.php";
    include "../include/isAuthenticated.php";
?>
</head>
<body>
    <div class="container">
        <div class="col-md-3">

        </div>
            <form action="newItem.php" method="post" class="form-horizontal col-md-6" enctype="multipart/form-data">
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <center>Add New Item</center>
                    </div>
                    <div class="panel-body">
                    <input type="hidden" name="id_user" value="<?php echo $_SESSION['id']; ?>">
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Name</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control col-md-8" id="name" placeholder="name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="name">Image</label>
                            <div class="col-sm-10">
                                <input type="file" name="image" class="form-control col-md-8" id="image" placeholder="your file name">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="decription">Description</label>
                            <div class="col-sm-10">
                                <textarea type="text" name="description" class="form-control" id="description" placeholder="Enter description"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="decription">Price</label>
                            <div class="col-sm-10">
                                <input type="text" name="price" class="form-control" id="price" placeholder="Enter price">
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