<?php
    include "connect.php";
    $query = "SELECT * FROM category";
    $data = mysqli_query($conn,$query);
    $categoryArr = array();
    while ($row = mysqli_fetch_assoc($data)){
        array_push($categoryArr,new category(
            $row['Id'],
            $row['Name'],
            $row['Image']
        ));
    }
    echo json_encode($categoryArr);

    class category{
        function category($id,$name,$image){
            $this->id = $id;
            $this->name = $name;
            $this->image = $image;
        }
    }
?>