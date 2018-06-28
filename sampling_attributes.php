<?php
$xml = new DomDocument();
$xmlFile = "/home/username/records.xml";          
$xml= DOMDocument::load($xmlFile);          
$s_pnt = $xml->getElementsByTagName("samplingpoint_point");
foreach($s_pnt as $node) {          
  $x = $node->getAttribute("x");          
  echo $x," ";  
 $y = $node->getAttribute("y");           
	echo $y," " ;
$sql = "INSERT INTO coords (x, y) VALUES('". $x ."','". $y ."')";
mysql_query($sql)
} 


?> 

