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
    $.ajax(
        {
            type: "GET",
            data: $("#priceContainer"),
            cache: false,
            url: "/refresh/"+nightmode+"/"+currency+"/",
            success: function(fragment)
            {
                $("#priceContainer").replaceWith(fragment); // update snippet of page
            },
            error: function() //replacing individual sections with error message
            {
                var error = "<span id='btcPrice'><strong>ERROR</strong></span>"
                var btcCurrency = "<span id='btcCurrency'></span>"
                var btcChange = "<span id='btcChange' style='color:#FAA31B; font-size: 13vh;'>LOST</span>"
                var btcChangeBracelets = "<span id='btcChangeBracelets' style='color:#FAA31B; font-size: 13vh;'>CONNECTION</span>"

                $("#btcPrice").replaceWith(error);
                $("#btcCurrency").replaceWith(btcCurrency);
                $("#btcChange").replaceWith(btcChange);
                $("#btcChangeBracelets").replaceWith(btcChangeBracelets);
            }
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