$(document).ready(function () {


    $("#subservice").change(function () {

        $(".users").prop('checked', false);

        $.ajax({
            type: "GET",
            url: "./getmappednewdepartment.htm",
            data: "subservicecode=" + $('#subservice').val(),
            success: function (data) {
                if (data.length > 0) {
                    $("input:radio[name=department][value=" + data[0][1] + "]").prop("checked", true);

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
       

        var usercode = $("input[name=users]:checked").val();
//        alert(appelateid)


//        params.push({
//            usercode: usercode,
//            subservicecode: $("#subservice").val(),
//            
//        });
 var params = {
            usercode: usercode,
            subservicecode: $("#subservice").val()
            
        };

        var jsonString = encodeURIComponent(JSON.stringify(params));
                        alert(JSON.stringify(params))
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "./savedosubmap.htm",
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

