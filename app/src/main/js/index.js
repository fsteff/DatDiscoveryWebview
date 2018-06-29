const hyperdrive = require('hyperdrive')
const ram = require('random-access-memory')

logcat.log("test from js");
var storage = function (name) {
    return new ram();
}
const drive = hyperdrive(storage, "0393041397eee8a48dc452ba4544769b6d518233f10acb17c971703758fc408c");
drive.on('ready', function(){
    //var key = drive.discoveryKey;
    var key = "b3462ad8c9ddc312ab3749c2b52b88d504861c9c";
    discovery.join(key, function (channel) {
        logcat.log("got channel: " + channel);
    });
})
