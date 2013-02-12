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
	#footer {
		height: 60px;
	}
	</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<ul class="nav nav-pills pull-right">
			  <#if user??>
			  <li class="disabled">
			  	<a href="#">Connected as ${user.name}</a>
			  </li>
			  <li>
			  	<a href="/j_spring_security_logout">Logout</a>
			  </li>
			  <#else>
			  <li>
			    <a href="#modalLogin" data-toggle="modal">Login</a>
			  </li>
			  <li>
			    <a href="#modalSignUp" data-toggle="modal">Sign up</a>
			  </li>
			  </#if>
			</ul>
		</div>

		<div class="hero-unit">
			<h1>Brainbox</h1>
			<p class="lead">The suggestion box</p>
			<#if user??>
			<p><a href="#modalIdea" class="btn btn-primary btn-large" data-toggle="modal">Post an idea</a></p>
			</#if>
		</div>
		<div class="row-fluid">
			<#if !user??>
			<div class="alert alert-info">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<p><strong>Tip</strong>: If you want to post an idea, you have to be authenticated.</p>
				<p>
					You can <a href="#modalSignUp" data-toggle="modal">sign up</a> or <a href="#modalLogin" data-toggle="modal">login</a> with the following credentials:
					<dl class="dl-horizontal">
						<dt>username</dt>
						<dd>test</dd>
						<dt>password</dt>
						<dd>password</dd>
					</dl>
				</p>
			</div>
			</#if>
			<div class="offset2 span8">
				<ul class="media-list">
					<#list ideas as idea>
					<li class="media well">
						<h4 class="media-heading">${idea.title}</h4>
						<p class="muted">${idea.description}</p>
						<small class="pull-right">by <strong>${idea.author.name}</strong> the ${idea.createdDate.toString("MM/dd/yy' at 'HH:mm")}</small>
					</li>
					</#list>
				</ul>
			</div>
		</div>
		<div class="row">
			<p class="muted">Small example of a Spring 3 based java webapp by <a href="http://github.com/Plixee">Plixee</a></p>
		</div>
	</div>

	<form action="/" method="post" data-validate="parsley">
		<div id="modalIdea" class="modal hide fade" tabindex="-1" role="dialog">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h3>Post an idea</h3>
			</div>
			<div class="modal-body">
				<div class="row-fluid">
					<label for="input-title">Idea</label>
					<input type="text" class="span12" id="input-title" name="title"  placeholder="Send a poney to the moon" data-trigger="keyup" data-maxlength="140" required="required"/>
					<label for="input-description">Description</label>
					<textarea class="span12" id="input-description" name="description" placeholder="Because poneys are great!" rows="5" data-trigger="keyup" data-maxlength="1000" required="required"></textarea>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">Post</button>
				<button type="reset" class="btn" data-dismiss="modal">Cancel</button>
			</div>
		</div>
	</form>

	<form action="/j_spring_security_check" method="post" data-validate="parsley">
		<div id="modalLogin" class="modal hide fade" tabindex="-1" role="dialog">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h3>Login</h3>
			</div>
			<div class="modal-body">
				<div class="row-fluid">
					<label for="input-login-name">Username</label>
					<input type="text" class="span12" id="input-login-name" name="j_username" placeholder="Chuck Norris" data-trigger="change" data-rangelength="[2,32]" data-validation-minlength="1" required="required"/>
					<label for="input-login-password">Password</label>
					<input type="password" class="span12" id="input-login-password" name="j_password"  placeholder="******************" data-minlength="6" required="required"/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">Login</button>
				<button type="reset" class="btn" data-dismiss="modal">Cancel</button>
			</div>
		</div>
	</form>

	<form action="/signup" method="post" data-validate="parsley">
		<div id="modalSignUp" class="modal hide fade" tabindex="-1" role="dialog">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h3>Sign up</h3>
			</div>
			<div class="modal-body">
				<div class="row-fluid">
					<label for="input-signup-name">Username</label>
					<input type="text" class="span12" id="input-signup-name" name="name" placeholder="Chuck Norris" data-trigger="change" data-rangelength="[2,32]" data-validation-minlength="1" required="required"/>
					<label for="input-signup-password">Password</label>
					<input type="password" class="span12" id="input-signup-password" name="password"  placeholder="****************" data-trigger="keyup" data-minlength="6" required="required"/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary">Sign up</button>
				<button type="reset" class="btn" data-dismiss="modal">Cancel</button>
			</div>
		</div>
	</form>

	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"></script>
	<script src="https://raw.github.com/guillaumepotier/Parsley.js/master/dist/parsley-standalone.min.js"></script>
</body>
</html>