<?php
include "connect.php";
$makh=$_POST['makh'];
$billlist=array();
$query="SELECT * FROM hoadon WHERE makh=$makh ORDER by ngaythanhtoan DESC";
$data = mysqli_query($conn,$query);
while ($row = mysqli_fetch_assoc($data)) {
    array_push($billlist, new BillList(
        $row['mahd'],
        $row['tongtien'],
        $row['ngaythanhtoan']
    ));
}
echo json_encode($billlist);
class BillList
{
    function BillList($mahd,$tongtien,$ngaythanhtoan){
        $this->mahd=$mahd;
        $this->tongtien=$tongtien;
        $this->ngaythanhtoan=$ngaythanhtoan;
    }
}
?>