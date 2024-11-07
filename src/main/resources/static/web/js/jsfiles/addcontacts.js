$(document).ready(function () {



    $('#submitbtn').click(function () {
        var params = [];
        $('#contactsgrid > tr').each(function (index, tr) {
           

            params.push({

                contactid: $(this).find("input[name='contactid']").val(),
                contactname: $(this).find("input[name='contactname']").val(),
                designation: $(this).find("input[name='designation']").val(),
                phone: $(this).find("input[name='phone']").val(),
                email: $(this).find("input[name='email']").val()  

            });

        });
        var jsonString = JSON.stringify(params);
        alert(jsonString)

//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");

//        alert(jsonString);
        $.ajax({
            type: "POST",
            url: "/secure/savecontacts",
            data: jsonString,
            contentType: 'application/json',
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                if (data == 'updated') {
                    alert("Saved")
                    location.reload()
                } else {
                    alert("Error")
                }


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('#addcontactbtn').click(function () {
        var row = "";
        row = "<tr><td><input name='contactid' class='form-control'/></td><td><input name='contactname' class='form-control'/></td><td><input name='designation' class='form-control'/></td><td><input name='phone' class='form-control'/></td><td><input name='email' class='form-control'/></td></tr>";
        $("#contactsgrid").append(row);

    });
    $('#delcontactbtn').click(function () {
        $("#contactsgrid tr:last").remove();

    });
    $('.remove').click(function () {
        $(this).parent().parent().remove();

    });


});
