$(document).ready(function () {
//                    $('#userstable').DataTable();

    $('#savebtn').click(function () {


        var userdetails;

        userdetails = {
            fullname: $('#fullname').val(),
            contact: $('#contact').val(),
            username: $('#username').val(),
            email: $('#email').val(),
            designation: $('#designation').val()
        };

        var userdetailsjson = JSON.stringify(userdetails);
//        alert(userdetailsjson);
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "./saveprofile.htm",
            data: "appjson=" + userdetailsjson,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data != '-1') {
                    alert("Profile Updated successfully");
                    location.reload();
                } else {
                    alert("Error");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });
});


