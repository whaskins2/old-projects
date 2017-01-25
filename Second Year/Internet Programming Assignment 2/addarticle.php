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
	$articleContents = true;
	if(isset($_POST['newArticle'])){
		if($_POST['title'] === ''){
			$title = false;
			$valid = false;
		}
		if($_POST['content'] === ''){
			$articleContents = false;
			$valid = false;
		}
	}
	if(isSet($_POST['newArticle']) && $valid === true){
		$category = $pdo->query('SELECT categoryid FROM categories WHERE categoryname = "' . $_POST['categoryList'] . '";');
		$result = $category->fetch();
		$stmt = $pdo->prepare('INSERT INTO articles (title, author_id, publish_date, content, category)
								VALUES (:title, :author_id, :publish_date, :content, :category)');
		$criteria = [
			'title' => $_POST['title'],
			'author_id' => $user['userid'],
			'publish_date' => $_POST['publishDate'],
			'content' => $_POST['content'],
			'category' => $result['categoryid']
			];
		$stmt->execute($criteria);
		if (!$stmt){
			echo $pdo->errorInfo()[2];
		}else{
			$articleAdded = true;
		}
	}
	
	echo '<div class="mainbody"><form action="addarticle.php" method ="POST">';
		if(isset($articleAdded)){
			echo 'Article successfully added!<br><br>';
		}		
	echo '<label for="categoryList">Select a category for this article:<br>
		<select name="categoryList">';
		$results = $pdo->query('SELECT categoryname FROM categories;');
		if(!$results){
			$error = $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				echo '<option value="' . $row['categoryname'] . '" />' . $row['categoryname'];
			}
		}
		
	echo '</select><br>
		  <a href="http://192.168.56.2/assignment_files2/addcategory.php">Add a new category</a><br>
		  <label for="title">Enter the article title:<br>
		  <input type="text" name="title"/><br>';
	if(!$title){
		echo 'You must enter an article title.<br>';
	}
	echo '<label for="publishDate">Enter the date you wish this article to be published:<br>
		  <input type="date" name="publishDate" min="'. $date->format('Y-m-d') .'" value="'. $date->format('Y-m-d') .'"/><br>';
	if(!$title){
		echo 'You must enter an article title.<br>';
	}
	echo '<label for="content">Contents:<br>
	<textarea rows="30" cols="100" name="content">hello</textarea><br>';
	if(!$articleContents){
		echo 'Your article must contain something.<br>';
	}
	echo'<input type="submit" name="newArticle">';
	echo '</form></div><br>';
		
	if(isset($error)){
		echo $error;
	}	
	require 'footer.php';
?>