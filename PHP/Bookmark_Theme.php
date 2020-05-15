<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$u_email=isset($_POST['u_email']) ? $_POST['u_email'] : ''; 
$theme=isset($_POST['theme']) ? $_POST['theme'] : '';  
$addr=isset($_POST['addr']) ? $_POST['addr'] : ''; 
$work_nm=isset($_POST['work_nm']) ? $_POST['work_nm'] : '';


if ($u_email !="" and $theme !=""){   
  
    $sql="insert into BookmarkTheme (u_id,theme,addr,work_nm) values ('$u_email','$theme','$addr','$work_nm')";  
    $result=mysqli_query($con,$sql);  

    if($result){  
       echo "'$u_id'의 보관함에 추가했습니다.";  
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
         u_email: <input type = "text" name = "u_email" />
         theme: <input type = "text" name = "theme" />
         addr: <input type = "text" name = "addr" />
         work_nm: <input type = "text" name = "work_nm" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>