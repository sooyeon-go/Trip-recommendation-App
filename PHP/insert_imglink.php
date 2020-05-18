<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$email=isset($_POST['u_email']) ? $_POST['u_email'] : ''; 
$link=isset($_POST['link']) ? $_POST['link'] : ''; 




if ($email !="" and $link !=""){   
  
    $sql="insert into ImgLink (u_email,link)
    values ('$email','$link')";  
    
    $result=mysqli_query($con,$sql);  

    if($result){  
       echo "결과를 추가했습니다.";  
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
<?php

$android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

if (!$android){
?>

<html>
   <body>
   
      <form action="<?php $_PHP_SELF ?>" method="POST">
         email: <input type = "text" name = "u_email" />
         link: <input type = "text" name = "link" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>