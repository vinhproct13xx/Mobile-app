<?php
include "connect.php";
$makh=$_POST['makh'];
$tenkh=$_POST['tenkh'];
$dienthoai=$_POST['dienthoai'];
$diachi=$_POST['diachi'];
if (strlen($makh)>0&&strlen($tenkh)&&strlen($dienthoai)&&strlen($diachi)){
    $query="UPDATE khachhang SET tenkh='$tenkh',dienthoai='$dienthoai',diachi='$diachi' WHERE makh=$makh";
    if(mysqli_query($conn,$query))
        echo "Thành công";
    else echo "Thất bại";
}else{
    echo "Bạn hãy kiểm tra lại các dữ liệu";
}
?>