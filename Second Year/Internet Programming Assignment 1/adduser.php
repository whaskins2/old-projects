<?php
	require 'header.php';
	echo '<div class="mainbody">';
	if(isset($_SESSION['loggedin']) && $user['admin'] === 'n'){
		header('Location: index.php?admin=false');
	}elseif($user['admin'] != 'y'){
		$_POST['admin'] = 'n';
		echo '<p>Sign Up</p>';
	}
	
			//input validation
	$valid = true;
	if(isset($_POST['newUser'])){
		if($_POST['firstname'] === ''){
			$validFirstName = false;
			$valid = false;
		}else $validFirstName = true;
		if($_POST['surname'] === ''){
			$validSurname = false;
			$valid = false;
		}else $validSurname = true;
		if(!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)){ //http://php.net/manual/en/function.filter-var.php
			$validEmail = false;
			$valid = false;	
		}else{
			$uniqueCheck = $pdo->query('SELECT email FROM users;');
			$validEmail = true;
			foreach($uniqueCheck as $row){
				if ($row['email'] === $_POST['email']){
					$validEmail = false;
					$valid = false;
				}
			}
		}
		if($_POST['password'] === ''){
			$validPassword = false;
			$valid = false;
		}else{
			$validPassword = true;
		}
		if(isset($uploaded)){
			if($uploaded = 0){
				$valid = false;
			}
		}
	}
	if(isSet($_POST['newUser']) && $valid === true){
		$stmt = $pdo->prepare('INSERT INTO users (firstname, surname, dob, email, phonenumber, admin, password)
							VALUES (:firstname, :surname, :dob, :email, :phonenumber, :admin, :password)');
		$criteria = [
		'firstname' => $_POST['firstname'],
		'surname' => $_POST['surname'],
		'dob' => $_POST['dob'],
		'email' => $_POST['email'],
		'phonenumber' => $_POST['phone'],
		'admin' => $_POST['admin'],
		'password' => password_hash($_POST['password'], PASSWORD_DEFAULT)
		];
		$stmt->execute($criteria);
		require 'uploadCV.php'; //contains code for uploading a CV.
		if (!$stmt){
			echo $pdo->errorInfo()[2];
		}else{
			echo 'Account added to users!<br>
				 You may now log in!';
			$hideFields = true;
			if($uploaded == 0){
				echo $cvMessage;
				echo 'Please contact an administrator to upload your CV';
			}
		}
	}
	
	echo '<form action="adduser.php" method="POST" enctype="multipart/form-data">';
	if(!isset($hideFields)){
	echo'	<label for="firstname">Firstname:<br>
		<input type="text" name="firstname"/><br>';
		if(isset($_POST['newUser'])){
			if(!$validFirstName){
				echo ' You must enter a firstname <br>';
			}
		}
		echo '<label for="surname">Surname:<br>
		<input type="text" name="surname"/><br>';
		if(isset($_POST['newUser'])){
			if(!$validSurname){
				echo ' You must enter a surname <br>';
			}
		}
		echo '<label for="dob">DOB:<br>
		<input type="date" name="dob" max="' . date("Y-m-d") . '" min="1900-01-01"/><br>
		
		<label for="phone">Phone number:<br>
		<input type="text" name="phone"/><br>
		
		<label for="email">Email:<br>
		<input type="text" name="email"/><br>';
		if(isset($_POST['newUser'])){
			if(!$validEmail){
				echo 'The email you entered is not valid. <br>';
			}
		}
	if($user['admin']==='y'){
		echo '<label for="admin">Admin rights:<br>
			  <select name="admin">
				<option value="y">Yes
				<option value="n">No
			  </select><br>';
	}
	echo '<label for="password">Account Password:<br>
		<input type="password" name="password"/><br>';
		if(isset($_POST['newUser'])){
			if(!$validPassword){
				echo 'You must enter a password.<br>';
			}
		}
			
	echo '<label for="cv">Upload your CV:<br>
		  <input type="file" name="cv"/><br><br>';
		  
	if(isset($cvMessage)){
		echo $cvMessage;
	}
	echo '<input type="submit" name="newUser"/><br>';
	echo '</div><br>';
	}
	require 'footer.php';
?>