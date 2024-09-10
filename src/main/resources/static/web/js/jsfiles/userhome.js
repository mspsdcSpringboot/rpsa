


$(document).ready(function () {
//                alert("rs")
    $('#fileappeal').click(function () {
        window.location.href = 'fileappeal.htm';
    });
    $('#inbox').click(function () {
        window.location.href = 'uinbox.htm';
    });

    $('#showmodal').click(function () {

        $('#exampleModal').modal('toggle')
    })

    $('#hidemodal').click(function () {
        $('#exampleModal').modal('hide')
    })

    $('#getappealstatus').click(function () {

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "GET",
            url: "./getappealstatus.htm",
            data: "appealcode=" + $("#appealcode").val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data != '-1') {
//                            alert("Appeal exist");
                    window.location.href = "appealstatus.htm?appealcode=" + data;
                } else {
                    alert("Appeal does not Exists");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

});




