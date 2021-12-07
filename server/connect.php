<?php
    $host = "localhost";
    $userName = "root";
    $password ="";
    $database = "appbanhang";

    $conn = mysqli_connect($host,$userName,$password,$database);
    mysqli_query($conn,"SET NAMES 'utf8'");
?>