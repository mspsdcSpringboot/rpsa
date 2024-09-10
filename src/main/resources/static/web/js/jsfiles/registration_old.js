// Function to check captcha validity
const checkCaptcha = () => {
    const captcha = document.getElementById("captcha");
    const hiddenCaptcha = document.getElementById("hiddenCaptcha");

    if (captcha.value !== hiddenCaptcha.value) {
        captcha.value = "";

        // Use jquery-confirm for displaying error message
        $.confirm({
            title: 'Error!',
            content: 'Captcha Does Not Match!',
            type: 'red',
            buttons: {
                OK: {
                    text: 'OK',
                    btnClass: 'btn-red',
                    action: function () {}
                }
            }
        });

        return false; // Return false to prevent form submission
    }

    return true; // Captcha matches, allow form submission
};

// Function to reload captcha
const reloadCaptcha = () => {
    const realCaptcha = document.getElementById("realCaptcha");
    const hiddenCaptcha = document.getElementById("hiddenCaptcha");

    $.ajax({
        url: "/refresh-captcha",
        method: "GET",
        dataType: "json",
    })
    .done(function(response) {
        hiddenCaptcha.value = response.hiddenCaptcha;
        realCaptcha.src = "data:image/jpg;base64," + response.realCaptcha;
    })
    .fail(function() {
        alert("Something went wrong.");
    });
};
$(document).ready(function () {


    // Bind reloadCaptcha function to click event of reloadCaptchaBtn
        $("#reloadCaptchaBtn").on("click", function(event) {
            event.preventDefault(); // Prevent default link behavior
            reloadCaptcha(); // Call reloadCaptcha function
        });


                alert("sjhsajdfhgjshdr")
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
//                 $(function () {
    $('.numbers').keyup(function () {
        if (this.value.match(/[^0-9 ]/g)) {
            this.value = this.value.replace(/[^0-9 ]/g, '');
        }
    });
    if (checkCaptcha()) {
    $("#btnclick").click(function () {
    alert("clicked")
        var secucode = $.trim($("#secucode").val());
//                alert(secucode)
        if (secucode.length == 0) {

            $("#secucode").focus();

            console.log("################Secure code issue")
            return false;
        }
//        else if (checkcaptcha(secucode) == 0) {
//            $("#err_secucode").show().fadeOut(3000);
//            $("#secucode").val("");
//            changeCaptcha();
//            return false;
//        }
        else {

            if ($('#fullname').val() == "") {
                $('#fullname').focus();


            } else if ($('#district').val() == "") {
                $('#district').focus();
            } else if ($('#password').val() == "") {
                $('#password').focus()
            } else if ($('#confirmpassword').val() == "") {
                $('#confirmpassword').focus()
            } else if ($('#mobile').val() == "") {
                $('#mobile').focus()
            } else if ($('#address').val() == "") {
                $('#address').focus()

            } else if ($('#lname').val() == "") {
                $('#lname').focus()

            } else {
//                var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//                var csrfToken = $("meta[name='_csrf']").attr("content");
//                var pass, get, slt;
//                var pwd = "";
//                pass = $('#password').val();
//                if ((pass.toString()).length == 0) {
//                    pwd = "";
//                } else {
//
//                    get = sha256_digest(pass);
//                    pwd = get;
//                }


                var userdetails;
                userdetails = {
                    fullname: $('#fullname').val(),
                    lname: $('#lname').val(),
                    mname: $('#mname').val(),
                    username: $('#email').val(),
                    userpassword: $('#password').val(),
                    contact: $('#mobile').val(),
                    address: $('#address').val(),
//                            aadhaar: $('#aadhaar').val(),
                    role: $('#role').val()
                };
                var userdetailsjson = encodeURIComponent(JSON.stringify(userdetails));
//                        alert(userdet/ailsjson);
                console.log(userdetailsjson)
                $.ajax({
                    type: "POST",
                    url: "./saveregistration.htm",
                    data: "appjson=" + userdetailsjson,
                    beforeSend: function (xhr)
                    {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    success: function (data) {
                        var res = data;
                        if (res == '-1') {
                            alert("Error");
                        } else if (res == '-2') {
                            alert("Mobile Number Already Registered");
                        } else {

                            alert("Data has been saved successfully");
                            window.location.href = "registrationsuccess.htm?usercode=" + data;
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {

                        alert("error:" + textStatus + " - exception:" + errorThrown);
                    }
                });
            }
        }

    })

    $("#showconfirmpassword").click(function () {
        var newpw = document.getElementById("confirmpassword");
        if (newpw.type === "password") {
            newpw.type = "text";
        } else {
            newpw.type = "password";
        }
    })
    $(".changeCaptcha1").click(function () {

        changeCaptcha();
    });
    $(".changeCaptcha2").click(function () {
        changeCaptcha();
    });



});
function showpass() {
    var newpw = document.getElementById("password");
    if (newpw.type === "password") {
        newpw.type = "text";
    } else {
        newpw.type = "password";
    }
}



function checkcaptcha(seccode)
{
    var res;
    var param = jQuery("meta[name='_csrf_parameter']").attr("content") + "=" + jQuery("meta[name='_csrf']").attr("content") + "&seccode=" + seccode;

    jQuery.ajax({
        url: "getcaptcha.htm",
        dataType: 'json',
        data: param,
        async: false,
        success: function (data)
        {
            res = data;
//                        alert(data)
        },
        error: function (e) {
            alert(e.status);
        }
    });
    return res;
}
function changeCaptcha() {
    var src = "./jcaptcha.jpg?date=" + new Date();
    jQuery("#jcaptchaimg").attr("src", src);
}
}





