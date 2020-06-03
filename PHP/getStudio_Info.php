<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}

$sql="SELECT DISTINCT * FROM Studio_Info si GROUP BY work_title";
$result=mysqli_query($con,$sql);
$data=array();
if($result){

  while($row=mysqli_fetch_array($result)){
    array_push($data,
    array('work_title'=>$row['work_title'],
            'location'=>$row['location'],
            'address'=>$row['address'],
            'scene'=>$row['work_nm'],
            'img'=>$row['img']
    ));
  }
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("Studio_Info"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
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