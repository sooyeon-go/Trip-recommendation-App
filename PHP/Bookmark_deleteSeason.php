<?php

$con=mysqli_connect('ssadola.caaqegau8sq4.ap-northeast-2.rds.amazonaws.com','admin','-Tkehfdk-','test','3306');


mysqli_set_charset($con,"utf8");

if (mysqli_connect_errno($con))

{

   echo "Failed to connect to MySQL: " . mysqli_connect_error();

}
//POST 값을 읽어온다.
$u_email=isset($_POST['u_email']) ? $_POST['u_email'] : '';
$name=isset($_POST['f_name']) ? $_POST['f_name'] : '';
$season=isset($_POST['f_season']) ? $_POST['f_season'] : '';
$address=isset($_POST['f_addr']) ? $_POST['f_addr'] : '';




if ($u_email !="" and $name !="" and $season !="" and $address !=""){

    $sql="delete from Bookmark_SeasonInsert
        where u_email = '$u_email' and f_name = '$name' and f_season = '$season'";

    $result=mysqli_query($con,$sql);

    if($result){
       echo "보관함에서 삭제했습니다.";
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
         name: <input type = "text" name = "f_name" />
         addr: <input type = "text" name = "f_addr" />
         season : <input type = "text" name = "f_season" />
         <input type = "submit" />
      </form>

   </body>
</html>
<?php
}

?>