$(document).ready(function () {

    $('.updatestatus').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var applicationrefno = $(this).attr('id');
        var submissiondate = $(this).prev().val();

        // Show fullscreen loader
        $('#loaderOverlay').show();

        $.ajax({
            type: "GET",
            url: "./updatespapplication.htm",
            data: "referenceNo=" + applicationrefno + "&submissionDate=" + submissiondate,
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data === "1") {
                    alert("Updated Successfully");
                } else {
                    alert("Error");
                }
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            },
            complete: function () {
                // Hide fullscreen loader when the AJAX request is complete
                $('#loaderOverlay').hide();
            }
        });
    });







    $('#servicewisetable2').DataTable({
        "bJQueryUI": true,
        order: [[1, 'asc']]
//        paging: false
//                    "sPaginationType": "full_numbers",




    });
});




  