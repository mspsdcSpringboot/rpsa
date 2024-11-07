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


    // Function to encrypt the password using CryptoJS
    function encryptPassword(pass) {
        if (pass.length === 0) {
            return ""; // Return empty string if the password is empty
        } else {
            // Hash the password using SHA256
            return CryptoJS.SHA256(pass).toString();
        }
    }

    // Event handler for the save button click
    $('#savebtn').click(function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Collect user details from the form
        var userdetails = {
            username: $('#username').val().trim(),
            currentpassword: encryptPassword($('#currentpassword').val()),
            newpassword: encryptPassword($('#newpassword').val()),
            confirmnewpassword: encryptPassword($('#confirmnewpassword').val()), // Fix here
            // salt: $('#randomKey').val(), // Uncomment if needed
        };

        var newpassword1 = $('#newpassword').val();
        var confirmnewpassword1 = $('#confirmnewpassword').val();

        // Input validation
        if (!userdetails.username || !userdetails.currentpassword ||
            !userdetails.newpassword || !userdetails.confirmnewpassword) {
            alert("All fields are required.");
            return;
        }

        // Check if new password matches confirm new password
        if (newpassword1 !== confirmnewpassword1) {
            alert("New Password and Confirm New Password do not match.");
            return;
        }

        // Convert user details to JSON
        var userdetailsjson = JSON.stringify(userdetails);
        alert(userdetailsjson); // For debugging

        // AJAX request to send the data to the server
        $.ajax({
            type: "POST",
            url: "/public/savenewpassword",
            data: userdetailsjson,
            contentType: "application/json",
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(csrfHeader, csrfToken); // Set CSRF token in headers
            // },
            success: function (data) {
                alert(data); // Show response from the server
                location.reload(); // Reload the page upon success
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
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




