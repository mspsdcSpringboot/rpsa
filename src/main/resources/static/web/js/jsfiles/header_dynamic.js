$(document).ready(function () {



    $('.autoresizing').on('input', function () {
        this.style.height = 'auto';

        this.style.height =
                (this.scrollHeight) + 'px';
    });

    $('#uappealtable').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#fappealtable').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#appealtable').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#pappealtable').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#forwardedappeals').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#directionslist').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#uappealtable4').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#uappealtable5').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#selfdisposed').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
    $('#forwardeddappeals').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );

    $('#uappealtable2').DataTable(
            {
                rowReorder: {
                    selector: 'td:nth-child(2)'
                },
                responsive: true
            }
    );
//    $('#uappealtable2').DataTable({
//        order: [[6, 'desc']]
//    });
    $('#audittrailtable').DataTable({
        order: [[0, 'desc']],
        responsive: true
    });

    $('#uappealtable3').DataTable({

        order: [[5, 'asc']],
        responsive: true

    });
    $('#paymenttable').DataTable({

        order: [[0, 'desc']],
        responsive: true

    });
    $('#comtable').DataTable({

        order: [[5, 'asc']],
        responsive: true

    });


    $('#fpappealtable').DataTable(
            {
                responsive: true
            }
    );
    $('#alertstable').DataTable(
            {
                responsive: true
            }
    );

    $("#trackspapplications").click(function () {
        $('#trackspapplicationsmodal').modal('toggle');
    })

    $(".savedoaction").click(function () {
        if (confirm("Are you sure you are done?")) {
            var appealcode = $(this).attr('id');
//            alert(appealcode)
//            alert()
            var remarks = $("#remarks_" + appealcode).val();
            if (remarks.length == 0) {
                alert("Please Enter Remarks")
                $("#remarks_" + appealcode).focus()
            } else {
//                alert(remarks)
                var csrfHeader = $("meta[name='_csrf_header']").attr("content");
                var csrfToken = $("meta[name='_csrf']").attr("content");
                $.ajax({
                    type: "POST",
                    url: "./savedoaction.htm",
                    data: "appealcode=" + appealcode + "&remarks=" + remarks,
                    beforeSend: function (xhr)
                    {
                        xhr.setRequestHeader(csrfHeader, csrfToken);
                    },
                    success: function (data) {

                        if (data != '-1') {
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
            }

        } else {

            return false;
        }
    });


    $("#trackmodalspbtn").click(function () {

        var dateArray = $("#submissionDate").val().split("-")
        var convertdate = dateArray[2] + "/" + dateArray[1] + "/" + dateArray[0]

        window.open("viewapplicationdetails.htm?referenceNo=" + $("#referenceNo").val().trim() + "&submissionDate=" + convertdate)




    });




});

