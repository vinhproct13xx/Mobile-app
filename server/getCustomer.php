<?php
include "connect.php";
$query="SELECT * FROM khachhang WHERE makh=1";
$data = mysqli_query($conn,$query);
$CustomerArr = array();
while ($row = mysqli_fetch_assoc($data)) {
    array_push($CustomerArr,new Customer(
        $row['makh'],
        $row['tenkh'],
        $row['dienthoai'],
        $row['diachi']
    ));
}
echo json_encode($CustomerArr);
class Customer
{
    function Customer($makh,$tenkh,$dienthoai,$diachi){
        $this->makh=$makh;
        $this->tenkh=$tenkh;
        $this->dienthoai=$dienthoai;
        $this->diachi=$diachi;
    }
}
?>