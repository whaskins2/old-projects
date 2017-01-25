<?php
	require 'header.php';

	//validation for user inputs
	echo '<div class="mainbody">
		  <form action="display.php" method ="POST">';
	
	// Sets the search parameters to list out the articles in the given category.
	if(isset($_GET['catSearch'])){
		$_GET['search'] = true;
		$_GET['option1'] = 'categoryname';
		$_GET['entry'] = $_GET['catSearch'];
	}
	
	$addTable = new addTable();
	$tableReady = false;
	
	//Various Table Heading Formats
	$articleHeadings = ['ID', 'Title', 'Author', 'publish_date', 'Category'];
	$categoryHeadings = ['categoryname', 'category ID'];
	$userHeadings = ['Name', 'ID', 'DOB', 'Email Address', 'Phone Number', 'Admin'];
	
	if(isSet($_GET['search'])){
		if(strcasecmp($_GET['option1'],'categoryname')){ //http://php.net/manual/en/function.strcasecmp.php
			$addTable -> setHeadings($articleHeadings);
			
			if($user['admin'] == 'y'){
					$stmt = $pdo->prepare('SELECT * FROM articles WHERE ' . $_GET['option1'] . ' LIKE :entry');
			}else{
					$stmt = $pdo->prepare('SELECT * FROM articles WHERE ' . $_GET['option1'] . ' LIKE :entry AND publish_date <= "' . $date->format('Y-m-d') . '";');
					
			}
			$criteria = [
				'entry' => '%' . $_GET['entry'] . '%'
			];
			$stmt->execute($criteria);
			while ($row = $stmt->fetch()) {
				$results = $pdo->query('SELECT * FROM categories WHERE categoryid = ' . $row['category'] . ';');
				if(!$results){
					echo $pdo->errorInfo()[2];
				}else{
					$fetchAuthor = $pdo->query('SELECT firstname, surname FROM users WHERE userid = ' . $row['author_id'] . ';');
					$name = $fetchAuthor -> fetch();
					$authorName = $name['firstname'] . " " . $name['surname'];
					$rowToAdd = [
						$row['article_id'], $row['title'], $authorName, $row['publish_date']
					];
					$catName = $results -> fetch();
					if(!$catName['categoryname']){
						$noneSet = 'None Set';
						array_push($rowToAdd, $noneSet);
					}else{
						array_push($rowToAdd, $catName['categoryname']);
					}
					$viewButton = '<button type="submit" name="view" value="' . $row['article_id'] . '">View</button>';
					array_push($rowToAdd, $viewButton);
					if($user['admin'] === 'y'){
						$editButton = '<button type="submit" name="edit" value="' . $row['article_id'] . '">Edit</button>';
						array_push($rowToAdd, $editButton);
					}
					$addTable -> addRow($rowToAdd);
					$tableReady = true;
				}
			}
		}else{
			$addTable -> setHeadings($articleHeadings);
			$stmt = $pdo->prepare('SELECT * FROM categories WHERE categoryname LIKE :entry');
			$criteria = [
				'entry' => '%' . $_GET['entry'] . '%'
			];
			$stmt->execute($criteria);
			while ($row = $stmt->fetch()) {
				if($user['admin'] == 'y'){
					$results = $pdo->query('SELECT * FROM articles WHERE category = ' . $row['categoryid']);
				}else{
					$results = $pdo->query('SELECT * FROM articles WHERE category = ' . $row['categoryid'] . ' AND publish_date <= "' . $date->format('Y-m-d') . '";');
				}
				if(!$results){
					echo $pdo->errorInfo()[2];
				}else{
					foreach($results as $row){
						$result = $pdo->query('SELECT categoryname FROM categories WHERE categoryid = ' . $row['category'] . ';');
						$fetchAuthor = $pdo->query('SELECT firstname, surname FROM users WHERE userid = ' . $row['author_id'] . ';');
						$name = $fetchAuthor -> fetch();
						
						$authorName = $name['firstname'] . " " . $name['surname'];
						$rowToAdd = [
							$row['article_id'], $row['title'], $authorName, $row['publish_date']
						];
						foreach($result as $key){
							array_push($rowToAdd, $key['categoryname']);
						}
						$viewButton = '<button type="submit" name="view" value="' . $row['article_id'] . '">View</button>';
						array_push($rowToAdd, $viewButton);
						$tableReady = true;
						if($user['admin'] === 'y'){
							$editButton = '<button type="submit" name="edit" value= "' . $row['article_id'] . '">Edit</button>';
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
	
	if(isSet($_GET['show_all_articles'])){
		$addTable -> setHeadings($articleHeadings);
		if($user['admin'] == 'y'){
			$results = $pdo->query('SELECT * FROM articles');
		}else{
			$results = $pdo->query('SELECT * FROM articles WHERE publish_date <= "' . $date->format('Y-m-d') . '";');
		}
		if(!$results){
			echo $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				$catresults = $pdo->query('SELECT * FROM categories WHERE categoryid = ' . $row['category'] . ';');
				
				$fetchAuthor = $pdo->query('SELECT firstname, surname FROM users WHERE userid = ' . $row['author_id'] . ';');
				$name = $fetchAuthor -> fetch();		
				
				$authorName = $name['firstname'] . " " . $name['surname'];
				
				$rowToAdd = [
							$row['article_id'], $row['title'], $authorName, $row['publish_date']
						];
				
				$tableReady = true;
				$catName = $catresults -> fetch();
					if(!$catName['categoryname']){
						$noneSet = 'None Set';
						array_push($rowToAdd, $noneSet);
					}else{
						array_push($rowToAdd, $catName['categoryname']);
					}
				
				$viewButton = '<button type="submit" name="view" value="' . $row['article_id'] . '">View</button>';
				array_push($rowToAdd, $viewButton);
				if($user['admin'] === 'y'){
							$editButton = '<button type="submit" name="edit" value="' . $row['article_id'] . '">Edit</button>';
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
	
	if(isSet($_GET['show_all_categories'])){
		$addTable -> setHeadings($categoryHeadings);
		$results = $pdo->query('SELECT * FROM categories;');
		if(!$results){
			echo $pdo->errorInfo()[2];
		}else{
			foreach($results as $row){
				$viewButton = '<button type="submit" name="catSearch" value="' . $row['categoryname'] . '">View all articles in this category</button></p>';
				$rowToAdd = [$row['categoryid'], $row['categoryname'], $viewButton];
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
	<option checked="checked" value="title"/>Title <br>
	<option value="categoryname"/> Category <br>
	</select>
	<input type="text" name ="entry"/><br>
	<input type="submit" name="search" value="Search"/><br><br>';
	echo '<button type="submit" name="show_all_articles">Show All Articles</button><br><br>
		  <button type="submit" name="show_all_categories">Show All categories</button><br>';
	if($user['admin'] === 'y'){	
		echo '<br><button type="submit" name="show_all_users">Show All Registered Users</button>';
	}
	echo '</form><br><br></div><br>';
	
	require 'footer.php';
?>