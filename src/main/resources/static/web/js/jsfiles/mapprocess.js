$(document).ready(function () {

    $("#role").change(function () {
        $(".processes").prop('checked', false);
        $.ajax({
            type: "GET",
            url: "./getmappedprocess.htm",
            data: "roleid=" + $('#role').val(),
            success: function (data) {


                for (var n = 0; n < data.length; n++) {
                    $("#process" + data[n]).prop('checked', true);

                }


            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })

    $("#savemapping").click(function () {
        var params = [];
        var $boxes = $('input[name=processes]:checked');
        $boxes.each(function () {
            params.push({
                roleid: $("#role").val(),
                processid: $(this).val()
            });
        });
        var jsonString = encodeURIComponent(JSON.stringify(params));
//                        alert(jsonString)
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "./saveprocessmap.htm",
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

