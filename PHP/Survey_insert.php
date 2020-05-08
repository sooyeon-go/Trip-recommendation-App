<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$Q1=isset($_POST['Q1']) ? $_POST['Q1'] : ''; 
$Q2=isset($_POST['Q2']) ? $_POST['Q2'] : ''; 
$Q3=isset($_POST['Q3']) ? $_POST['Q3'] : ''; 
$Q4=isset($_POST['Q4']) ? $_POST['Q4'] : ''; 
$Q5=isset($_POST['Q5']) ? $_POST['Q5'] : ''; 
$Q6=isset($_POST['Q6']) ? $_POST['Q6'] : ''; 
$Q7=isset($_POST['Q7']) ? $_POST['Q7'] : ''; 
$Q8=isset($_POST['Q8']) ? $_POST['Q8'] : ''; 



if ($Q1 !="" and $Q2 !="" and $Q3 !="" and $Q4 !="" and $Q5 !=""){   
  
    $sql="insert into Survey_Result (Q1,Q2,Q3,Q4,Q5,Q6,Q7,Q8)
    values ('$Q1','$Q2','$Q3','$Q4','$Q5','$Q6','$Q7','$Q8')";  
    
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
         Q1: <input type = "text" name = "Q1" />
         Q2: <input type = "text" name = "Q2" />
         Q3: <input type = "text" name = "Q3" />
         Q4: <input type = "text" name = "Q4" />
         Q5: <input type = "text" name = "Q5" />
         Q6: <input type = "text" name = "Q6" />
         Q7: <input type = "text" name = "Q7" />
         Q8: <input type = "text" name = "Q8" />
         <input type = "submit" />
      </form>
   
   </body>
</html>
<?php
}

   
?>