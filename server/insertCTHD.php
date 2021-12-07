<?php
    include "connect.php";
    $json=$_POST['json'];
    $data=json_decode($json,true);
    foreach ($data as $value){
        $mahd=$value['mahd'];
        $masp=$value['masp'];
        $soluong=$value['soluong'];
        $query="INSERT INTO cthd VALUES($mahd,$masp,$soluong)";
        $Dta=mysqli_query($conn,$query);
    }
    if ($Dta){
        echo "1";
    }else {
        echo "2";
    }
?>