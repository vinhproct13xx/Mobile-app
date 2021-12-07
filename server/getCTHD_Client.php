<?php
include "connect.php";
$mahd=$_POST['mahd'];
$cthdArray=array();
$query="SELECT masp,p.Name,p.Price,p.Discount,p.Image,c.soluong from cthd c, product p where c.mahd=$mahd and c.masp=p.Id";
$data = mysqli_query($conn,$query);
while ($row = mysqli_fetch_assoc($data)) {
    array_push($cthdArray, new CTHD_Client(
        $row['masp'],
        $row['Name'],
        $row['Price'],
        $row['Discount'],
        $row['Image'],
        $row['soluong']
    ));
}
echo json_encode($cthdArray);


class CTHD_Client
{
    function CTHD_Client($masp,$name,$price,$discount,$image,$soluong){
        $this->masp=$masp;
        $this->Name=$name;
        $this->Price=$price;
        $this->Discount = $discount;
        $this->Image=$image;
        $this->soluong=$soluong;
    }
}