$(document).ready(function () {
    if ($('#subservicecodeedit').val() != "") {
        $('#subservice').val($('#subservicecodeedit').val());
        $.ajax({
            type: "GET",
            url: "./getmappedsad.htm",
            data: "subservicecode=" + $('#subservice').val(),
            success: function (data) {
                if (data.length > 0) {
                    $("input:radio[name=appelateradio][value=" + data[0][0] + "]").prop("checked", true);
                    for (var n = 0; n < data.length; n++) {
                        $("#office" + data[n][1]).prop('checked', true);

                    }
                } else {
                    alert("Not Mapped")
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    }

    $("#subservice").change(function () {
       
        $(".office").prop('checked', false);
        $(".appelate").prop('checked', false);

        $.ajax({
            type: "GET",
            url: "./getmappedsad.htm",
            data: "subservicecode=" + $('#subservice').val(),
            success: function (data) {
                if (data.length > 0) {
                    $("input:radio[name=appelateradio][value=" + data[0][0] + "]").prop("checked", true);
                    for (var n = 0; n < data.length; n++) {
                        $("#office" + data[n][1]).prop('checked', true);

                    }
                } else {
                    alert("Not Mapped")
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })

    $("#savemapping").click(function () {
        var params = [];
        var $boxes = $('input[name=office]:checked');
        var appelateid = $("input[name=appelateradio]:checked").val();
//        alert(appelateid)

        $boxes.each(function () {
//            alert($(this).val())
            params.push({
                appelateid: appelateid,
                subservicecode: $("#subservice").val(),
                officeid: $(this).val()
            });
        });
        var jsonString = encodeURIComponent(JSON.stringify(params));
//                        alert(JSON.stringify(params))
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "./saveappelatemap.htm",
            data: "datas=" + jsonString,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Saved")
                    location.reload();

                } else {
                    alert("Error")
                }


            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })




})
function savemapping() {

}

