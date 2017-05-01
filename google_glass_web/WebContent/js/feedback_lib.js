		  function showAlertPane(metric) {
		      var alertPane = document.getElementById('alertPane');
		      //var successMessagContaier = document.getElementById('successMessage');
		      //successMessagContaier.innerHTML = metric.replace("update","") + " voted successfully!";
		      alertPane.style.display = "block";
		      setTimeout(hideAlertPane, 2000);
		  }


		  function hideAlertPane() {
		      document.getElementById('loadingModal').style.display = "none";
		      var alertPane = document.getElementById('alertPane');
		      alertPane.style.display = "none";
		  }


		  function parseMetricValue(value) {
		      return value; //?1:-1;
		  }

		  function sendFeedback(metric, value) {

		      studentId = document.getElementById('studentId').value;
		      document.getElementById('loadingModal').style.display = "block";
		      document.getElementById('spinner').style.display = "block";

		      w3Http("service/" + metric + "?studentId=" + studentId + "&value=" + parseMetricValue(value), function() {

		          if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
		              document.getElementById('spinner').style.display = "none";
		              showAlertPane(metric);
		          }
		      });
		  }

		  function setStudentId() {
		      var studentId = document.getElementById('studentId').value.trim();

		      if ("" != studentId) {
		          document.getElementById("loginButton").innerHTML = "Loading...";
		          w3Http("service/register?studentId=" + studentId, function() {

		              if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
		                  if (this.responseText.includes("success")) {
		                      document.getElementById("loginButton").innerHTML = "Enter";
		                      document.getElementById("errorPane").style.display = "none";
		                      document.getElementById('loginForm').style.display = 'none';
		                      document.getElementById('mainForm').style.display = 'block';
		                  } else {
		                      document.getElementById("loginButton").innerHTML = "Enter";
		                      document.getElementById("errorPane").style.display = "block";
		                  }
		              }
		          });
		      }
		  }

		  function exit() {
		      document.getElementById('loginForm').style.display = 'block';
		      document.getElementById('mainForm').style.display = 'none';
		  }

		  function runSurvey() {
		      var studentId = document.getElementById('studentId').value;
		      var url = "https://docs.google.com/forms/d/e/1FAIpQLScKBjj7xU8j5dHzjJcrYB-uA52gw1AZmPid_GndIjjdH-9LBA/viewform?entry.2095739304=" + studentId;
		      window.open(url, "_self")
		  }

		  function registerStudent(studentId) {

		      studentId = document.getElementById('studentId').value;
		      document.getElementById('loadingModal').style.display = "block";

		      w3Http("service/registerStudent?studentId=" + studentId, function() {

		          if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
		              alert(this.responseText);
		          }
		      });
		  }