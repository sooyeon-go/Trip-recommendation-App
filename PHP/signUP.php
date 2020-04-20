<?php

$con=mysqli_connect("ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com","admin","-Tkehfdk-","test");


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}

$username = $_POST['Name'];
$userage = $_POST['Age'];
$usersex = $_POST['Sex'];
$useremail = $_POST['Email'];
$userpw = $_POST['Pw'];

$result = mysqli_query($con,"insert into User_Info (u_name,age,sex,u_email,u_pw) values ('$userid','$userage','$usersex','$useremail',$userpw')");
  if($result){

    echo 'success';

  }

  else{

    echo 'failure';

  }
mysqli_close($con);

?>