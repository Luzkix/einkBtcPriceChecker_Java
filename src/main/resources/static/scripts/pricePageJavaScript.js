// getting clock (with time and minutes)
function getClock() {
    var today = new Date();
    function minutes(today) {
        return (today.getMinutes() < 10 ? '0' : '') + today.getMinutes();
    }
    var timeInMinutes = today.getHours() + ":" + minutes(today);
    document.getElementById("clock").innerHTML = "🕑" + timeInMinutes;
}

// displaying clock immediately when the page is loaded
getClock();

// getting fresh th:object "bitcoinObject" for refreshing priceContainer fragment
url = $(location).attr('href');
urlParts = url.split("/");
var nightmode = urlParts[urlParts.length-3];
var currency = urlParts[urlParts.length-2];

function getFreshBitcoinObject() {
    $.get("/refresh/"+nightmode+"/"+currency+"/").done(function(fragment) { // get from controller
        $("#priceContainer").replaceWith(fragment); // update snippet of page
    });
}

//refreshing page sections based on set interval
function refreshPageValues() {
    var actualSec = new Date().getSeconds();
    //console.log(actualSec);
    if (actualSec == 0 || /*actualSec == 10|| actualSec == 20||*/ actualSec == 30 /*|| actualSec == 40|| actualSec == 50*/) {
        getClock();
        getFreshBitcoinObject();
        console.log("fragments refreshed on: " + actualSec + "s");
    }
}
setInterval(refreshPageValues, 1000);

// reload whole page once per X msec (actually 24 hours) so the page receives new code updates automatically
setTimeout(function() {
    window.location.reload(false);
}, 86400000);