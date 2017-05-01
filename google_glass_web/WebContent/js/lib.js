

populateData();
var myVar = setInterval(populateData, 2000);
function populateData(){
	w3Http("service/getFeedback", function () {
	    if (this.readyState == 4 && (this.status == 204||this.status == 200)) {
	    	var feedbackObject = JSON.parse(this.responseText);
	    	for(var i in feedbackObject){
	    		setLHSData(feedbackObject[i].metric, feedbackObject[i].lhs);
	    		setRHSData(feedbackObject[i].metric, feedbackObject[i].rhs);
	    		setCount(feedbackObject[i].metric, feedbackObject[i].count)
	    	}
	    	
	    }
	});
}
function setCount(metric, count){
	var counterElement = document.getElementById(metric+"_header");
	var headerValue = counterElement.innerHTML;
	counterElement.innerHTML = headerValue.substring(0, headerValue.indexOf('(')+1) + count +")";
}
function setLHSData(metric, value){
	var lhsElement = document.getElementById(metric+"_lhs");
	value = value.replace(".0", "");
	lhsElement.innerHTML = (value=='0%'?'-':value);
	lhsElement.style.width = value;
}

function setRHSData(metric, value){
	var rhsElement = document.getElementById(metric+"_rhs");
	value = value.replace(".0", "");
	rhsElement.innerHTML = (value=='0%'?'-':value); 
	rhsElement.style.width = value;
}
function act(studentId){
    
    var actPane = document.getElementById('actPane');
    //document.getElementById('loadingModal').style.display = "block";
    
    w3Http("service/act", function () {
    	actPane.innerHTML = "Acting...";
        if (this.readyState == 4 && (this.status == 200 || this.status == 204)) {
        	actPane.innerHTML = "Act Here!";

        }
    });
    }