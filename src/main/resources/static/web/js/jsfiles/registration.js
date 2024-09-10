$(document).ready(function() {
            // Function to reload captcha
//            alert("hello")


            $("#err_secucode").hide().fadeOut(3000);


                $("#savebtn").attr("hidden", true);
                $("#confirmpassword").attr("readonly", true);
                $("#password").keyup(function () {
                    var password = $("#password").val();
                    $("#confirmpassword").val("");
            //                    $("#savebtn").attr("disabled", true);
                    $("#passwordmatch").text("").css("color", "red");
                    var flag = 0;
                    if (password.length > 8) {
                        $("#8chars").attr("src", "web/assets/bootstrap5/bootstrap_icons/check-circle-fill.svg");
                        flag++;
                    } else {
                        $("#8chars").attr("src", "web/assets/bootstrap5/bootstrap_icons/x-circle-fill.svg");
                    }
                    // If password contains both lower and uppercase characters, increase strength value.
                    if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
                        $("#1cap").attr("src", "web/assets/bootstrap5/bootstrap_icons/check-circle-fill.svg");
                        flag++;
                    } else {
                        $("#1cap").attr("src", "web/assets/bootstrap5/bootstrap_icons/x-circle-fill.svg");
                    }
                    // If it has numbers and characters, increase strength value.
                    if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) {
                        $("#1num").attr("src", "web/assets/bootstrap5/bootstrap_icons/check-circle-fill.svg");
                        flag++;
                    } else {
                        $("#1num").attr("src", "web/assets/bootstrap5/bootstrap_icons/x-circle-fill.svg");
                    }
                    // If it has one special character, increase strength value.
                    if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) {
                        $("#1special").attr("src", "web/assets/bootstrap5/bootstrap_icons/check-circle-fill.svg");
                        flag++;
                    } else {
                        $("#1special").attr("src", "web/assets/bootstrap5/bootstrap_icons/x-circle-fill.svg");
                    }
                    if (flag >= 4) {
                        $("#confirmpassword").attr("readonly", false);
                        $("#passwordalert").removeClass('alert-warning').addClass('alert-success');
                    } else {
                        $("#confirmpassword").attr("readonly", true);
                        $("#passwordalert").removeClass('alert-success').addClass('alert-warning');
                    }
                    $("#savebtn").attr("hidden", true);
                    // If it has two special characters, increase strength value.
            //                    if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/))
            //                        strength += 1
                    // Calculated strength value, we can return messages
                    // If value is less than 2
                });

                $("#confirmpassword").keyup(function () {
                    if ($("#confirmpassword").val() != $("#password").val()) {
                        $("#passwordmatch").text("Passwords do not match").css("color", "red");
                        $("#savebtn").attr("hidden", true);
                    } else {
                        $("#passwordmatch").text("Passwords matched").css("color", "green");
                        $("#savebtn").attr("hidden", false);
                    }
                });

                $('.numbers').keyup(function () {
                        if (this.value.match(/[^0-9 ]/g)) {
                            this.value = this.value.replace(/[^0-9 ]/g, '');
                        }
                    });

                    $("#showconfirmpassword").click(function () {
                            var newpw = document.getElementById("confirmpassword");
                            if (newpw.type === "password") {
                                newpw.type = "text";
                            } else {
                                newpw.type = "password";
                            }
                        })

                        function showpass() {
                            var newpw = document.getElementById("password");
                            if (newpw.type === "password") {
                                newpw.type = "text";
                            } else {
                                newpw.type = "password";
                            }
                        }




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


                //password encryption

               function encryptPassword(pass) {
                   if (pass.length === 0) {
                       return "";
                   } else {
                       var hashedPassword = CryptoJS.SHA256(pass).toString();
                       return hashedPassword;
                   }
               }



            $('#submitbtn').click(function(e) {

                e.preventDefault();

                var userDetails;

                if(checkCaptcha()){
                    userDetails = {
                        fullname: $('#fullname').val(),
                        lname: $('#lname').val(),
                        mname: $('#mname').val(),
                        username: $('#email').val(),
                        userpassword: encryptPassword($('#password').val()),
                        contact: $('#mobile').val(),
                        address: $('#address').val(),
                        role: $('#role').val()
                    };

                    console.log(userDetails)

                    var userDetailsJson = JSON.stringify(userDetails)

                    console.log("User Details JSON" ,userDetailsJson)

                    $.ajax({
                            url: '/register',
                            type: 'POST',
                            contentType: 'application/json',
                            data: userDetailsJson,
                            success: function (data) {
                                    var res = data;
                                    if (res == '-1') {
                                        $.confirm({
                                            title: 'Error',
                                            content: 'An error occurred while processing your request.',
                                            type: 'red',
                                            buttons: {
                                                OK: function () {}
                                            }
                                        });
                                    } else if (res == '-2') {
                                        $.confirm({
                                            title: 'Mobile Number Already Registered',
                                            content: 'The mobile number you entered is already registered.',
                                            type: 'orange',
                                            buttons: {
                                                OK: function () {}
                                            }
                                        });
                                    } else {
                                        $.confirm({
                                            title: 'Success',
                                            content: 'Data has been saved successfully.',
                                            type: 'green',
                                            buttons: {
                                                OK: function () {
                                                    window.location.href = "/";
                                                }
                                            }
                                        });
                                    }
                                },
                                error: function (jqXHR, textStatus, errorThrown) {
                                    $.confirm({
                                        title: 'Error',
                                        content: 'An error occurred: ' + textStatus + ' - ' + errorThrown,
                                        type: 'red',
                                        buttons: {
                                            OK: function () {}
                                        }
                                    });
                                }
                        });

                }
                else{
                 console.log("Captcha Does Not Match", userDetails)
                 }

            });


        });