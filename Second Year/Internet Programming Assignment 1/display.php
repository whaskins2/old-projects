<?php
	require 'header.php';

	echo '<div class="mainbody"><form action="display.php" method ="POST" enctype="multipart/form-data">';
	
	//The below allows us to get the jobid for the job whether the user navigates here from the query page or by hitting the submit button.
	if(isset($_POST['view'])){
		$jobid = $_POST['view'];
	}elseif(isset($_POST['apply'])){
		$jobid = $_POST['apply'];
	}elseif(isset($_POST['delete'])){ //Deletes Current Job
		$jobid = $_POST['delete'];
		$deleteApplications = $pdo->query('DELETE FROM applications WHERE jobid = ' . $jobid . ';');
		$deleteJob = $pdo->query('DELETE FROM jobs WHERE jobid = ' . $jobid . ';');
		$hideFields = true;
		echo 'Job deleted';
	}elseif(isset($_POST['deleteUser'])){ //Deletes Current User and their uploaded cv if it exists.
		$hideFields = true;
		$directory = 'uploads/' . $_POST['deleteUser'];
		$dirForGlob = $directory . '/*'; //tells glob to find everything inside the directory
				$glob = glob($dirForGlob);
				foreach($glob as $file){
					unlink($file); //deletes existing files in directory
				}
		$deleteApps = $pdo->query('DELETE FROM applications WHERE userid = ' . $_POST['deleteUser'] . ';');
		$deleteUser = $pdo->query('DELETE FROM users WHERE userid = ' . $_POST['deleteUser']. ';');
		echo 'User deleted';
	}elseif(isset($_POST['viewapplicants'])){
		$jobid = $_POST['viewapplicants'];
		$hideFields = true;
		$applications = $pdo->query('SELECT * FROM applications WHERE jobid = ' . $jobid . ';');
		$jobappliedto = $pdo->query('SELECT title FROM jobs WHERE jobid = ' . $jobid);
		$jobInfo = $jobappliedto->fetch();
		echo '<h3>The job ' . $jobInfo['title'] . ' has been applied to by: </h3>';
		foreach($applications as $row){
			$applicant = $pdo->query('SELECT firstname, surname, email, phonenumber FROM users WHERE userid = ' . $row['userid']);
			$userInfo = $applicant->fetch();
			echo '<p>' . $userInfo['firstname'] . ' ' . $userInfo['surname'] . '<br>Contact Info:<br>Email: ' . $userInfo['email'] . ' Phone: ' . 
			$userInfo['phonenumber'] . '<br>Cover Letter:<br>' . $row['coverLetter'];
		}	
	}elseif(isset($_POST['edit'])){
		$jobid = $_POST['edit'];
		$hideFields = true;
	}elseif(isset($_POST['confirm'])){
		$jobid = $_POST['confirm'];
	}elseif(isset($_POST['editUser'])){
		$editUserID = $_POST['editUser'];
		$hideFields = true;
	}elseif(isset($_POST['confirmEdit'])){
		$hideFields = true;
	}else{
		header('Location: index.php');
	}
	
	//validation for job edits
	if(isset($_POST['confirm']) || isset ($_POST['edit'])){
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
		$location = true;
		$cvError;
		if(isset($_POST['confirm'])){
			if($_POST['title'] === ''){
				$title = false;
				$valid = false;
			}
			if($_POST['companyname'] === ''){
				$compName = false;
				$valid = false;
			}
			if($_POST['companyemail'] === ''){
				$email = false;
				$valid = false;
			}elseif(!filter_var($_POST['companyemail'], FILTER_VALIDATE_EMAIL)){ //http://php.net/manual/en/function.filter-var.php
				$validEmail = false;
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
			if($_POST['contacttitle'] === ''){
				$contactTitle = false;
				$valid = false;
			}
			if($_POST['contactfirstname'] === ''){
				$contactFirstName = false;
				$valid = false;
			}
			if($_POST['contactsurname'] === ''){
				$contactSurname = false;
				$valid = false;
			}
		}
	}
	
	if(isset($_POST['confirm']) && $valid === true){ //Code for applying job updates;
		$stmt = $pdo->prepare('UPDATE jobs SET title = :title, companyname = :companyname, companyemail = :companyemail,
		companyphone = :companyphone, contacttitle = :contacttitle, contactfirstname = :contactfirstname, contactsurname = :contactsurname,
		salary = :salary, location = :location, catagoryid = :catagoryid WHERE jobid = ' . $jobid . ';');
		$results = $pdo->query('SELECT * FROM catagories WHERE catagoryname = "' . $_POST['catagoryList'] . '";');
		$newcat = $results->fetch();
		$hideFields = true;
		if(!$stmt){
			$error = $pdo->errorInfo()[2];
		}else{
			$criteria = [
			'title' => $_POST['title'],
			'companyname' => $_POST['companyname'],
			'companyemail' => $_POST['companyemail'],
			'companyphone' => $_POST['companyphone'],
			'contacttitle' => $_POST['contacttitle'],
			'contactfirstname' => $_POST['contactfirstname'],
			'contactsurname' => $_POST['contactsurname'],
			'companyphone' => $_POST['companyphone'],
			'contacttitle' => $_POST['contacttitle'],
			'contactfirstname' => $_POST['contactfirstname'],
			'contactsurname' => $_POST['contactsurname'],
			'salary' => $_POST['salary'],
			'location' => $_POST['location'],
			'catagoryid' => $newcat['catagoryid']
			];
			$stmt->execute($criteria);
			echo '<p>changes made</p>';
		}
	}elseif(isset($_POST['confirm']) && $valid === false){
		echo '<h1>error!</h1>';
	}
	
	if(!isset($_POST['delete']) && !isset($_POST['editUser']) && !isset($_POST['deleteUser']) && !isset($_POST['confirmEdit'])){ //If user is either editing or viewing an individual job.
		$job = $pdo->query('SELECT * FROM jobs WHERE jobid = ' .  $jobid . ';');
				if(!$job){
					echo $pdo->errorInfo()[2];
				}else{
					$result = $job->fetch();
					$catagory = $pdo->query('SELECT * FROM catagories WHERE catagoryid = ' . $result['catagoryid'] . ';');
					$catResult = $catagory->fetch();
					$addedBy = $pdo->query('SELECT firstname, surname FROM users WHERE userid = ' . $result['userid'] . ';');
					$addedName = $addedBy->fetch();
					if(isset($_POST['view'])){ //if the user wants to view the job
						echo '<h3>Job Title: ' . $result['title'] . ' <br>Company: ' . $result['companyname'] . '<br>Job ID: ' . $result['jobid'] . '<br>Salary: ' . $result['salary'] . '<br>Location: ' . $result['location'] .
						' <br>Contact Name: ' . $result['contacttitle'] . ' ' . $result['contactfirstname'] . ' ' . $result['contactsurname'] . 
						' <br>Email: ' . $result['companyemail'] . ' <br>phone number: ' . $result['companyphone'] . '<br>Catagory: ' . $catResult['catagoryname'] . '<br>Added by: ' . $addedName['firstname'] . ' ' . $addedName['surname']  . '</p>';
					}elseif(isset($_POST['edit']) || isset($_POST['confirm'])){ //if the user wants to edit the job
							echo '<h2>Edit Job</h2><h3>Job ID:' . $result['jobid'] . '<br>Job Title: <input type="text" name="title" value="' . $result['title'] . '"/>';
							if(!$title){
								echo 'This job needs a title';
							}
							echo'<br>
							Company: <input type="text" name="companyname" value="' . $result['companyname'] . '"/>';
							if(!$compName){
								echo 'You must provide a company name';
							}
							echo '<br>
							Salary: <input type="text" name="salary" value="' . $result['salary'] . '"/>';
							if(!$salary){
								echo 'You must provide a salary';
							}elseif(!$salaryNum){
								echo 'The salary must be a number';
							}
							echo '<br>
							Location: <input type="text" name="location" value="' . $result['location'] . '"/>';
							if(!$location){
								echo 'You must provide a location';
							}
							echo '<br>
							Contact Title: <input type="text" name="contacttitle" value="' . $result['contacttitle'] . '"/>';
							if(!$contactTitle){
								echo 'You must provide a contact title';
							}
							echo'<br>
							Contact Firstname: <input type="text" name="contactfirstname" value="' . $result['contactfirstname'] . '"/>';
							if(!$contactFirstName){
								echo 'You must provide a contact firstname';
							}
							echo '<br>
							Contact Surname: <input type="text" name="contactsurname" value="' . $result['contactsurname'] . '"/>';
							if(!$contactSurname){
								echo 'You must provide a contact surname';
							}
							echo '<br>
							Email: <input type="text" name="companyemail" value="' . $result['companyemail'] . '"/>';
							if(!$email){
								echo 'You must provide an email address';
							}elseif(!$validEmail){
								echo 'That email address is not valid';
							}
							echo '<br>
							phone number: <input type="text" name="companyphone" value="' . $result['companyphone'] . '"/><br>
							Catagory: <select name="catagoryList">';
							$results = $pdo->query('SELECT catagoryname FROM catagories;');
							if(!$results){
								$error = $pdo->errorInfo()[2];
							}else{
								foreach($results as $row){
									echo '<option value="' . $row['catagoryname'] . '" ';
									if ($row['catagoryname'] === $catResult['catagoryname']){ //Sets default value to current catagory
										echo 'selected = "selected"';
									}
									echo '/>' . $row['catagoryname'];
								}
							}
							echo '</select><br>
							Added By: ' . $addedName['firstname'] . ' ' . $addedName['surname'] . '
							<br><button type="submit" name="confirm" value="' . $jobid . '">Submit</button><br>';
					}
				}
	}
	
	//validation for user edits
	if(isset($_POST['confirmEdit']) || isset($editUserID)){ //a bit of duplication here between this and job edits, maybe consolidate the variables in an external method.
		$valid = true;
		$firstname = true;
		$surname = true;
		$validEmail = true;
		$email = true;
		if(isset($_POST['confirmEdit'])){
			if($_POST['firstname'] === ''){
				$firstname = false;
				$valid = false;
			}
			if($_POST['surname'] === ''){
				$surname = false;
				$valid = false;
			}
			if(!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)){
				$validEmail = false;
				$valid = false;
			}elseif($_POST['email'] === ''){
				$email = false;
				$valid = false;
			}
		}
	}
	
	if(isset($_POST['confirmEdit']) && $valid === true){
		$stmt = $pdo->prepare('UPDATE users SET firstname = :firstname, surname = :surname, dob = :dob, email = :email,
		phonenumber = :phonenumber,	admin = :admin WHERE userid = ' . $_POST['confirmEdit'] . ';');
		if(!$stmt){
			$error = $pdo->errorInfo()[2];
		}else{
			$criteria = [
			'firstname' => $_POST['firstname'],
			'surname' => $_POST['surname'],
			'dob' => $_POST['dob'],
			'email' => $_POST['email'],
			'phonenumber' => $_POST['phonenumber'],
			'admin' => $_POST['admin']
			];
			$stmt->execute($criteria);
			if(isset($_FILES['cv'])){
				$directory = 'uploads/' . $_POST['confirmEdit'];
				require 'uploadCV.php';
			}
			if(isset($cvMessage) && isset($_FILES['cv'])){
				echo $cvMessage;
				echo '<p>Other changes successfully made to user account: ' . $_POST['confirmEdit'] . '</p>
					  <button type="submit" name="editUser" value="' . $_POST['confirmEdit'] . '">View User</button>';
			}else{
				echo '<p>Changes successfully made to user account: ' . $_POST['confirmEdit'] . '</p>
					  <button type="submit" name="editUser" value="' . $_POST['confirmEdit'] . '">View User</button>';
			}
		}
	}
	
	if(isset($editUserID)){
		$subject = $pdo->query('SELECT * FROM users WHERE userid = ' . $editUserID . ';');
		$result = $subject->fetch();
		$hideFields = true;
		if(!$subject){
			$error = $pdo->errorInfo()[2];
		}else{
			echo '<h2>Edit User</h2><h3>
			User ID: ' . $result['userid'] . '<br>
			User Firstname: <input type="text" name="firstname" value="' . $result['firstname'] . '"/>';
			if(!$firstname){
				echo 'You must enter a firstname';
			}
			echo '<br>
			User Surname: <input type="text" name="surname" value="' . $result['surname'] . '"/><br>';
			if(!$surname){
				echo 'You must enter a surname';
			}
			echo 'User DOB: <input type="date" name="dob" value=' . $result['dob'] . '"/><br>
			Email: <input type="text" name="email" value="' . $result['email'] . '"/>';
			if(!$validEmail){
				echo 'That\'s not a valid email address';
			}elseif(!$email){
				echo 'You must enter an email address';
			}
			echo '<br>
			phone number: <input type="text" name="phonenumber" value="' . $result['phonenumber'] . '"/><br>
			Admin rights: <select name="admin">
							<option value="y"';
			if($result['admin'] == 'y'){
				echo 'selected = "selected"';
			}
			echo'/>Yes
							<option value="n"';
			if($result['admin'] == 'n'){
				echo 'selected = "selected"';
			}
			echo'>No</select><br>';
						  
			$directory = 'uploads/' . $result['userid'];
			if(is_dir($directory) === false ){ //based stackoverflow - http://stackoverflow.com/questions/18216930/how-to-create-folder-with-php-code
				mkdir($directory);
			}
			$dirContent = scandir($directory);
			$newCVButton = '<br><label for="cv">Select a new CV:</label><br><input type="file" name="cv"><br>';
			if(isset($dirContent[2])){
				echo 'CV filename: ' . $dirContent[2];
				echo $newCVButton;
				
			}else{
				echo 'No CV Uploaded';
				echo $newCVButton;
			}
		}
	}
	if (isset($_POST['apply'])){
		if(isset($_SESSION['loggedin'])){
			$stmt = $pdo->prepare('INSERT INTO applications (userid, jobid, date, coverLetter)
								  VALUES (:userid,:jobid,:date,:coverLetter)');
			if(!$stmt){
				$error = $pdo->errorInfo()[2];
			}else{
				$criteria = [
				'userid' => $_SESSION['loggedin'],
				'jobid' => $result['jobid'],
				'date' => date("Y-m-d"),
				'coverLetter' => $_POST['coverLetter']
				];
				$stmt->execute($criteria);
				echo '<p>Application Successful</p>';
				$hideFields = true;
				$applicant = $pdo->query('SELECT * FROM users WHERE userid = ' . $_SESSION['loggedin']);
				$job = $pdo->query('SELECT * FROM jobs WHERE jobid = ' . $result['jobid']);
				$applicantData = $applicant->fetch(); //Fetches information on the job applicant
				$jobData = $job->fetch(); //Fetches information on the job being applied too
				$jobCreator = $pdo->query('SELECT * FROM users WHERE userid = ' . $jobData['userid']);
				$jobCreatorData = $jobCreator->fetch(); //Fetches data on the user that added the job
				
				$subject = 'New Application for ' . $jobData['title']; //Creates the subject of the email.
				$emailContent = $applicantData['firstname'] . ' ' . $applicantData['surname'] . ' has just applied for the job: ' .  $jobData['title']; //Creates the content of the email.
				mail($jobCreatorData['email'],$subject,$emailContent); //Sends an email to admin that listed the job
				mail($jobData['companyemail'],$subject,$emailContent); //Sends an email to the job\'s listed email
			}
		}else{
			echo 'You must login to apply for this job<br>
			<a href="adduser.php?newuser=new">No account? Sign up here</a><br>';
			$hideFields = true;
		}
	}

	if(!isset($hideFields)){
		echo '<textarea name="coverLetter" cols="80" rows="8" maxlength="2000">Enter your cover letter here</textarea><br>
		<button type="submit" name="apply" value="' . $jobid . '">Send Application</button><br>';
	}
	if ($user['admin'] === 'y'  && !isset($_POST['editUser']) && !isset($_POST['deleteUser']) &&!isset($_POST['delete']) && !isset($_POST['confirmEdit'])){
		if(isset($_POST['viewapplicants'])){
			echo '<br><button type="submit" name="view" value="' . $jobid . '">View Job</button><br>';
		}else{
			echo '<button type="submit" name="viewapplicants" value="' . $jobid . '">View Applications for this job</button><br>';
		}
		if(!isset($_POST['edit']) && !isset($_POST['confirm'])){
			echo '<button type="submit" name="edit" value="' . $jobid . '">Edit Job</button><br>';
		}
		echo '<button type="submit" name="delete" value="' . $jobid . '">Delete Job</button><br>';
	}elseif(isset($_POST['editUser'])){
		echo '<button type="submit" name="confirmEdit" value="' . $_POST['editUser'] . '">Confirm Changes</button>';
		echo '<button type="submit" name="deleteUser" value="' . $_POST['editUser']	. '">Delete User</button><br>';
	}
	echo '</div><br>';
	
	require 'footer.php';
?>