<?php
    
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
    $con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


    mysqli_set_charset($con,"utf8");

    if (mysqli_connect_errno($con))

    {

       echo "Failed to connect to MySQL: " . mysqli_connect_error();

    }
    $hotel = isset($_POST['hotel']) ? $_POST['hotel'] : ''; 
    $sight = isset($_POST['sight']) ? $_POST['sight'] : ''; 
    $eat = isset($_POST['eat']) ? $_POST['eat'] : ''; 
    $place = isset($_POST['place']) ? $_POST['place'] : ''; 
    $rating = isset($_POST['rating']) ? $_POST['rating'] : ''; 
    $spec = isset($_POST['spec']) ? $_POST['spec'] : ''; 

    if ($hotel !="" and $sight !="" and $eat !="" and $place !="" and $rating !="" and $spec !=""){   
  
        $sql = "INSERT INTO Review_data(hotel, sight, eat, place, rating, spec) 
        VALUES ('$hotel', '$sight', '$eat', '$place', '$rating', '$spec')";
    
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