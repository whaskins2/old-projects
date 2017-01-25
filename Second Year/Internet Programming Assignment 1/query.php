<?php
	require 'header.php';

	//validation for user inputs
	$salaryNum = true;
	if(isset($_GET['salaryLower'])){
		if(!is_numeric($_GET['salaryLower']) && $_GET['salaryLower'] != ''){ //http://php.net/manual/en/function.is-numeric.php
			$salaryNum = false;
		}elseif(!is_numeric($_GET['salaryUpper']) && $_GET['salaryUpper'] != ''){
			$salaryNum = false;
		}
	}
	
	echo '<div class="mainbody">
		  <form action="display.php" method ="POST">';
	
	// Sets the search parameters to list out the jobs in the given catagory.
	if(isset($_GET['catSearch'])){
		$_GET['search'] = true;
		$_GET['option1'] = 'catagoryname';
		$_GET['entry'] = $_GET['catSearch'];
	}
	
	$addTable = new addTable();
	$tableReady = false;
	
	//Various Table Heading Formats
	$jobHeadings = ['Job Title', 'Company', 'ID', 'Contact Email', 'Contact Name', 'Phone Number', 'Salary', 'location', 'Catagory'];
	$catagoryHeadings = ['Catagoryname', 'Catagory ID'];
	$userHeadings = ['Name', 'ID', 'DOB', 'Email Address', 'Phone Number', 'Admin'];
	
	if(isSet($_GET['salarySearch'])){
		if(isset($_GET['salaryLower']) || isset($_GET['salaryUpper'])){	//If the user is searching by salary this will trigger
			if($salaryNum == true){
				$addTable->setHeadings($jobHeadings);
				if($_GET['salaryLower'] > 0 && $_GET['salaryUpper'] <= 0){ //These isset's identify whether the user is searching with an upper limit a lower limit or want to find every job within two values.
						$stmt = $pdo->prepare('SELECT * FROM jobs WHERE salary >= :salaryLower');
						$criteria = [
							'salaryLower' => $_GET['salaryLower']
						];
				}elseif($_GET['salaryLower'] <= 0 && $_GET['salaryUpper'] > 0){
					$stmt = $pdo->prepare('SELECT * FROM jobs WHERE salary <= :salaryUpper');	
					$criteria = [
							'salaryUpper' => $_GET['salaryUpper']
						];
				}else{
					$stmt = $pdo->prepare('SELECT * FROM jobs WHERE salary >= :salaryLower AND salary <= :salaryUpper');
					$criteria = [
							'salaryLower' => $_GET['salaryLower'],
							'salaryUpper' => $_GET['salaryUpper']
						];
				}
				$stmt->execute($criteria);
				while ($row = $stmt->fetch()){ //Horrific amounts of duplication
					$results = $pdo->query('SELECT * FROM catagories WHERE catagoryid = ' . $row['catagoryid'] . ';');
					if(!$results){
						echo $pdo->errorInfo()[2];
					}else{
						$contactFullName = $row['contacttitle'] . ' ' . $row['contactfirstname'] . ' ' . $row['contactsurname']; //horrific duplication
						$rowToAdd = [
							$row['title'], $row['companyname'], $row['jobid'], $row['companyemail'], $contactFullName, $row['companyphone'], $row['salary'], $row['location']
						];
						
						$catName = $results -> fetch();
						if(!$catName['catagoryname']){
							$noneSet = 'None Set';
							array_push($rowToAdd, $noneSet);
						}else{
							array_push($rowToAdd, $catName['catagoryname']);
						}
						
						$viewButton = '<button type="submit" name="view" value="' . $row['jobid'] . '">View</button>';
						array_push($rowToAdd, $viewButton);
						if($user['admin'] === 'y'){
							$editButton = '<button type="submit" name="edit" value="' . $row['jobid'] . '">Edit</button>';
							array_push($rowToAdd, $editButton);
						}
						$addTable -> addRow($rowToAdd);
						$tableReady = true;
					}
				}
			}
		}
	}
	if(isSet($_GET['search'])){
		if(strcasecmp($_GET['option1'],'catagoryname')){ //http://php.net/manual/en/function.strcasecmp.php
			$addTable -> setHeadings($jobHeadings);
			$stmt = $pdo->prepare('SELECT * FROM jobs WHERE ' . $_GET['option1'] . ' LIKE :entry');
			$criteria = [
				'entry' => '%' . $_GET['entry'] . '%'
			];
			$stmt->execute($criteria);
			while ($row = $stmt->fetch()) {
				$results = $pdo->query('SELECT * FROM catagories WHERE catagoryid = ' . $row['catagoryid'] . ';');
				if(!$results){
					echo $pdo->errorInfo()[2];
				}else{
					$contactFullName = $row['contacttitle'] . ' ' . $row['contactfirstname'] . ' ' . $row['contactsurname'];
					$rowToAdd = [
						$row['title'], $row['companyname'], $row['jobid'], $row['companyemail'], $contactFullName, $row['companyphone'], $row['salary'], $row['location']
					];
					$catName = $results -> fetch();
					if(!$catName['catagoryname']){
						$noneSet = 'None Set';
						array_push($rowToAdd, $noneSet);
					}else{
						array_push($rowToAdd, $catName['catagoryname']);
					}
					$viewButton = '<button type="submit" name="view" value="' . $row['jobid'] . '">View</button>';
					array_push($rowToAdd, $viewButton);
					if($user['admin'] === 'y'){
						$editButton = '<button type="submit" name="edit" value="' . $row['jobid'] . '">Edit</button>';
						array_push($rowToAdd, $editButton);
					}
					$addTable -> addRow($rowToAdd);
					$tableReady = true;
				}
			}
		}else{
			$addTable -> setHeadings($jobHeadings);
			$stmt = $pdo->prepare('SELECT * FROM catagories WHERE catagoryname LIKE :entry');
			$criteria = [
				'entry' => '%' . $_GET['entry'] . '%'
			];
			$stmt->execute($criteria);
			while ($row = $stmt->fetch()) {
				$results = $pdo->query('SELECT * FROM jobs WHERE catagoryid = ' . $row['catagoryid'] . ';');
				if(!$results){
					echo $pdo->errorInfo()[2];
				}else{
					foreach($results as $row){
						$result = $pdo->query('SELECT catagoryname FROM catagories WHERE catagoryid = ' . $row['catagoryid'] . ';');
						$contactFullName = $row['contacttitle'] . ' ' . $row['contactfirstname'] . ' ' . $row['contactsurname'];
						$rowToAdd = [
							$row['title'], $row['companyname'], $row['jobid'], $row['companyemail'], $contactFullName, $row['companyphone'], $row['salary'], $row['location']
						];
						foreach($result as $key){
							array_push($rowToAdd, $key['catagoryname']);
						}
						$viewButton = '<button type="submit" name="view" value="' . $row['jobid'] . '">View</button>';
						array_push($rowToAdd, $viewButton);
						$tableReady = true;
						if($user['admin'] === 'y'){
							$editButton = '<button type="submit" name="edit" value="' . $row['jobid'] . '">Edit</button>';
							array_push($rowToAdd, $editButton);
						}
						$addTable -> addRow($rowToAdd);
					}
				}
			}
		}
	}
	if(isSet($_GET['show_all_users'])){
		$addTable -> setHeadings($userHeadings);
		$results = $pdo->query('SELECT * FROM users;');
		if(!$results){
			echo $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				$editButton = '<button type="submit" name="editUser" value="' . $row['userid'] . '">Edit</button>';
				$userFullName = $row['firstname'] . ' ' . $row['surname'];
				$rowToAdd = [$userFullName, $row['userid'], $row['dob'], $row['email'], $row['phonenumber'],  $row['admin'], $editButton];
				$tableReady = true;
				$addTable -> addRow($rowToAdd);
			}
		}
	}
	
	if(isSet($_GET['show_all_jobs'])){
		$addTable -> setHeadings($jobHeadings);
		$results = $pdo->query('SELECT * FROM jobs;');
		if(!$results){
			echo $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				$catresults = $pdo->query('SELECT * FROM catagories WHERE catagoryid = ' . $row['catagoryid'] . ';'); //duplication
				$contactFullName = $row['contacttitle'] . ' ' . $row['contactfirstname'] . ' ' . $row['contactsurname'];
				$rowToAdd = [
						$row['title'], $row['companyname'], $row['jobid'], $row['companyemail'], $contactFullName, $row['companyphone'], $row['salary'], $row['location']
				];
				$tableReady = true;
				$catName = $catresults -> fetch();
					if(!$catName['catagoryname']){
						$noneSet = 'None Set';
						array_push($rowToAdd, $noneSet);
					}else{
						array_push($rowToAdd, $catName['catagoryname']);
					}
				
				$viewButton = '<button type="submit" name="view" value="' . $row['jobid'] . '">View</button>';
				array_push($rowToAdd, $viewButton);
				if($user['admin'] === 'y'){
							$editButton = '<button type="submit" name="edit" value="' . $row['jobid'] . '">Edit</button>';
							array_push($rowToAdd, $editButton);
				}
				$addTable -> addRow($rowToAdd);
			}
		}
	}
	if($tableReady){
		echo $addTable -> getHTML();
		$tableReady = false;
	}
	echo '</form><form action="query.php" method ="GET">';
	
	if(isSet($_GET['show_all_catagories'])){
		$addTable -> setHeadings($catagoryHeadings);
		$results = $pdo->query('SELECT * FROM catagories;');
		if(!$results){
			echo $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				$viewButton = '<button type="submit" name="catSearch" value="' . $row['catagoryname'] . '">View all jobs in this catagory</button></p>';
				$rowToAdd = [$row['catagoryid'], $row['catagoryname'], $viewButton];
				$tableReady = true;
				$addTable -> addRow($rowToAdd);
			}
		}
	}
	if($tableReady){
		echo $addTable -> getHTML();
		$tableReady = false;
	}
	echo '<label for="option1"> Select a column:<br>
	<select name="option1">
	<option checked="checked" value="title"/> Job Title <br>
	<option value="catagoryname"/> Catagory <br>
	<option value="companyname"/> Company Name <br>
	</select>
	<input type="text" name ="entry"/><br>
	<input type="submit" name="search" value="Search"/><br><br>
	Search by salary:<br>
	<label for="salaryLower">Enter minimum salary here:
	<input type="text" name="salaryLower"/><br>
	<label for="salaryUpper">Enter maximum salary here:
	<input type="text" name="salaryUpper"/><br>';
	if(!$salaryNum){
		echo 'Invalid salary entered, please input numerical characters only<br>';
	}
	echo '<input type="submit" name="salarySearch" value="Submit Salary Search"/><br><br>
		  <button type="submit" name="show_all_jobs">Show All Available Jobs</button><br><br>
		  <button type="submit" name="show_all_catagories">Show All Catagories</button><br>';
	if($user['admin'] === 'y'){	
		echo '<br><button type="submit" name="show_all_users">Show All Registered Users</button>';
	}
	echo '</form><br><br></div><br>';
	
	require 'footer.php';
?>