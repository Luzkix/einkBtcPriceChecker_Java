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
            error: function() //replacing individual sections with error message displayed to users directly on the screen in case something unexpected happens.
                // The app is still working even if this error message is displayed and will recover by itself when connection to db server is reestablished.
            {
                var error = "<span id='btcPrice'><strong>ERROR</strong></span>"
                var btcCurrency = "<span id='btcCurrency'></span>"
                var btcChange = "<span id='btcChange' style='color:#FAA31B; font-size: 13vh;'>LOST</span>"
                var btcChangePercentage = "<span id='btcChangePercentage' style='color:#FAA31B; font-size: 13vh;'>CONNECTION</span>"

                $("#btcPrice").replaceWith(error);
                $("#btcCurrency").replaceWith(btcCurrency);
                $("#btcChange").replaceWith(btcChange);
                $("#btcChangePercentage").replaceWith(btcChangePercentage);
            }
        }
        );
}

//refreshing page sections based on set interval (by default refreshing values every 30 seconds)
function refreshPageValues() {
    var actualSec = new Date().getSeconds();
    //console.log(actualSec);
    if (actualSec == 0 || actualSec == 30 ) {
        getClock();
        getFreshBitcoinObject();

        console.log("fragments refreshed on: " + actualSec + "s");

    }

}
setInterval(refreshPageValues, 1000);

// automatically reload whole page once per X msec (actually 24 hours) so the page receives new code updates automatically, without user intervention.
setTimeout(function() {
    window.location.reload(false);
}, 86400000);


// Dynamically adjust font size for bitcoin price to its maximum
// (e.g. make it smaller or bigger based on the length of the number and available screen space)
const fragmentWrapper = document.getElementById('fragmentWrapper');
const observer = new MutationObserver(setMaxFontSize);

observer.observe(fragmentWrapper, {
    childList: true
});

function setMaxFontSize() {

    const btcPriceId = fragmentWrapper.querySelector('#btcPrice');
    const btcPriceClass = document.getElementsByClassName('btcPriceBlock').item(0);

    // Get text length and width
    const text = btcPriceId.innerText;
    const len = text.length + 1; //+1 stands for $ sign
    const width = 100;

    // Calculate max font size
    const charWidth = 0.62;
    const maxFontSize = width / (len * charWidth);

    // Set btcPriceClassElement font size in vw units
    btcPriceClass.style.fontSize = maxFontSize + "vw";


}
// Set initial max font size when page loads
setMaxFontSize();


