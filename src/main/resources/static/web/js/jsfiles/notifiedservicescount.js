$(document).ready(function () {

    $('.submitNotifiedServiceCount').click(function (e) {
        e.preventDefault();
//        alert($("#scount").val());


        $.ajax({
            type: "POST",
            url: "/secure/addnotifiedservicecount/" + $("#scount").val(),
            success: function (data) {
                alert(data);
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

});
