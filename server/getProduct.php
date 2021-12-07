<?php
include "connect.php";
$page = $_GET['page'];
$idCategory = $_POST['idcategory'];
$space = 4;
$limit = ($page - 1)*$space;
$productArr = array();

$query = "SELECT * FROM product WHERE Id_Category = $idCategory LIMIT $limit,$space";
$data = mysqli_query($conn,$query);
while ($row = mysqli_fetch_assoc($data)){
    array_push($productArr,new Product(
        $row['Id'],
        $row['Name'],
        $row['Price'],
        $row['Discount'],
        $row['Image'],
        $row['Desciption'],
        $row['Id_Category']
    ));
}
echo json_encode($productArr);

class Product{
    function Product($id,$name,$price,$discount,$image,$description,$idCategory){
        $this->id = $id;
        $this->name = $name;
        $this->price = $price;
        $this->discount = $discount;
        $this->image = $image;
        $this->description = $description;
        $this->idCategory = $idCategory;
    }
}
?>