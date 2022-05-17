<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="fr">
<body>
<?php

$hostname = "localhost";
$username = "review_site";
$password = "JxSLRkdutW";
$db = "menu";

$dbconnect = mysqli_connect($hostname, $username, $password, $db);

if ($dbconnect->connect_errno) {
    die("Database connection failed: " . $dbconnect->connect_error);
}
?>

<h1>Exemple Application Apache + MariaDB + PHP </h1>

<table border="1" align="center">
  <tr>
    <td>Item</td>
    <td>Details</td>
    <td>Prix</td>
  </tr>

    <?php

    $query = mysqli_query($dbconnect, "SELECT * FROM menu_items")
    or die (mysqli_error($dbconnect));

    while ($row = mysqli_fetch_array($query)) {
        echo
        "<tr>
    <td>{$row['name']}</td>
    <td>{$row['details']}</td>
    <td>{$row['price']} €</td>
   </tr>\n";

    }

    ?>
</table>
</body>
</html>