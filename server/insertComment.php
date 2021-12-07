<?php
    include "connect.php";
    $makh=$_POST['makh'];
    $masp=$_POST['masp'];
    $content=$_POST['content'];

    if(strlen($content)>0){
        $query="INSERT INTO comment(makh,masp,content) VALUES($makh,$masp,'$content')";
        if(mysqli_query($conn,$query)){
            echo "1";
        }else{
            echo "0";
        }
    }else{
        echo "Bạn hãy kiểm tra lại các dữ liệu";
    }
?>