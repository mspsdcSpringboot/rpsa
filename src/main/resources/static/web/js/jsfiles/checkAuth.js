$(document).ready(function() {
    // Check if the authentication process has already been executed
    if (!localStorage.getItem('hasRun')) {
        console.log('Running authentication process...');
        alert('Authentication');
        var cookieToken = getCookie('authToken');
        console.log("Token from cookie######  ", cookieToken);

        if (cookieToken != null) {
            $.ajax({
                url: '/jwt/fetch-user', // Replace with your actual endpoint
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + cookieToken
                },
                success: function(data) {
                    var userData = JSON.stringify(data, null, 2);
                    console.log(headers);
                    console.log(userData);
                    localStorage.setItem('userData', userData); // Store user data in local storage

                    var userNameLocal = localStorage.getItem('userData'); // Retrieve data from local storage
                    if (userNameLocal) {
                        var username = userNameLocal.replace(/"/g, ""); // Remove quotes
                        console.log("#########UserName: " + username);

                        // Redirect to secure home page with username as query parameter
                        var requestUrl = "/secure/home?username=" + encodeURIComponent(username);
                        console.log('Request URL:', requestUrl);
                        window.location.href = requestUrl; // Redirect to the secure home page
                    } else {
                        window.location.href = "/login"; // Redirect if no user data found
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('Error fetching user data:', textStatus, errorThrown);
                    window.location.href = "/login"; // Redirect to login on error
                    console.log(headers);

                }
            });
        } else {
            // Redirect to login if no cookie token is found
            window.location.href = "/login";
                    console.log(headers);

        }

        // Set a flag in localStorage to indicate that the code has run
        localStorage.setItem('hasRun', 'true');
    }
});

// Function to get a cookie by name
function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
