<?php  
require "connect.php";
 
	// mysqli_set_charset($con,'utf8');
	/** Array for JSON response*/
		$email = $_POST['email'];
		$password = $_POST['password'];

		$sql = "SELECT makh, tenkh, email FROM khachhang WHERE email = '$email' AND password= '$password'";
		$data = mysqli_query($conn,$sql);
		$arrUser = array();
		if(mysqli_num_rows($data)>0){
            while ($row = mysqli_fetch_assoc($data)){
                array_push($arrUser,new khachhang(
                    $row['makh'],
                    $row['tenkh'],
                    $row['email']
                ));
            }
            echo json_encode($arrUser);
		}
		else{
			echo "error";
		}

class khachhang{
    function khachhang($makh,$tenkh,$email){
        $this->makh = $makh;
        $this->tenkh = $tenkh;
        $this->email = $email;
    }
}
?>