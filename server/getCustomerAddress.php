<?php
include "connect.php";
$makh=$_POST['makh'];
$customerArray=array();
$query="SELECT * FROM khachhang WHERE makh=$makh";
$data = mysqli_query($conn,$query);
while ($row = mysqli_fetch_assoc($data)) {
    array_push($customerArray, new CustomerAddress(
        $row['makh'],
        $row['tenkh'],
        $row['dienthoai'],
        $row['diachi']
    ));
}
echo json_encode($customerArray);

class CustomerAddress
{
    function CustomerAddress($makh,$tenkh,$dienthoai,$diachi){
        $this->makh=$makh;
        $this->tenkh=$tenkh;
        $this->dienthoai=$dienthoai;
        $this->diachi=$diachi;
}
}
?>