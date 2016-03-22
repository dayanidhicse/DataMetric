<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<title>Data Metrics</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript" src="qrcode.js"></script>

<style>
@import url(http://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);

body {
  
  background:#ffffff;
  font-family: "Roboto", helvetica, arial, sans-serif;
  font-size: 16px;
  font-weight: 400;
  text-rendering: optimizeLegibility;
}
	div.table-title {
	   display: block;
	  margin: auto;
	  max-width: 600px;
	  padding:5px;
	  width: 100%;
	}
	
	.table-title h3 {
	   color: #fafafa;
	   font-size: 30px;
	   font-weight: 400;
	   font-style:normal;
	   font-family: "Roboto", helvetica, arial, sans-serif;
	   text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);
	   text-transform:uppercase;
	   
	   
	}

</style>

</head>
<body>

<div style="-webkit-box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);
-moz-box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);
box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);background-color: #3e94ec;width:100%;height:250px;" >
<br></br><br></br>
<div class="table-title" > 
<div style="display:inline;">
<img alt="Juspay" src="juspay_icon.png" width="100px" style="float: left;"height="100px"/>
 <h3 >JusPay Data Metrics</h3>
</div>
</div></div><br/>
<div style="-webkit-box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);
-moz-box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);
box-shadow: 0px 0px 2px 0px rgba(0,0,0,1);background-color: #3e94ec;width:100%;height:500px;" >
<br></br>
<div id="qrcode"  style="margin-left: 3cm;width:100px; height:100px; margin-top:15px; ">
</div>
<div style="float:right;margin-right: 3cm;text-align: center;">
<h3 style=" color: #fafafa;font-style:normal;"><font size="8">Juspay Data Metrics</font></h3>
<br>
</br>
<p  style=" color: #fafafa;"><font size="3">Use Juspay Data Metrics on your phone to scan the code</font></p>
</div>
</div>
<script src="http://cdn.firebase.com/js/client/1.0.6/firebase.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>    
<script type="text/javascript">
var daya="<?php echo uniqid(); ?>";

var fireBaseRef = new Firebase("https://sizzling-inferno-6307.firebaseio.com/");
var child1 =fireBaseRef.child(daya);
fireBaseRef.child(daya).set({deviceid:daya});

var qrcode = new QRCode(document.getElementById("qrcode"), {
	width : 300,
	height : 300
});
function makeid()
{ // var comment = $("#comments");
	
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}
var st=makeid();
qrcode.makeCode(daya);


dd.on('value', function(snapshot) {
    var uniqName = snapshot.val();
    var comment = snapshot.val()["id"];
    
	var deviceid = comment;
	var check = "undefined";
var result = check.localeCompare(comment);
	if(result!=0)
	{
	window.location.href = "http://localhost/phpfiles/Datametrics/table.php?id="+comment;	
	}
	
});

</script>

</body>
</html>