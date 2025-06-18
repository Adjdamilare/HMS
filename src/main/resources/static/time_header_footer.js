function updateClock() {
    var now = new Date();
    var hours = now.getHours();
    var minutes = now.getMinutes();
    var seconds = now.getSeconds();

    // Add leading zeros if needed
    hours = (hours < 10 ? "0" : "") + hours;
    minutes = (minutes < 10 ? "0" : "") + minutes;
    seconds = (seconds < 10 ? "0" : "") + seconds;

    var timeString = hours + ":" + minutes + ":" + seconds;
    var clockElement = document.getElementById("clock");
    if (clockElement) {
        clockElement.innerHTML = timeString;
    }
}

// Update the clock every second
setInterval(updateClock, 1000);
// Initial call to display the clock immediately
updateClock();