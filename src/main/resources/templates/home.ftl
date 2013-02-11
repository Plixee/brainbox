<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Brainbox</title>
	<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="hero-unit">
			<h1>Brainbox</h1>
			<p class="lead">The suggestion box</p>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<form action="/" method="post">
					<fieldset>
						<legend>Post an idea</legend>
						<label for="input-author">Author</label>
						<input type="text" id="input-author" name="author" class="span12" placeholder="Chuck Norris" />
						<label for="input-title">Idea</label>
						<input type="text" id="input-title" name="title" class="span12" placeholder="Send a poney to the moon" />
						<label for="input-description">Description</label>
						<textarea id="input-description" name="description" class="span12" placeholder="Because poneys are great!" rows="5"></textarea>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">Post</button>
							<button type="reset" class="btn">Cancel</button>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="span8">
				<ul class="media-list">
					<#list ideas as idea>
					<li class="media">
		  				<h4 class="media-heading">${idea.title}</h4>
						<p class="muted">${idea.description}</p>
		  				<small class="pull-right">by <strong>${idea.author}</strong> the ${idea.createdDate.toString("MM/dd/yy' at 'HH:mm")}</small>
					</li>
					</#list>
				</ul>
			</div>
		</div>
	</div>

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"></script>
</body>
</html>