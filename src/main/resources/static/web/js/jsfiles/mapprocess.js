$(document).ready(function () {
    // Initial alert for debugging purposes
//    alert("Hello");


    // Event listener for the role dropdown change
    $("#role").change(function () {
        // Uncheck all checkboxes
        $(".processes").prop('checked', false);

        // Get selected role value
        var selectedRole = $('#role').val();
//        alert(selectedRole);

        // AJAX call to fetch the mapped processes for the selected role
        $.ajax({
            type: "GET",
            url: "/secure/getmappedprocess",
            data: { roleid: selectedRole }, // Send the role ID as data
            success: function (data) {

                // Iterate through the received data (assuming it's an array of process IDs)
                for (var n = 0; n < data.length; n++) {
                    // Check the checkbox corresponding to the process ID
                    $("#process" + data[n]).prop('checked', true);
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });

    // Event listener for the save button
    $("#savemapping").click(function () {
        var params = [];
        var $boxes = $('input[name=processes]:checked');

        // Check if any boxes are selected
        if ($boxes.length === 0) {
            console.log("No checkboxes are selected.");
        } else {
            console.log($boxes.length + " checkboxes are selected.");
        }

        $boxes.each(function () {
            var roleId = $("#role").val();
            var processId = $(this).val();

            // Debugging: Check if roleId and processId are valid
            console.log("Role ID: " + roleId);
            console.log("Process ID: " + processId);

            if (roleId && processId) {
                params.push({
                    roleid: roleId,
                    processid: processId
                });
            } else {
                console.log("Either role ID or process ID is missing.");
            }
        });



        // Convert params array to JSON string and encode it
//        var jsonString = encodeURIComponent(JSON.stringify(params));
//        alert(params);

        var jsonString = JSON.stringify(params);
//        alert(jsonString);
        // CSRF token handling
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");

        // AJAX POST request to save the mapping
        $.ajax({
            type: "POST",
            url: "/secure/saveprocessmap",
            contentType: "application/json", // Set the content type to JSON
            data: jsonString, // Set content type
//            beforeSend: function (xhr) {
//                // Add CSRF token to the request headers
//                if (csrfHeader && csrfToken) {
//                    xhr.setRequestHeader(csrfHeader, csrfToken);
//                }
//            },
            success: function (data) {
                if (data == 'updated') {
                    $.confirm({
                        title: 'Updated!',
                        content: 'Updated successfully.',
                        type: 'green',
                        buttons: {
                            ok: function () {
                                location.reload(); // Reload the page after saving
                            }
                        }
                    });
                } else {
                    $.confirm({
                        title: 'Error!',
                        content: 'Unable to update, Please try again',
                        type: 'red',
                        buttons: {
                            ok: function () {
                                // Optionally, you can add actions here
                            }
                        }
                    });
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });
});


