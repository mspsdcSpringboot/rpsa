$(document).ready(function () {


    document.getElementById('submitprocessbtn').addEventListener('click', function(event) {
            // Prevent the default form submission
            event.preventDefault();

            const submitButton = document.getElementById('submitprocessbtn');
            submitButton.disabled = true;
            submitButton.innerHTML = "Processing...";

            // Get the form element
            const form = document.getElementById('fprocessappeal');
            var appealCode = document.getElementById('appealCodes').value;


            // Create a FormData object from the form
            const formData = new FormData(form);

            // Convert the FormData into a plain object
            const dataObject = {};
            formData.forEach((value, key) => {
                dataObject[key] = value;
            });

            // Log the form data object to the console in a formatted manner
            console.log("Appeal Form Data -", dataObject);
            console.log("Appeal Code -", appealCode);


            alert(dataObject);
            alert(appealCode);


            $.ajax({
                type: 'POST',
                url: '/secure/fProcessAppeal/' + appealCode,
                data: formData,
                processData: false, // Important for FormData
                contentType: false, // Important for FormData
                success: function(data) {
                    window.location.href = '/secure/appealProcessSuccess';

                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.error('Error:', textStatus, errorThrown);
                    alert('There is a problem problem in process the appeal !');
                }
            });

        });





    $("#hearingendtime1").blur(function () {
        var starttimestr = $("#hearingtime1").val()
        starttimestr = starttimestr.replace(":", "")
        var endtimestr = $("#hearingendtime1").val()
        endtimestr = endtimestr.replace(":", "")

        var starttime = parseInt($("#hearingtime1").val())
        var endtime = parseInt($("#hearingendtime1").val())
        if (starttime >= endtime) {
            alert("End Time should be Greater than Start time")
            $("#hearingendtime1").val("")
        }
    })




    $('#rejected').hide();

    $('#link1').hide();

    $('.callforhearingaction').hide();

    $('#rejectaction').hide();

    $('#offline').hide();



    $('.callforhearingaction').hide();
    $('#rejectaction').hide();
    var hdate = new Date($("#hearingdate").val());
//                alert($('input[name="forwardactioncode"]:checked').val())
    if ($('input[name="forwardactioncode"]:checked').val()) {
//                    alert("value")

        if ($('input[name="forwardactioncode"]:checked').val() == '1') {
            if ($("#hearingdate").val() == '' || hdate > new Date()) {
                $('.dontdispose').show();
                $('.dispose').hide();
                $('.dontdisposelabel').show();
                $('.disposelabel').hide();
                $('.callforhearingaction').show();
                $('#rejectaction').hide();
                if ($("#hearingtype").val() == '2') {
                    $('#link1').show();
                } else {
                    $('#link1').hide();
                }

            } else if (hdate < new Date()) {
                $('.dontdispose').hide();
                $('.dispose').show();
                $('.dontdisposelabel').hide();
                $('.disposelabel').show();
                $('#rejectaction').show();

            }


        } else if ($('input[name="forwardactioncode"]:checked').val() == '2' || $('input[name="forwardactioncode"]:checked').val() == '3') {
            $('.dontdispose').hide();
            $('.dispose').hide();
            $('.dontdisposelabel').hide();
            $('.disposelabel').hide();
            $('.callforhearingaction').hide();
            $('#rejectaction').hide();
            $('#submitprocessbtn').hide();
            $('#rejected').show();


        }


    } else {
        $('.dontdispose').show();
        $('.dispose').show();
        $('.dontdisposelabel').show();
        $('.disposelabel').show();
        $('.callforhearingaction').hide();
        $('#rejectaction').hide();
    }




    $('.actions').change(function () {

        var a = $('input[name="forwardactioncode"]:checked').val();
//                    alert(a)
        if (a == '1') {
            $('.callforhearingaction').show();
            $('#rejectaction').hide();
            if ($("#hearingtype").val() == '1') {
                $('#offline').show();
            } else {
                $('#offline').hide();
            }

        } else if (a == '2') {

            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#selectremarks').hide();
            $('#remarks').show();

        } else if (a == '3') {

            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#selectremarks').show();
            $('#remarks').hide();
            $('#offline').hide();
        }

    });


    $('#selectremarks').change(function () {
        if ($('#selectremarks').val() == '0') {
            $('#remarks').show();
            $('#remarks').focus();
            $('#remarks').val("");
        } else {

            $('#remarks').hide();
            $('#remarks').val($('#selectremarks :selected').text());
        }
    })




});