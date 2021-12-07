<?php
include "connect.php";

$masp = $_GET['masp'];

$commentArr = array();

$query = "SELECT email,content FROM comment,khachhang WHERE comment.makh = khachhang.makh and masp = $masp";
$data = mysqli_query($conn,$query);
while ($row = mysqli_fetch_assoc($data)){
    array_push($commentArr,new Comment(
        $row['email'],
        $row['content']
    ));
}
echo json_encode($commentArr);

class Comment{
    function Comment($username, $content){
        $this->username = $username;
        $this->content = $content;
    }
}
?>