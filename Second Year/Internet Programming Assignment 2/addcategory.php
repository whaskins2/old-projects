<?php
	require 'header.php';
	
	if (isset($_SESSION['loggedin']) && $user['admin'] === 'n'){ //stops non admins from navigating to this page via url.
		header('Location: index.php?admin=false');
	}elseif(!isset($_SESSION['loggedin'])){
		header('Location: index.php?login=false');
	}
	
 //Validation for new categories.
		$uniqueCheck = $pdo ->query('SELECT categoryname FROM categories;');
		$valid = true;
		$duplicate = false;
		$noInput = false;
	if(isset($_POST['newcategory'])){
		$validateMe = $_POST['categoryname'];
	}
	if(isset($_POST['editcategory'])){
		$validateMe = $_POST['newName'];
	}
	if(isset($validateMe)){
		foreach($uniqueCheck as $row){
			if ($row['categoryname'] === $validateMe){
				$valid = false;
				$duplicate = true;
			}
		}
		if($validateMe === ''){
			$valid = false;
			$noInput = true;
		}
	}
	
	
	if(isSet($_POST['newcategory']) && $valid === true){
		$stmt = $pdo->prepare('INSERT INTO categories (categoryname)
							   VALUES (:categoryname)');
		$criteria = [
			'categoryname' => $_POST['categoryname']
		];
		$stmt->execute($criteria);
		if (!$stmt){
			echo $pdo->errorInfo()[2];
		}
	}
	if(isSet($_POST['editcategory']) && $valid === true){
		$stmt = $pdo->prepare('UPDATE categories SET categoryname = :categoryname
							   WHERE categoryid = ' . $_POST['editcategory'] . ';');
		$criteria = [
			'categoryname' => $_POST['newName']
		];
		$stmt->execute($criteria);
		if(!$stmt){
			echo $pdo->errorInfo()[2];
		}else{
			echo 'Changes Made!';
		}
	}
	
	echo '<form action="addcategory.php" method ="POST">';
	
	echo '<div class="mainbody">';
	
	if(isSet($_POST['delete'])){
		$stmt = $pdo->query('DELETE FROM categories WHERE categoryid = ' . $_POST['delete'] . ';');
		echo 'Category Deleted';
	}
	
	if(!isset($_POST['edit'])){
		echo '<p>Existing Categories:</p>';
		$results = $pdo->query('SELECT * FROM categories;');
		$addTable = new addTable();
		$categoryHeadings = ['Categoryname', 'Category ID'];
		$addTable->setHeadings($categoryHeadings);
		foreach($results as $row){
			$catDelete = '<button type="submit" name="delete" value="' . $row['categoryid'] . '">Delete</button>';
			$catEdit = '<button type="edit" name="edit" value="' . $row['categoryid'] . '">Edit</button>';
			$rowToAdd = [
				$row['categoryname'], $row['categoryid'], $catDelete, $catEdit
			];
			$addTable -> addRow($rowToAdd);
		}
		echo $addTable -> getHTML();
		if(!$valid){
			echo '<p>category name not valid</p>';
			if($noInput){
				echo '<p>You need to enter a category name</p>';
			}elseif($duplicate){
				echo '<p>That category already exists</p>';
			}
		}
	}else{
		echo 'Edit category Name:<br>';
		$results = $pdo->query('SELECT * FROM categories WHERE categoryid = ' . $_POST['edit'] . ';');
		foreach ($results as $row){
			echo '<input type="textfield" name="newName" value="' . $row['categoryname'] . '"/>';
			echo '<button type="submit" name="editcategory" value="' . $row['categoryid'] . '">Submit</button><br>';
		}
	}
	echo '<br>
		<label for="categoryname">Enter a category name for a new category:<br>
		<input type="text" name="categoryname"/><br>
		<input type="submit" name="newcategory"/><br>
		</div><br>';
		
	if(isset($error)){
		echo $error;
	}	
	require 'footer.php';
?>