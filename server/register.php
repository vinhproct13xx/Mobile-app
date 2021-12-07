
 
 <?php  
    include "connect.php";
    // unset($_GET);  
      
    if( isset($_POST['email']) && isset($_POST['password']) ) {

        $email = $_POST['email'];
        $pass  = $_POST['password'];
        $tenuser = $_POST['tenuser'];
        $diachi = $_POST['diachi'];
        $dienthoai = $_POST['dienthoai'];
        
        $query = "SELECT email FROM khachhang WHERE email = '$email'";
        $result = mysqli_query($conn, $query);
        if (mysqli_num_rows($result)>0)
        { 
           echo "error1";
    
        }
        else{
            $insert = "Insert into khachhang(tenkh, email, password, diachi, dienthoai) values('$tenuser','$email', '$pass', '$diachi', '$dienthoai')";
            $result = mysqli_query($conn, $insert);
            if ($result){
                echo "success";
            }
            else{
                echo "error2";
            }
        }
    }
 
?>