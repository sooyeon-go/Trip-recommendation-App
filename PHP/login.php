<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$email=isset($_POST['Email']) ? $_POST['Email'] : '';
$pw=isset($_POST['Pw']) ? $_POST['Pw'] : '';

if ($email !="" and $pw !=""){   
  
    $sql="select if(strcmp(u_pw,'$pw'),0,1) as pw_chk, u_name,age,sex,u_email from User_Info where u_email = '$email'";  "select if(strcmp(u_pw,'$pw'),0,1) as pw_chk from User_Info where u_email = '$email'";  
    $result=mysqli_query($con,$sql);  
    $data=array();   
    if($result){  
    
      while($row=mysqli_fetch_array($result)){
        array_push($data,
          array('pw_chk'=>$row['pw_chk']
        ));
      }
      header('Content-Type: application/json; charset=utf8');
      $json = json_encode(array("User_Info"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
      echo $json;
    }  
    // if($result){  
    //    $row = mysql_fetch_array($result);
    //    if(is_null($row[pw_chk])){
    //       echo "없는 회원 정보입니다.";
    //    } 
    //    else{
    //     echo "$row[pw_chk]";
    //    }
    // }  
    // else{  
    //    echo "SQL문 처리중 에러 발생 : "; 
    //    echo mysqli_error($con);
    // }  
} else {
    echo "데이터를 입력하세요 ";
}
mysqli_close($con);

?>
<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         Email: <input type = "text" name = "Email" />
         Pw: <input type = "text" name = "Pw" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>
