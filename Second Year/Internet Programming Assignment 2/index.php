<?php
	require 'header.php';
	
	//Accounts for testing
	//passwords: joebloggs@gmail.com = password - Admin
	//			 themobe@gmail.com = password - Non admin

			
	if (isset($_POST['login'])) { //if they pressed login
		//Check they entered the correct username/password 
		$stmt = $pdo->prepare('SELECT * FROM users WHERE email = :email;');
		$criteria = [
				'email' => $_POST['email']
			];
		$stmt->execute($criteria);
		$user = $stmt->fetch();
		if(password_verify($_POST['password'], $user['password'])){
				$_SESSION['loggedin'] = $user['userid'];
				echo '<p>Login Successful. Welcome ' . $user['firstname'] . ' ' . $user['surname'] . '</p>
				<p>You are now logged in</p>';
				echo '<a href="logout.php">logout</a>';
		}
			//If they didn't, display an error message
		else {
				echo '<p>You did not enter the correct username and password</p>';
				echo $loginform;
		}
	}
	//The submit button was not pressed, show the log-in form
	elseif(isset($_SESSION['loggedin']) && !isset($_GET['login'])) {
		if(isset($_GET['admin'])){
			echo '<p>You need admin permission to access that page.</p>';
		}
	}elseif(isset($_GET['login'])){
		echo 'You need to be logged in to access that page.<br>';
	}

	echo '<div class="mainbody">
		  <br>Welcome To ArticlePublish.com!<br>';
	echo '</div><br>';
	
	require 'footer.php';
?>