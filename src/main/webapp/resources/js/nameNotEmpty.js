function isEmptyUserName() {
	if (document.getElementById("user-name-field").value.length == 0) {
		alert("User name cannot be empty!");
		return false;
	}
	return true;
};

function isEmptyAppName() {
	if (document.getElementById("app-name-field").value.length == 0) {
		alert("App/site name cannot be empty!");
		return false;
	}
	return true;
};