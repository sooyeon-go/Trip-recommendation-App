<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$name=isset($_POST['Name']) ? $_POST['Name'] : ''; 
$age=isset($_POST['Age']) ? $_POST['Age'] : '';  
$sex=isset($_POST['Sex']) ? $_POST['Sex'] : ''; 
$email=isset($_POST['Email']) ? $_POST['Email'] : '';
$pw=isset($_POST['Pw']) ? $_POST['Pw'] : '';

if ($name !="" and $age !="" and $sex !="" and $email !="" and $pw !=""){   
  
    $sql="insert into User_Info (u_name,age,sex,u_email,u_pw) values ('$name','$age','$sex','$email','$pw')";  
    $result=mysqli_query($con,$sql);  

    if($result){  
       echo "'$email'을 추가했습니다.";  
    }  
    else{  
       echo "SQL문 처리중 에러 발생 : "; 
       echo mysqli_error($con);
    } 
 
} else {
    echo "데이터를 입력하세요 ";
}
mysqli_close($con);

?>