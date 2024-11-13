$(document).ready(function() {

    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('userpassword');
        const toggleIcon = document.getElementById('togglePassword');

        // Toggle the type attribute
        const type = passwordField.getAttribute('type') === 'password' ? 'text' : 'password';
        passwordField.setAttribute('type', type);

        // Toggle the icon
        toggleIcon.classList.toggle('bi-eye');
        toggleIcon.classList.toggle('bi-eye-slash');
    });

    // Function to reload captcha
    const reloadCaptcha = () => {
        $.ajax({
            url: "/refresh-captcha",
            method: "GET",
            success: function(response) {
                $('#hiddenCaptcha').val(response.hiddenCaptcha);
                $('#realCaptcha').attr('src', 'data:image/jpg;base64,' + response.realCaptcha);
            },
            error: function() {
                $.confirm({
                    title: 'Error',
                    content: 'Something went wrong while loading the captcha.',
                    type: 'red',
                    buttons: {
                        OK: function () {}
                    }
                });
            }
        });
    };

    // Initial load of the captcha
    reloadCaptcha();

    // Bind the reloadCaptcha function to the button click
    $('.reloadCaptchaBtn').click(function(e) {
        e.preventDefault();
        reloadCaptcha();
    });

    // Check captcha function
    const checkCaptcha = () => {
        const captcha = $('#captcha').val();
        const hiddenCaptcha = $('#hiddenCaptcha').val();

        if (captcha !== hiddenCaptcha) {
            $('#captcha').val("");
            $.confirm({
                title: 'Error!',
                content: 'Captcha Does Not Match!',
                type: 'red',
                buttons: {
                    okay: {
                        text: 'Okay',
                        action: function() {}
                    }
                }
            });
            return false;
        } else {
            return true;
        }
    };

    $('#btnclick').click(function(e) {
        e.preventDefault();

        if(checkCaptcha()) {
            const loginCredentials = {
                username: $('#username').val(),
                password: $('#userpassword').val()
            };

            console.log(loginCredentials);

            const loginCredentialsJson = JSON.stringify(loginCredentials);
            console.log("User loginCredentials JSON", loginCredentialsJson);

            $.ajax({
                url: '/userlogin',
                type: 'POST',
                contentType: 'application/json',
                data: loginCredentialsJson,
                success: function (data) {
                    console.log("User Data Details - ", data.userrole.roleid)
                    var requestUrl = "/secure/home";

                    if(data.userrole.roleid == "3"){
                        requestUrl = "/secure/uinbox";
                    }
                    else if(data.userrole.roleid == "1"){
                        requestUrl = "/secure/mapprocess";
                    }
                    else if(data.userrole.roleid == "2"){
                        requestUrl = "/secure/aainbox";
                    }
                    else if(data.userrole.roleid == "0"){
                        requestUrl = "/secure/home";
                    }
                    else if(data.userrole.roleid == "8"){
                        requestUrl = "/secure/khadcdashboard";
                    }
                    else if(data.userrole.roleid == "5"){
                        requestUrl = "/secure/doinbox";
                    }
                    else if(data.userrole.roleid == "6"){
                        requestUrl = "/secure/aasoinbox";
                    }
                    else if(data.userrole.roleid == "10"){
                        requestUrl = "/secure/dosubhome";
                    }

                    window.location.href = requestUrl;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    if (jqXHR.status === 400) {
                        $.alert({
                            title: 'Error!',
                            content: "Username or password is incorrect",
//                            content: jqXHR.responseText,
                            type: 'red'
                        });
                    }
                    else if (jqXHR.status === 409) {
                        $.alert({
                            title: 'Password Expired!',
                            content: "Your password is expired. Please reset the password.",
                        //  content: jqXHR.responseText,
                            type: 'orange', // Warning style
                            buttons: {
                                ok: {
                                    text: 'Reset Password',
                                    action: function () {
                                        // Redirect to a new page after OK is clicked
                                        window.location.href = "/public/resetexpiredpassword"; // Change to your desired URL
                                    }
                                }
                            }
                        });
                    }
                     else {
                        $.alert({
                            title: 'Error!',
                            content: 'An error occurred: ' + textStatus,
                            type: 'red'
                        });
                    }
                }
            });
        } else {
            console.log("Captcha Does Not Match");
        }
    });
});

function getCookie(name) {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}
