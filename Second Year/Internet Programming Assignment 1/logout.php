<?php
	//This page destroys the session and redirects the user back to the homepage where they can log in again.
	session_start();
	session_destroy(); //http://php.net/manual/en/function.session-destroy.php
	header('Location: index.php');
	exit;
?>