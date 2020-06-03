<?php 
    error_reporting(E_ALL); 
    ini_set('display_errors',1); 
    $con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');

    putenv("LANG=ko_KR.UTF-8");
    setlocale(LC_ALL, 'ko_KR.utf8');
        
    $stmt = $con->prepare('select * from Review_data');
    $stmt->execute();

    if ($stmt->rowCount() > 0)
    {
        $data = array(); 

        while($row=$stmt->fetch(PDO::FETCH_ASSOC))
        {
            extract($row);
            array_push($data, 
                array('hotel'=>$hotel,'sight'=>$sight,'eat'=>$eat,'place'=>$place,'rating'=>$rating,'spec'=>$spec)
            );
        }

        header('Content-Type: application/json; charset=utf8');
        $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
        echo $json;
    }

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