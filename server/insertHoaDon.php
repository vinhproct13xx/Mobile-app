<?php
    include "connect.php";
    $makh=$_POST['makh'];
    $tongtien=$_POST['tongtien'];
    $nguoinhan=$_POST['nguoinhan'];
    $sodienthoai=$_POST['sodienthoai'];
    $diachigiao=$_POST['diachigiao'];
    if(strlen($makh)>0&&strlen(@$tongtien)>0&&strlen($nguoinhan)>0&&strlen($sodienthoai)>0&&strlen($diachigiao)>0){
        $query="INSERT INTO hoadon VALUES(null,$makh,$tongtien,'$nguoinhan','$sodienthoai','$diachigiao',CURRENT_DATE )";
        if(mysqli_query($conn,$query)){
            $mahd=$conn->insert_id;//lấy mahd vừa insert
            echo $mahd;
        }else{
            echo "Thất bại";
        }
    }else{
        echo "Bạn hãy kiểm tra lại các dữ liệu";
    }
?>