<?php
session_start();
echo'<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Warren\'s Website</title>
		<link rel="stylesheet" type="text/css" href="styles.css" />
	</head>
	<header>
		<h3> Jobsearchsite.com </h3>
	</header>';
	$server = '192.168.56.2';
	$username = 'student';
	$password = 'student';
	$schema = 'joblist';
	$pdo = new PDO('mysql:dbname=' . $schema . ';host=' . $server, $username, $password);
	
	$loginform = '<form action="index.php" method="POST">
			Email:  
			<input type="text" name="email"><br>
			Password: 
			<input type="password" name="password"><br>
			<input type="submit" name="login"><br>
			</form>
			<a href="adduser.php?newuser=new">No account? Sign up here</a><br>';
	
	if (isset($_SESSION['loggedin'])) {
		$stmt = $pdo->prepare('SELECT userid, firstname, surname, admin FROM users WHERE userid = :userid;');
		$criteria = [
				'userid' => $_SESSION['loggedin']
		];
		$stmt->execute($criteria);
		$user = $stmt->fetch();
		echo 'You are signed in ' . $user['firstname'] . ' ' . $user['surname'] . '<br>';
		echo '<a href="logout.php">logout</a><br>';	
	}elseif (!isset($_POST['login']) && !isset($_GET['newuser'])){
		echo 'You should sign in';
		echo $loginform;
	}
	if(!isset($user['admin'])){
		$user['admin'] = 'n';
	}
	//navigation
	echo '<div class=navigation><a href="http://192.168.56.2/assignment_files/index.php">Home</a> 
	      <a href="http://192.168.56.2/assignment_files/query.php">Search</a> ';
	if ($user['admin'] === 'y'){
		echo '<a href="http://192.168.56.2/assignment_files/addjob.php">Add Job</a> 
		<a href="http://192.168.56.2/assignment_files/addcatagory.php">Edit Categories</a>
		<a href="http://192.168.56.2/assignment_files/adduser.php">Add User</a>
		</div>';
	}
	echo '</div><br>';
	
	function autoload($name){
		require strtolower($name) . '.php';
	}
	spl_autoload_register('autoload');
?>