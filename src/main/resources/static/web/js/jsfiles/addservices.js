$(document).ready(function () {



    $('#submitbtn').click(function () {
        var params = [];
        $('#subservicesgrid > tr').each(function (index, tr) {


            params.push({

                subservicecode: $(this).find("input[name='subservicecode']").val(),
                subservicename: $(this).find("input[name='subservicename']").val(),
                stipulatedtime: $(this).find("input[name='stipulatedtime']").val(),
                servicecode: $("#servicecode").val(),
                servicename: $("#servicename").val()


            });

        });
        var jsonString = JSON.stringify(params);

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

//        alert(jsonString);
        $.ajax({
            type: "POST",
            url: "./saveservice.htm",
            data: "sl_no" + sl_no,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data == '1') {
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
        row = "<tr><td><input name='subservicecode' hidden='true' class='form-control'/><input name='subservicename' class='form-control'/></td><td><input name='stipulatedtime' class='form-control'/></td></tr>";
        $("#subservicesgrid").append(row);

    });
    $('#delcontactbtn').click(function () {
        $("#subservicesgrid tr:last").remove();

    });
    $('.remove').click(function () {
        $(this).parent().parent().remove();

    });

    $("#service").change(function () {

 $("#subservicesgrid").html("");

        $.ajax({
            type: "GET",
            url: "./getsubservices.htm",
            data: "servicecode=" + $('#service').val(),
            success: function (data) {
                var row = "";
                $("#servicecode").val($('#service').val())
                $("#servicename").val($('#service :selected').text())
               
                for (var n = 0; n < data.length; n++) {
                    row = row + "<tr><td><input hidden='true' name='subservicecode' value='"+data[n].subservicecode+"' class='form-control'/><input name='subservicename' value='"+data[n].subservicename+"' class='form-control'/></td><td><input name='stipulatedtime' value='"+data[n].stipulatedtime+"' class='form-control'/></td></tr>";

                }
                $("#subservicesgrid").append(row);


            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })


});
