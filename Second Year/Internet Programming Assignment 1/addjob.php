<?php	
	require 'header.php';
	
	if (isset($_SESSION['loggedin']) && $user['admin'] === 'n'){
		header('Location: index.php?admin=false');
	}elseif(!isset($_SESSION['loggedin'])){
		header('Location: index.php?login=false');
	}
	//input validation
	$valid = true;
	$title = true;
	$compName = true;
	$validEmail = true;
	$email = true;
	$contactTitle = true;
	$contactFirstName = true;
	$contactSurname = true;
	$salary = true;
	$salaryNum = true;
	$phone = true;
	$location = true;
	if(isset($_POST['newJob'])){
		if($_POST['jobtitle'] === ''){
			$title = false;
			$valid = false;
		}
		if($_POST['company'] === ''){
			$compName = false;
			$valid = false;
		}
		if($_POST['phone'] === ''){
			$phone = false;
			$valid = false;
		}
		if($_POST['email'] === ''){
			$email = false;
			$valid = false;
		}elseif(!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)){ //http://php.net/manual/en/function.filter-var.php
			$validEmail = false;
			$valid = false;	
		}
		if($_POST['contactTitle'] === ''){
			$contactTitle = false;
			$valid = false;
		}
		if($_POST['contactFirstName'] === ''){
			$contactFirstName = false;
			$valid = false;
		}
		if($_POST['contactSurname'] === ''){
			$contactSurname = false;
			$valid = false;
		}
		if($_POST['salary'] === ''){
			$salary = false;
			$valid = false;
		}elseif(!is_numeric($_POST['salary'])){ //http://php.net/manual/en/function.is-numeric.php
			$salaryNum = false;
			$valid = false;
		}
		if($_POST['location'] === ''){
			$location = false;
			$valid = false;
		}
	}
	if(isSet($_POST['newJob']) && $valid === true){
		$catagory = $pdo->query('SELECT catagoryid FROM catagories WHERE catagoryname = "' . $_POST['catagoryList'] . '";');
		$result = $catagory->fetch();
		$stmt = $pdo->prepare('INSERT INTO jobs (title, companyname, companyemail, companyphone, contacttitle, contactfirstname, contactsurname, catagoryid, userid, salary, location)
								VALUES (:title, :companyname, :companyemail, :companyphone, :contacttitle, :contactfirstname, :contactsurname,
								:catagoryid, :userid, :salary, :location)');
		$criteria = [
			'title' => $_POST['jobtitle'],
			'companyname' => $_POST['company'],
			'companyemail' => $_POST['email'],
			'companyphone' => $_POST['phone'],
			'contacttitle' => $_POST['contactTitle'],
			'contactfirstname' => $_POST['contactFirstName'],
			'contactsurname' => $_POST['contactSurname'],
			'catagoryid' => $result['catagoryid'],
			'userid' => $user['userid'],
			'salary' => $_POST['salary'],
			'location' => $_POST['location']
		];
		$stmt->execute($criteria);
		if (!$stmt){
			echo $pdo->errorInfo()[2];
		}else{
			$jobAdded = true;
		}
	}
	
	echo '<div class="mainbody"><form action="addjob.php" method ="POST">';
		if(isset($jobAdded)){
			echo 'Job successfully added!<br><br>';
		}		
	echo '<label for="catagoryList">Select a catagory for this job:<br>
		<select name="catagoryList">';
		$results = $pdo->query('SELECT catagoryname FROM catagories;');
		if(!$results){
			$error = $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				echo '<option value="' . $row['catagoryname'] . '" />' . $row['catagoryname'];
			}
		}
		
	echo '</select><br>
		  <a href="http://192.168.56.2/assignment_files/addcatagory.php">Add a new catagory</a><br>
		  <label for="jobtitle">Enter the job title:<br>
		  <input type="text" name="jobtitle"/><br>';
	if(!$title){
		echo 'You must enter a job title.<br>';
	}
	echo '<label for="company">Enter company name:<br>
	<input type="text" name="company"/><br>';
	if(!$compName){
		echo 'you must enter a company name<br>';
	}
	echo '<label for="salary">Enter the salary in GBP:
		  <br><input type="text" name="salary"/><br>';
	if(!$salary){
		echo'You must enter a valid salary.<br>';
	}
	if(!$salaryNum){
		echo 'Not a valid salary, only numbers will be accepted.<br>';
	}
	echo '<label for="location">Enter the job location:<br>
		  <input type="text" name="location"/><br>';
	if(!$location){
		echo 'You must enter a job location.';
	}
	echo '<label for="email">Enter a relevant email:
		  <br><input type="text" name="email"/><br>';
	if(!$email){
		echo 'you must enter an email address.<br>';
	}elseif(!$validEmail){
		echo 'That email address is invalid.<br>';
	}
	echo '<label for="phone">Enter a phone number:<br>
	<input type="text" name="phone"/><br>';
	if(!$phone){
		echo 'Please enter a valid phone number';
	}
	echo 'Enter the name of the individual acting as a point of contact for this job:<br>
	<label for="contactTitle">Title:<br>
	<input type="text" name="contactTitle"/><br>';
	if(!$contactTitle){
		echo 'You must enter the title of the contact for this job.<br>';
	}
	echo '<label for="contactFirstName">Firstname:<br>
	<input type="text" name="contactFirstName"/><br>';
	if(!$contactFirstName){
		echo 'You must enter a firstname for the contact of this job.<br>';
	}
	echo '<label for="contactSurname">Surname:<br>
	<input type="text" name="contactSurname"/><br>';
	if(!$contactSurname){
		echo 'You must enter a surname for the contact of this job.<br><br>';
	}
	echo'<input type="submit" name="newJob">';
	echo '</form></div><br>';
		
	if(isset($error)){
		echo $error;
	}	
	require 'footer.php';
?>