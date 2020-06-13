<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
//$name=isset($_POST['u_name']) ? $_POST['u_name'] : '';
$email=isset($_POST['u_email']) ? $_POST['u_email'] : '';

if ($email !=""){   
  
    $sql="select * from Bookmark_StudioInsert where u_email = '$email'";  
    $result=mysqli_query($con,$sql);  
    $data=array();   
    if($result){  
    
      while($row=mysqli_fetch_array($result)){
        array_push($data,
          array('u_email'=>$row['u_email'],
            'location'=>$row['location'],
            'address'=>$row['address'],
            'title'=>$row['title'],
            'image'=?$row['image']
        ));
      }
      header('Content-Type: application/json; charset=utf8');
      $json = json_encode(array("BookmarkTheme"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
      echo $json;
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
         Email: <input type = "text" name = "u_email" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>
