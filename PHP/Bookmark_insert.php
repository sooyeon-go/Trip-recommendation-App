<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$u_email=isset($_POST['u_email']) ? $_POST['u_email'] : ''; 
$title=isset($_POST['title']) ? $_POST['title'] : ''; 
$scene=isset($_POST['scene']) ? $_POST['scene'] : ''; 
$location=isset($_POST['location']) ? $_POST['location'] : ''; 
$address=isset($_POST['address']) ? $_POST['address'] : ''; 
$image=isset($_POST['image']) ? $_POST['image'] : ''; 



if ($u_email !="" and $title !="" and $scene !="" and $location !="" and $address !=""){   
  
    $sql="insert into Bookmark_StudioInsert
    values ('$u_email','$scene','$location','$address','$title','$image')";  
    
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
         u_email: <input type = "text" name = "u_email" />
         title: <input type = "text" name = "title" />
         scene: <input type = "text" name = "scene" />
         location: <input type = "text" name = "location" />
         address: <input type = "text" name = "address" />
         image: <input type = "text" name = "image" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}
   
?>