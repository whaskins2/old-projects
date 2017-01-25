<?php
	require 'header.php';

	echo '<div class="mainbody"><form action="display.php" method ="POST" enctype="multipart/form-data">';
	
	//The below allows us to get the articleid for the article whether the user navigates here from the query page or by hitting the submit button.
	if(isset($_POST['view'])){
		$articleid = $_POST['view'];
	}elseif(isset($_POST['postComment'])){
		$articleid = $_POST['postComment'];
	}elseif(isset($_POST['delete'])){ //Deletes Current article
		$articleid = $_POST['delete'];
		$deleteApplications = $pdo->query('DELETE FROM comments WHERE article_id = ' . $articleid . ';');
		$deleteArticle = $pdo->query('DELETE FROM articles WHERE article_id = ' . $articleid . ';');
		$hideFields = true;
		echo 'Article deleted';
	}elseif(isset($_POST['deleteUser'])){ //Deletes Current User and their uploaded cv if it exists.
		$deleteUser = $pdo->query('DELETE FROM users WHERE userid = ' . $_POST['deleteUser']. ';');
		$deleteApps = $pdo->query('DELETE FROM comments WHERE userid = ' . $_POST['deleteUser'] . ';');
		$hideFields = true;
		echo 'User deleted';
	}elseif(isset($_POST['viewComments'])){
		$articleid = $_POST['viewComments'];
		$hideFields = true;
		$comments = $pdo->query('SELECT * FROM comments WHERE article_id = ' . $articleid . ';');
		$articleCommentedOn = $pdo->query('SELECT title FROM articles WHERE article_id = ' . $articleid);
		$articleInfo = $articleCommentedOn->fetch();
		echo '<h3>The job ' . $articleInfo['title'] . ' has the following comments: </h3>';
		foreach($comments as $row){
			$commentor = $pdo->query('SELECT firstname, surname, email, phonenumber FROM users WHERE userid = ' . $row['user_id']);
			$userInfo = $commentor->fetch();
			echo '<p>' . $userInfo['firstname'] . ' Says: ' . $row['comment'] . '</p>';
	}
	}elseif(isset($_POST['edit'])){
		$articleid = $_POST['edit'];
		$hideFields = true;
	}elseif(isset($_POST['confirm'])){
		$articleid = $_POST['confirm'];
		$hideFields = true;
	}elseif(isset($_POST['editUser'])){
		$editUserID = $_POST['editUser'];
		$hideFields = true;
	}elseif(isset($_POST['confirmEdit'])){
		$hideFields = true;
	}else{
		header('Location: index.php');
	}
	
	//validation for article edits
	if(isset($_POST['confirm']) || isset ($_POST['edit'])){
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
		if(isset($_POST['confirm'])){
			if($_POST['title'] === ''){
				$title = false;
				$valid = false;
			}
			if($_POST['content'] === ''){
				$articleContents = false;
				$valid = false;
			}
		}
	}
	
	if(isset($_POST['confirm']) && $valid === true){ //Code for applying article updates;
		$stmt = $pdo->prepare('UPDATE articles SET title = :title,
		content = :content, category = :category WHERE article_id = ' . $articleid . ';');
		$results = $pdo->query('SELECT * FROM categories WHERE categoryname = "' . $_POST['categoryList'] . '";');
		$newcat = $results->fetch();
		if(!$stmt){
			$error = $pdo->errorInfo()[2];
		}else{
			$criteria = [
			'title' => $_POST['title'],
			'content' => $_POST['content'],
			'category' => $newcat['categoryid']
			];
			$stmt->execute($criteria);
			echo '<p>changes made</p>';
		}
	}elseif(isset($_POST['confirm']) && $valid === false){
		echo '<h1>error!</h1>';
	}
	
	if(!isset($_POST['delete']) && !isset($_POST['editUser']) && !isset($_POST['deleteUser']) && !isset($_POST['confirmEdit'])){ //If user is either editing or viewing an individual article.
			$article = $pdo->query('SELECT * FROM articles WHERE article_id = ' .  $articleid . ';');
				if(!$article){
					echo $pdo->errorInfo()[2];
					echo 'big if failing';
				}else{
					$result = $article->fetch();
					$category = $pdo->query('SELECT * FROM categories WHERE categoryid = ' . $result['category'] . ';');
					$catResult = $category->fetch();
					$addedBy = $pdo->query('SELECT firstname, surname FROM users WHERE userid = ' . $result['author_id'] . ';');
					$addedName = $addedBy->fetch();
					if(isset($_POST['view'])){ //if the user wants to view the article
						echo '<h3>Title: ' . $result['title'] . ' <br>Author: ' . $addedName['firstname'] . ' ' . $addedName['surname'] . '<br>Article ID: ' . $result['article_id'] . '<br>Publish Date: ' . $result['publish_date'] . '<br>category: ' .
						$catResult['categoryname'] . '<br>Contents: <br>' . $result['content'] . '</p>';
					}elseif(isset($_POST['edit']) || isset($_POST['confirm'])){ //if the user wants to edit the article
							echo '<h2>Edit Article</h2><h3>Article ID: ' . $result['article_id'] . '<br>Title: <input type="text" name="title" value="' . $result['title'] . '"/>';
							if(!$title){
								echo 'This article needs a title';
							}
							echo'<br>
							Added By: ' . $addedName['firstname'] . ' ' . $addedName['surname'];
							echo '<br>Contents: <br><textarea rows="30" cols="100" name="content">' . $result['content'] . '</textarea><br>';
							if(!$title){
								echo 'This article needs some contents';
							}
							echo '<br>
							category: <select name="categoryList">';
							$results = $pdo->query('SELECT categoryname FROM categories;');
							if(!$results){
								$error = $pdo->errorInfo()[2];
							}else{
								foreach($results as $row){
									echo '<option value="' . $row['categoryname'] . '" ';
									if ($row['categoryname'] === $catResult['categoryname']){ //Sets default value to current category
										echo 'selected = "selected"';
									}
									echo '/>' . $row['categoryname'];
								}
							}
							echo '</select>
							<br><button type="submit" name="confirm" value="' . $articleid . '">Submit</button><br>';
					}
				}
	}
	
	//validation for user edits
	if(isset($_POST['confirmEdit']) || isset($editUserID)){
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
			echo '<p>Changes successfully made to user account: ' . $_POST['confirmEdit'] . '</p>
				  <button type="submit" name="editUser" value="' . $_POST['confirmEdit'] . '">View User</button>';
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
			echo 'User DOB: <input type="date" name="dob" max="' . date("Y-m-d") . '" min="1900-01-01" value="' . $result['dob'] . '"/><br>
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
		}
	}
	if (isset($_POST['postComment']) && !isset($_POST['viewComments'])){
		if(isset($_SESSION['loggedin'])){
			$stmt = $pdo->prepare('INSERT INTO comments (user_id, article_id, date, comment)
								  VALUES (:userid,:articleid,:date,:comment)');
			if(!$stmt){
				$error = $pdo->errorInfo()[2];
			}else{
				$criteria = [
				'userid' => $_SESSION['loggedin'],
				'articleid' => $result['article_id'],
				'date' => $date->format('Y-m-d'),
				'comment' => $_POST['comment']
				];
				$stmt->execute($criteria);
				echo '<p>Comment Posted</p>';
				$hideFields = true;
				$commentor = $pdo->query('SELECT * FROM users WHERE userid = ' . $_SESSION['loggedin']);
				$article = $pdo->query('SELECT * FROM articles WHERE article_id = ' . $result['article_id']);
				$commentorData = $commentor->fetch(); //Fetches information on the commentor
				$articleData = $article->fetch(); //Fetches information on the article being applied too
				$articleCreator = $pdo->query('SELECT * FROM users WHERE userid = ' . $articleData['author_id']);
				$articleCreatorData = $articleCreator->fetch(); //Fetches data on the user that added the article
				
				$subject = 'New Comment for ' . $articleData['title']; //Creates the subject of the email.
				$emailContent = $commentorData['firstname'] . ' ' . $commentorData['surname'] . ' has just commented on the article: ' .  $articleData['title']; //Creates the content of the email.
				mail($articleCreatorData['email'],$subject,$emailContent); //Sends an email to admin that posted the article
			}
		}else{
			echo 'You must login to comment on this article<br>
			<a href="adduser.php?newuser=new">No account? Sign up here</a><br>';
			$hideFields = true;
		}
	}
	if(!isset($hideFields)){
		echo '<textarea name="comment" cols="80" rows="8" maxlength="2000">Enter your comment here</textarea><br>
		<button type="submit" name="postComment" value="' . $articleid . '">Post Comment</button><br>';
		echo '<button type="submit" name="viewComments" value="' . $articleid . '">View Comments for this article</button><br>';
	}
	if ($user['admin'] === 'y'  && !isset($_POST['editUser']) && !isset($_POST['deleteUser']) &&!isset($_POST['delete']) && !isset($_POST['confirmEdit'])){
		if(isset($_POST['viewComments'])){
			echo '<button type="submit" name="view" value="' . $articleid . '">View Article</button><br>';
		}
		if(!isset($_POST['edit']) && !isset($_POST['confirm'])){
			echo '<button type="submit" name="edit" value="' . $articleid . '">Edit Article</button><br>';
		}
		echo '<button type="submit" name="delete" value="' . $articleid . '">Delete Article</button><br>';
	}elseif(isset($_POST['editUser'])){
		echo '<button type="submit" name="confirmEdit" value="' . $_POST['editUser'] . '">Confirm Changes</button>';
		echo '<button type="submit" name="deleteUser" value="' . $_POST['editUser']	. '">Delete User</button><br>';
	}
	echo '</div><br>';
	
	require 'footer.php';
?>