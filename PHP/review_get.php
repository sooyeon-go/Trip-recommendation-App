<?php 

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');

mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
$sql="select * from Review_data";
$result=mysqli_query($con,$sql);
$data=array();
if($result){

  while($row=mysqli_fetch_array($result)){
    array_push($data, 
    array('hotel'=>$row['hotel']
          ,'sight'=>$row['sight']
          ,'eat'=>$row['eat']
          ,'place'=>$row['place']
          ,'rating'=>$row['rating']
          ,'spec'=>$row['spec']
         ));
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
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
         hotel: <input type = "text" name = "review" />
         sight: <input type = "text" name = "sight" />
         eat: <input type = "text" name = "eat" />
         place: <input type = "text" name = "place" />
         rating: <input type = "text" name = "rating" />
         spec: <input type = "text" name = "spec" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>