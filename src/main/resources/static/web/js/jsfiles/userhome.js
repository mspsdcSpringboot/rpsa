


$(document).ready(function () {
//                alert("rs")
    $('#fileappeal').click(function () {
        window.location.href = 'fileappeal.htm';
    });
    $('#inbox').click(function () {
        window.location.href = 'uinbox.htm';
    });

    $('#showmodal').click(function () {

        $('#exampleModal').modal('toggle')
    })

    $('#hidemodal').click(function () {
        $('#exampleModal').modal('hide')
    })

    $('#getappealstatus').click(function () {
        // Get the reference number from input
        var refno = $("#appealcode").val();

        // Basic validation to check if reference number is entered
        if (!refno) {
            alert("Please enter a reference number.");
            return;
        }
//        alert(refno);

        // AJAX call to retrieve appeal status
        $.ajax({
            type: "GET",
            url: "/secure/findAppealcode",
            data: { "refno": refno },
            contentType: "application/json",
            success: function (data) {
//             alert(data);
                if (data !== 'Appeal not found!') {
                    // Redirect to the appeal status page if appeal exists
                    window.location.href = "/secure/appealstatus/" + data;
                } else {
                    alert("Appeal does not exist.");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });


});




