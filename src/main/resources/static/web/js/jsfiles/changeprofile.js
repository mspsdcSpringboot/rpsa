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
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "/secure/updateprofile",
            data: userdetailsjson,
            contentType: "application/json",
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {

//                alert(data);
                if (data == 'username') {
                    alert("Username Changed, Please Login Using the new username");
                    location.href='/login';
                }else if (data == 'updated') {
                    alert(data);
                    location.reload();
                }else if (data == 'failed'){
                    alert(data);
                    location.reload();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });
});


