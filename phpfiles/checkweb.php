<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "datametrics";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 

$deviceid = $_POST["deviceid"];  
$datenow = $_POST["datenow"];  
$website = $_POST["website"];  
$datausage = $_POST["datausage"];
$month = $_POST["month"];
$year = $_POST["year"];
$sql = "SELECT  * FROM mytable WHERE deviceid = '$deviceid' AND website = '$website'" ;
$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result) >0){
	$sql = "UPDATE mytable SET datausage = (datausage + '$datausage') WHERE deviceid = '$deviceid' AND website = '$website'";
if ($conn->query($sql) === TRUE) {
	
    echo "Record updated successfully";
	}
}
else
{
	$sql = "INSERT INTO mytable (deviceid, datenow, website, datausage, month, year)
VALUES ('$deviceid','$datenow','$website','$datausage','$month','$year')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
}

	
	
    
	

mysqli_close($conn);

?>