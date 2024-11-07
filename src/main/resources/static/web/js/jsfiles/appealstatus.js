$(document).ready(function () {
    $('#firstappeal').hide();
    $('#secondappeal').hide();
    $('#fileappeal2').hide();
    $('#recall').hide();

    if ($('#appeallevel').val() === '1') {

        $('#firstappeal').show();
        $('#secondappeal').hide();
        if ($('#statusid').val() == '5' || $('#statusid').val() == '3') {
            if ($('#roleid').val() === '0') {
                $('#fileappeal2').show();
            }


        }
        if ($('#daysleft').val() < '0') {
            if ($('#roleid').val() === '0') {
                $('#fileappeal2').show();
            }
        }
        if ($('#roleid').val() === '2') {
            if ($('#statusid').val() == '1') {
                $('#recall').show();
            } else {
                $('#recall').hide();
            }
        } else {
            $('#recall').hide();
        }





    } else {
        $('#firstappeal').show();
        $('#secondappeal').show();

    }



    $('#forwarddo').click(function () {
        $('#forwarddomodal').modal('show');
    })



    $('#recall').click(function () {
        if (confirm("Are you sure you want to Recall this Appeal?")) {
            // Get the appeal code from the hidden input
            var appealcode = $("#appealcode").val();
            alert(appealcode);

            // Prepare the CSRF token if needed
//            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//            var csrfToken = $("meta[name='_csrf']").attr("content");

            // Perform the AJAX call
            $.ajax({
                type: "POST",
                url: "/secure/recallAppeal",
                data: { appealcode : appealcode },
                success: function (data) {
                    alert(data);
                    location.href = '/secure/appealstatus/' + appealcode;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("An error occurred: " + textStatus + " - Exception: " + errorThrown);
                }
            });
        } else {
            // User canceled the recall action
            console.log("Recall action was canceled.");
        }
    });


    $('#submitbtn').click(function () {
        if (confirm("Are you sure you want to forward this Appeal?")) {
            var appealcode = $('#appealcode').val();
            var dosub = $('#dosub').val();

//            alert(appealcode);

            $.ajax({
                type: "POST",
                url: "/secure/forwarddoappeal",  // Adjust the URL based on your mapping
                data: {
                    appealcode: appealcode,
                    usercode: dosub
                },
                success: function (data) {
                    alert(data);
                    location.href = '/secure/appealstatus/' + appealcode;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("error:" + textStatus + " - exception:" + errorThrown);
                    console.error("Error details:", jqXHR.responseText);  // Log additional details for troubleshooting
                }
            });
        }
    });






});
