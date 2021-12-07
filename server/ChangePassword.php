<?php  
require "connect.php";
 
	// mysqli_set_charset($con,'utf8');
	/** Array for JSON response*/
	$response = array();

	$oldpass = $_POST['oldpass'];
	// $oldpass = 1;
	$newpass = $_POST['newpass'];
	// $newpass = 2;
	$email = $_POST['email'];

	// $email = 'a@gmail.com';
	/*echo "oldpass".$oldpass;
	echo "newpass".$newpass;
	echo "email".$email;*/

	$sql = "SELECT makh FROM khachhang WHERE email='$email' AND password= '$oldpass'";

	$data = mysqli_query($conn,$sql);
	if(mysqli_num_rows($data)> 0){
		$update = "update khachhang set password = '$newpass' where email = '$email'";
		//echo "".$update;
		if(mysqli_query($conn, $update))
		{
			echo "success";
		}

	}
	else{
		echo "error";
	}
?>