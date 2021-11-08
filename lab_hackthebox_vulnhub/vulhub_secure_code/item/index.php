
    <?php
    include "../include/header.php";
    include "../include/isAuthenticated.php";
    ?>
</head>
<body>
<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            List Item
        </div>
        <div class="panel-body" align="center">
            <?php
                $data = mysqli_query($conn, "SELECT * FROM item");
                while($result= mysqli_fetch_array($data)){
                    ?>
                    <div class="col-md-2">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <?php echo htmlentities($result['name']); ?>
                            </div>
                            <div class="panel-body">
                            <?php echo "<img src='image/".htmlentities($result['imgname'])."' width='100px' height='100px'/>"; ?>
                            </div>
                            <div class="panel-footer">
                                $<?php echo htmlentities($result['price']); ?>
                            </div>
                        </div>
                        <a href="editItem.php?id=<?php echo $result['id']; ?>" class="btn btn-info">Edit</a>
                        <a href="destroy.php?id=<?php echo $result['id']; ?>" class="btn btn-danger">Delete</a>
                    </div>
                    <?php
                }
            ?>
        </div>
    </div>
    <a href="addItem.php" class="btn btn-primary">Add new item</a>
    </div>
</body>
</html>