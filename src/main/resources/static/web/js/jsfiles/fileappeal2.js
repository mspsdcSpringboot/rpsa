$(document).ready(function () {
    $('#submitbtn').attr('disabled', true);
    $('#declaration').click(function () {
        if ($('#declaration').is(':checked')) {
            $('#submitbtn').attr('disabled', false);
        } else {
            $('#submitbtn').attr('disabled', true);
        }
    });

    $("#adddoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#adddoc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });


    document.getElementById('submitbtn').addEventListener('click', function(event) {
            // Prevent the default form submission
            event.preventDefault();

            // Get the form element
            const form = document.getElementById('appeal2');

            // Create a FormData object from the form
            const formData = new FormData(form);

            // Collect the data from FormData into a plain object (optional)
            const dataObject = {};
            formData.forEach((value, key) => {
                dataObject[key] = value;
            });

            console.log("Appeal Form Data Object - ", formData);
            console.log("Appeal Form - ", form);
            console.log("Appeal Form Data - ", dataObject);

    //        var hello = "Helloooo"

            // Optionally, send the data to the server via AJAX (as shown in the previous example)
//            $.ajax({
//                type: 'POST',
//                url: '/secure/appealSubmission',
//                data: formData,
//                processData: false, // Important for FormData
//                contentType: false, // Important for FormData
//                success: function(data) {
//                    console.log('Success:', data);
//                    alert(data);
//                    $('#appealform')[0].reset();
//    //                window.location.href = '/secure/initiatepayment';
//
//                    var pathVar = data;
//                    $.ajax({
//                        type: 'GET',
//                        url: '/secure/initiatepayment/' + pathVar,
//                        success: function(response) {
//
//                            window.location.href = '/secure/initiatepayment/' + pathVar;
//                        },
//                        error: function(jqXHR, textStatus, errorThrown) {
//                            console.error('Second request error:', textStatus, errorThrown);
//                            alert('There was a problem initiating the payment.');
//                        }
//                    });
//                    // Optionally reset the form or redirect
//                    $('#appealform')[0].reset();
//
//                },
//                error: function(jqXHR, textStatus, errorThrown) {
//                    console.error('Error:', textStatus, errorThrown);
//                    alert('There was a problem submitting your appeal.');
//                }
//            });
        });
});
