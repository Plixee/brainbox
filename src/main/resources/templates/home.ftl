<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
	<title>Brainbox</title>
	<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
	<style type="text/css">
		ul.parsley-error-list {
			font-size: 0.9em;
			list-style-type:none;
		}
		ul.parsley-error-list li {
			color: #b94a48;
			line-height: 1em;
			margin: 0.5em;
		}
	</style>
</head>
<body>
	<div class="container">
		<div class="hero-unit">
			<h1>Brainbox</h1>
			<p class="lead">The suggestion box</p>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<form action="/" method="post" data-validate="parsley">
					<fieldset>
						<legend>Post an idea</legend>
						<label for="input-author">Author</label>
						<input type="text" id="input-author" name="author" class="span12" placeholder="Chuck Norris" data-trigger="change" data-rangelength="[2,32]" data-validation-minlength="1" required="required"/>
						<label for="input-title">Idea</label>
						<input type="text" id="input-title" name="title" class="span12" placeholder="Send a poney to the moon" data-trigger="keyup" data-maxlength="140" required="required"/>
						<label for="input-description">Description</label>
						<textarea id="input-description" name="description" class="span12" placeholder="Because poneys are great!" rows="5" data-trigger="keyup" data-maxlength="1000" required="required"></textarea>
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
	<script src="https://raw.github.com/guillaumepotier/Parsley.js/master/dist/parsley-standalone.min.js"></script>
</body>
</html>