$(document).ready(function () {
    $('#savebtn').attr("disabled", true)
    $("#newpassword").keyup(function () {
        $("#confirmnewpassword").val("")
        $('#savebtn').attr("disabled", true)
        $("#pmsg").text("");
    })
    $("#confirmnewpassword").keyup(function () {
        if ($("#confirmnewpassword").val() != $("#newpassword").val()) {
            $("#pmsg").css("color", "red");
            $("#pmsg").text("Passwords do not match");
            $('#savebtn').attr("disabled", true)
        } else {
            $("#pmsg").css("color", "green");
            $("#pmsg").text("Passwords match");
            $('#savebtn').attr("disabled", false)
        }

    });

    $('#savebtn').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        var pass, get, slt;
        var pwd = "";
        pass = $('#currentpassword').val();
        if ((pass.toString()).length == 0) {
            pwd = "";
        } else {
            slt = $('#randomKey').val();
            get = sha256_digest(sha256_digest(pass) + slt);
            $('#currentpassword').val(get);
        }

        pass = $('#newpassword').val();
        if ((pass.toString()).length == 0) {
            pwd = "";
        } else {
            slt = $('#randomKey').val();
            get = sha256_digest(pass);
            $('#newpassword').val(get);
        }

        pass = $('#confirmnewpassword').val();
        if ((pass.toString()).length == 0) {
            pwd = "";
        } else {
            slt = $('#randomKey').val();
            get = sha256_digest(pass);
            $('#confirmnewpassword').val(get);
        }
        $("#randomKey").attr("disabled", "disabled");

        var userdetails;

        userdetails = {
            usercode: $('#usercode').val(),
            username: $('#username').val(),
            currentpassword: $('#currentpassword').val(),
            newpassword: $('#newpassword').val(),
            confirmnewpassword: $('#confirmnewpassword').val(),
            salt: $('#randomKey').val(),
        };

        var userdetailsjson = JSON.stringify(userdetails);
//                                                alert(userdetailsjson);
        $.ajax({
            type: "POST",
            url: "./savenewpassword.htm",
            data: "appjson=" + userdetailsjson,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Data has been saved successfully");
                    location.reload();
                } else if (data == '2') {
                    alert("Current Password is not Correct");
                } else {
                    alert("Error While Saving");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });
    $('#showpass').click(function () {
        var newpw = document.getElementById("newpassword");
        var oldpw = document.getElementById("currentpassword");
        var confirmpw = document.getElementById("confirmnewpassword");
        if (newpw.type === "password") {
            newpw.type = "text";
            oldpw.type = "text";
            confirmpw.type = "text";
        } else {
            newpw.type = "password";
            oldpw.type = "password";
            confirmpw.type = "password";
        }
    })

});




