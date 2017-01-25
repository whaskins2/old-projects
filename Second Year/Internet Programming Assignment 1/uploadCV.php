<?php
		if(isset($_FILES['cv'])){
			$uploaded = 1;
			$cvMessage;
			$findid = $pdo->query('SELECT userid FROM users WHERE email = "' . $_POST['email'] . '";');
			$userid = $findid->fetch();
			$directory = 'uploads/' . $userid['userid'];
			$fileStore = $directory . '/' . basename($_FILES['cv']['name']); //directory file is to be stored in.
			$fileType = pathinfo($fileStore,PATHINFO_EXTENSION);
			if(!is_dir($directory)){ //If no directory for the user already exists this will create one.
				mkdir($directory);
			}
			//Validation for the file uploads.
			if($_FILES['cv']['size'] > 500000){ //Stops files that are too large being uploaded.
				$cvMessage = 'That file is too large, please make it smaller';
				$uploaded = 0;
			}elseif($fileType != 'pdf' && $fileType != 'doc' && $fileType != 'docx' ){ //Stops files that are not of the accepted types from being uploaded.
					$cvMessage = 'The file uploaded is a ' . $fileType . ' file which is not a valid filetype, please use pdf/doc/docx file types.';
					$uploaded = 0;
			}

			if($uploaded == 1){
				$dirForGlob = $directory . '/*'; //tells glob to find everything inside the directory
				$glob = glob($dirForGlob);
				foreach($glob as $file){
					unlink($file); //deletes existing files in directory
				}
				if(move_uploaded_file($_FILES['cv']['tmp_name'], $fileStore)){ //Moves the file to the target fileStore directory where we save user CVs.
					$cvMessage = 'the file ' . basename($_FILES["cv"]["name"]) . ' has been uploaded.';
				}else{
					$cvMessage = 'The file could not be moved to the directory';
				}
			}
		}
?>