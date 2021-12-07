<?php
include "connect.php";
$query = "SELECT * FROM product";
$data = mysqli_query($conn,$query);
$newProductArr = array();
while ($row = mysqli_fetch_assoc($data)){
    array_push($newProductArr,new NewProduct(
        $row['Id'],
        $row['Name'],
        $row['Price'],
        $row['Discount'],
        $row['Image'],
        $row['Desciption'],
        $row['Id_Category']
    ));
}
echo json_encode($newProductArr);

class NewProduct{
    function NewProduct($id,$name,$price,$discount,$image,$description,$idCategory){
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