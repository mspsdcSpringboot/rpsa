$(document).ready(function (){
//    alert('New')



    //To open the pop-up when clicked in apply

    $(document).on('click', '.btnmodal', function() {
        var slno = $(this).attr('id');
        console.log(slno);
        $('#exampleModal' + slno).modal('toggle');
    });




    //Redirect to external website for apply service

    $(document).on('click', '.btnapply', function () {
        var btnid = $(this).attr('id');
        var id = btnid.split("_");

        $.ajax({
            type: "GET",
            url: "/public/applybtnclick",
            data: { slno: id[1] },
            success: function (data) {
                // Handle success
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });


    //For Track Status

    $(document).on('click', '.trackbtn', function () {
        var id = $(this).attr('id');
        console.log("Button ID: " + id);  // Debugging

        var applicationRefNo = $("#track_" + id).val();
        console.log("Application Ref No: " + applicationRefNo);  // Debugging

        $.ajax({
            type: "GET",
            url: "./trackapplicationsp.htm",
            data: "applicationrefno=" + applicationRefNo,
            success: function (data) {
                console.log("Server Response: ", data);  // Debugging
                $("#span_" + id).html("<b>" + data + "</b>");
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log("AJAX Error: ", jqXHR);  // Debugging
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });


})