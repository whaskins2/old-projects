<?php
	require 'header.php';
	
	if (isset($_SESSION['loggedin']) && $user['admin'] === 'n'){ //stops non admins from navigating to this page via url.
		header('Location: index.php?admin=false');
	}elseif(!isset($_SESSION['loggedin'])){
		header('Location: index.php?login=false');
	}
	
 //Validation for new categories.
		$uniqueCheck = $pdo ->query('SELECT catagoryname FROM catagories;');
		$valid = true;
		$duplicate = false;
		$noInput = false;
	if(isset($_POST['newCatagory'])){
		$validateMe = $_POST['newCatagory'];
	}
	if(isset($_POST['editCatagory'])){
		$validateMe = $_POST['editCatagory'];
	}
	if(isset($validateMe)){
		foreach($uniqueCheck as $row){
			if ($row['catagoryname'] === $validateMe){
				$valid = false;
				$duplicate = true;
			}
		}
		if($validateMe === ''){
			$valid = false;
			$noInput = true;
		}
	}
	
	
	if(isSet($_POST['newCatagory']) && $valid === true){
		$stmt = $pdo->prepare('INSERT INTO catagories (catagoryname)
							   VALUES (:catagoryname)');
		$criteria = [
			'catagoryname' => $_POST['catagoryname']
		];
		$stmt->execute($criteria);
		if (!$stmt){
			echo $pdo->errorInfo()[2];
		}
	}
	if(isSet($_POST['editCatagory']) && $valid === true){
		$stmt = $pdo->prepare('UPDATE catagories SET catagoryname = :catagoryname
							   WHERE catagoryid = ' . $_POST['editCatagory'] . ';');
		$criteria = [
			'catagoryname' => $_POST['newName']
		];
		$stmt->execute($criteria);
		if(!$stmt){
			echo $pdo->errorInfo()[2];
		}else{
			echo 'Changes Made!';
		}
	}
	
	echo '<form action="addcatagory.php" method ="POST">';
	
	echo '<div class="mainbody">';
	
	if(isSet($_POST['delete'])){
		$stmt = $pdo->query('DELETE FROM catagories WHERE catagoryid = ' . $_POST['delete'] . ';');
		echo 'Catagory Deleted';
	}
	
	if(!isset($_POST['edit'])){
		echo '<p>Existing Categories:</p>';
		$results = $pdo->query('SELECT * FROM catagories;');
		$addTable = new addTable();
		$catagoryHeadings = ['Categoryname', 'Category ID'];
		$addTable->setHeadings($catagoryHeadings);
		foreach($results as $row){
			$catDelete = '<button type="submit" name="delete" value="' . $row['catagoryid'] . '">Delete</button>';
			$catEdit = '<button type="edit" name="edit" value="' . $row['catagoryid'] . '">Edit</button>';
			$rowToAdd = [
				$row['catagoryname'], $row['catagoryid'], $catDelete, $catEdit
			];
			$addTable -> addRow($rowToAdd);
		}
		echo $addTable -> getHTML();
		if(!$valid){
			echo '<p>Catagory name not valid</p>';
			if($noInput){
				echo '<p>You need to enter a catagory name</p>';
			}elseif($duplicate){
				echo '<p>That catagory already exists</p>';
			}
		}
	}else{
		echo 'Edit Catagory Name:<br>';
		$results = $pdo->query('SELECT * FROM catagories WHERE catagoryid = ' . $_POST['edit'] . ';');
		foreach ($results as $row){
			echo '<input type="textfield" name="newName" value="' . $row['catagoryname'] . '"/>';
			echo '<button type="submit" name="editCatagory" value="' . $row['catagoryid'] . '">Submit</button><br>';
		}
	}
	echo '<br>
		<label for="catagoryname">Enter a category name for a new category:<br>
		<input type="text" name="catagoryname"/><br>
		<input type="submit" name="newCatagory"/><br>
		</div><br>';
		
	if(isset($error)){
		echo $error;
	}	
	require 'footer.php';
?>