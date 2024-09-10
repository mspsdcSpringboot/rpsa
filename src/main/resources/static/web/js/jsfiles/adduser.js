$(document).ready(function () {
    $('#userstable').DataTable();

    $('#savebtn').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        var pass, get, slt;
        var pwd = "";
        pass = makeid(8);
        if ((pass.toString()).length == 0) {
            pwd = "";
        } else {
            slt = $('#randomKey').val();
            get = sha256_digest(pass);
            pwd = get;
        }
        $("#randomKey").attr("disabled", "disabled");

        var userdetails;

        userdetails = {
            fullname: $('#fullname').val(),
            contact: $('#contact').val(),
            username: $('#username').val(),
            password: pwd,
            pw: pass,
            role: $('#role').val(),
            email: $('#email').val(),
            designation: $('#designation').val()
        };

        var userdetailsjson = encodeURIComponent(JSON.stringify(userdetails));
//                        alert(userdetailsjson);
        $.ajax({
            type: "POST",
            url: "./saveappelateuser.htm",
            data: "appjson=" + userdetailsjson,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data != '-1') {
                    alert("Data has been saved successfully");
                    location.reload();
                } else {
                    alert("Username Already Exists");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });

    $('.deactivateuser').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var usercode = this.id;

//        alert(usercode)
        $.ajax({
            type: "POST",
            url: "./deactivateuser.htm",
            data: "usercode=" + usercode,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Data has been saved successfully");
                    location.reload();
                } else {
                    alert("Error");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

    $('.activateuser').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
         var usercode = this.id;
//         alert(usercode)
        $.ajax({
            type: "POST",
            url: "./activateuser.htm",
            data: "usercode=" + usercode,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Data has been saved successfully");
                    location.reload();
                } else {
                    alert("Error");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

});





function makeid(length) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() *
                charactersLength));
    }
    return result;
}


