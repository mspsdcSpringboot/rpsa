$(document).ready(function () {

    $('.submitActiveDuration').click(function (e) {
        e.preventDefault();
        alert($("#duration").val());


        $.ajax({
            type: "POST",
            url: "/secure/updateactiveduration/" + $("#duration").val(),
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