var browserType;
var timeoutID;
var xhr1, xhr2, xhr3, xhr4, xhr5, xhr6;

// send new messages to the chat box
function sendMessage() {
    var myform;
    var x = document.getElementById("bookid");
    var jobj = {
        "call": "sendMessage",
        "user": document.getElementById("user").value,
        "bookid": x.options[x.selectedIndex].value,
        "message": document.getElementById("message").value,
        "dialog": document.getElementById("dialog").value
    };
    console.log(jobj);
    xhr1 = createXHR();
    xhr1.onreadystatechange = sendMessageResponseHandler;
    myform = document.getElementById("chatForm");

    xhr1.open("POST", "sendMessage", true);
    xhr1.send(JSON.stringify(jobj));
}

function sendMessageResponseHandler() {
    // readyState of 4 signifies request is complete
    if (xhr1.readyState === 4) {
        // status of 200 signifies sucessful HTTP call
        if (xhr1.status === 200) {
            console.log(xhr1.responseText);
            var JSONresp = JSON.parse(xhr1.responseText);
            document.getElementById("message").value = "";
            //alert(JSONresp["dialog"].toString());
            document.getElementById("dialog").value = JSONresp["dialog"];
            timeoutID = setTimeout(checkMessage, 3000);
        }
    }
}

//check new message, put new message in the chat box every 3 second
function checkMessage() {
    var jobj = {
        "call": "checkMessage",
        "user": document.getElementById("user").value,
        "dialog": document.getElementById("dialog").value
    };
    xhr3 = createXHR();
    xhr3.onreadystatechange = checkMessageResponseHandler;
    xhr3.open("POST", "checkMessage", true);
    xhr3.send(JSON.stringify(jobj));
}

function checkMessageResponseHandler() {
    // readyState of 4 signifies request is complete
    if (xhr3.readyState === 4) {
        // status of 200 signifies sucessful HTTP call
        if (xhr3.status === 200) {
            console.log(xhr3.responseText);
            var JSONresp = JSON.parse(xhr3.responseText);
            if (JSONresp["status"] === "DONE") {
                clearTimeout(timeoutID);
            }
            else {
                timeoutID = setTimeout(checkMessage, 3000);
            }
            document.getElementById("dialog").value = JSONresp["dialog"];
        }
    }
}

function buildComplexJSON(element) {
    var ljobj = {
        "call": "checkChat"};
    for (i = 0; i < element.length; i++) {
        var name = element[i].name;
        if (element[i].type === "select-multiple") {
            var llen = element[i].length;
            j = 0;
            chosen = "[";
            for (j = 0; j < llen; j++) {
                if (element[i].options[j].selected) {
                    chosen += element[i].options[j].value + ", ";
                }
            }
            chosen = chosen.substring(0, chosen.length - 2) + "]";
            ljobj[name] = chosen;
        }
        else if (element[i].type === "radio") {
            if (element[i].checked === true)
                ljobj[name] = element[i].value;
        }
        else if (element[i].type === "checkbox") {
            if (element[i].checked === true)
                ljobj[name] = element[i].value;
        }
        else {
            ljobj[name] = element[i].value;
        }
    }
    return ljobj;
}

function createXHR() {
    // This function creates the correct form of the XMLHttpRequestObject based on the browser
    var XHR;
    if (window.XMLHttpRequest) { // Mozilla, Safari,...
        XHR = new XMLHttpRequest();
        if (XHR.overrideMimeType) {
            XHR.overrideMimeType('text/html');
        }
        browserType = "Mozilla";
        return XHR;
    } //end mozilla attempt
    if (window.ActiveXObject) { // IE
        try {
            XHR = new ActiveXObject("Msxml2.XMLHTTP");
            browserType = "IE";
            return XHR;
        }
        catch (e) {
            try {
                XHR = new ActiveXObject("Microsoft.XMLHTTP");
                browserType = "IE";
                return XHR;
            }
            catch (e) {
                alert("Your browser does not support AJAX!");
                browserType = "Unknown";
                return null;
            }
        }
    }//end IE attempt
    alert("Your browser does not support AJAX!");
    return null;
}
