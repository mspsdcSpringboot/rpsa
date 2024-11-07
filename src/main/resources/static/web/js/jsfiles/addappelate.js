$(document).ready(function () {

    // Handle click event for editing an appellate
    $(document).on('click', '.appelateedit', function (e) {
        e.preventDefault();
        var appelateId = $(this).attr('id');

        // Fetch the appellate details
        $.ajax({
            type: "GET",
            url: "/secure/getappelate/" + appelateId,
            success: function (appelateData) {
                console.log(appelateData);

                // Populate modal fields with appellate data
                $('#modalAppelateId').val(appelateData.appelateid);
                $('#modalOfficeName').val(appelateData.officename);
                $("input:radio[name=officelevel][value=" + appelateData.officelevel + "]").prop("checked", true);

                // Fetch the department list
                $.ajax({
                    type: "GET",
                    url: "/secure/getdept",
                    success: function (departments) {
                        console.log(departments); // Log department data to inspect structure

                        // Clear existing options and add a default option
                        $('#modalDepartmentCode').empty().append('<option value="">Select</option>');

                        // Iterate over the department list and append options
                        $.each(departments, function (index, department) {
                            $('#modalDepartmentCode').append(
                                $('<option>', {
                                    value: department.departmentcode,  // Set department code as value
                                    text: department.departmentname    // Set department name as display text
                                })
                            );
                        });

                        // Auto-select the department code that matches the one from appellateData
                        $('#modalDepartmentCode').val(appelateData.departmentcode.departmentcode); // Select the matching department
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Error loading departments: " + textStatus + " - Exception: " + errorThrown);
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error loading appellate: " + textStatus + " - Exception: " + errorThrown);
            }
        });

        // Show the edit appellate modal
        $('#editAppelateModal').modal('show');
    });


    //Update the appellate authority
    $(document).on('click', '#updateAppellateAuthority', function (e) {

//            alert('Please')
            e.preventDefault(); // Prevent the default form submission

            // Collect data from the form
            var appelateId = $('#modalAppelateId').val();
            var appelateName = $('#modalOfficeName').val();
            var officeLevel = $('input[name=officelevel]:checked').val();
            var departmentCode = $('#modalDepartmentCode').val();

            // Prepare data object for the AJAX request
            var appellateData = {
                appelateid: appelateId,
                appelatename: appelateName,
                officelevel: officeLevel,
                departmentcode: departmentCode
            };


            console.log(appellateData); // Log data to console for debugging

            // Perform the AJAX request
            $.ajax({
                type: "POST",
                url: "/secure/updateappelate",
                contentType: 'application/json', // Specify that data is JSON
                data: JSON.stringify(appellateData), // Convert data to JSON
                success: function (data) {
                    if (data === 'updated') {
                        $.confirm({
                            title: 'Success!',
                            content: 'Updated Successfully',
                            type: 'green',
                            typeAnimated: true,
                            buttons: {
                                ok: {
                                    text: 'OK',
                                    btnClass: 'btn-success',
                                    action: function () {
                                        $('#editAppelateModal').modal('hide');
                                        location.reload();
                                    }
                                }
                            }
                        });
                    } else if (data === 'failed') {
                        $.confirm({
                            title: 'Error!',
                            content: 'An error occurred. Please try again.',
                            type: 'red',
                            typeAnimated: true,
                            buttons: {
                                ok: {
                                    text: 'OK',
                                    btnClass: 'btn-danger',
                                    action: function () {
                                        location.reload();
                                    }
                                }
                            }
                        });
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $.confirm({
                        title: 'Error!',
                        content: 'Error updating appellate: ' + textStatus + ' - Exception: ' + errorThrown,
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            ok: {
                                text: 'OK',
                                btnClass: 'btn-danger'
                            }
                        }
                    });
                }
            });
        });



        $(document).on('click', '.addAppellate', function (e) {
            e.preventDefault(); // Prevent default form submission
            var addAppellateOffcanvas = new bootstrap.Offcanvas(document.getElementById('addNewAppelateOffcanvas'));
            addAppellateOffcanvas.show();
            // Collect data from the form
            var appelateName = $('#officename').val(); // Appellate Name
            var officeLevel = $('input[name=officelevel]:checked').val(); // Office Level
            var departmentCode = $('#departmentcode').val(); // Department Code

            // Validate form inputs
            if (!appelateName || !officeLevel || !departmentCode) {
                $.alert({
                    title: 'Error!',
                    content: 'All fields are required!',
                    type: 'red'
                });
                return;
            }

            // Prepare data object for the AJAX request
            var appellateData = {
                appelatename: appelateName,
                officelevel: officeLevel,
                departmentcode: departmentCode
            };

            console.log(appellateData);
//            alert(JSON.stringify(appellateData));
            // Perform the AJAX request to add new appellate
            $.ajax({
                type: "POST",
                url: "/secure/addappelate", // Update the URL for adding a new appellate
                contentType: 'application/json', // Specify that the data is JSON
                data: JSON.stringify(appellateData), // Convert data to JSON
                success: function (data) {
                    if (data === 'added') {
                        $.confirm({
                            title: 'Success!',
                            content: 'Appellate Authority Added Successfully!',
                            type: 'green',
                            typeAnimated: true,
                            buttons: {
                                ok: {
                                    text: 'OK',
                                    btnClass: 'btn-success',
                                    action: function () {
                                        $('#addNewAppelateModal').modal('hide'); // Hide the modal
                                        location.reload(); // Reload the page to reflect the new entry
                                    }
                                }
                            }
                        });
                    } else if (data === 'failed') {
                        $.confirm({
                            title: 'Error!',
                            content: 'An error occurred. Please try again.',
                            type: 'red',
                            typeAnimated: true,
                            buttons: {
                                ok: {
                                    text: 'OK',
                                    btnClass: 'btn-danger',
                                    action: function () {
                                        location.reload(); // Reload the page on error
                                    }
                                }
                            }
                        });
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $.confirm({
                        title: 'Error!',
                        content: 'Error adding appellate: ' + textStatus + ' - Exception: ' + errorThrown,
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            ok: {
                                text: 'OK',
                                btnClass: 'btn-danger'
                            }
                        }
                    });
                }
            });
        });







    // Handle click event for deleting an appellate
    $('.delete').click(function () {
        var appelateId = $(this).attr('id');
        alert(appelateId);

        $.confirm({
            title: 'Confirm Deletion',
            content: 'Are you sure you want to delete this appellate?',
            type: 'orange',
            buttons: {
                confirm: {
                    text: 'Yes',
                    btnClass: 'btn-warning',
                    action: function () {
                        // Perform the deletion
                        $.ajax({
                            type: "DELETE",
                            url: "/secure/deleteappelate/" + appelateId,
                            success: function (data) {
                                if (data === 'deleted') {
                                    $.confirm({
                                        title: 'Success!',
                                        content: 'Deleted Successfully',
                                        type: 'green',
                                        typeAnimated: true,
                                        buttons: {
                                            ok: {
                                                text: 'OK',
                                                btnClass: 'btn-success',
                                                action: function () {
                                                    location.reload();
                                                }
                                            }
                                        }
                                    });
                                } else if (data === 'failed') {
                                    $.confirm({
                                        title: 'Error!',
                                        content: 'An error occurred. Please try again.',
                                        type: 'red',
                                        typeAnimated: true,
                                        buttons: {
                                            ok: {
                                                text: 'OK',
                                                btnClass: 'btn-danger',
                                                action: function () {
                                                    location.reload();
                                                }
                                            }
                                        }
                                    });
                                }
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                $.confirm({
                                    title: 'Error!',
                                    content: 'Error deleting appellate: ' + textStatus + ' - Exception: ' + errorThrown,
                                    type: 'red',
                                    typeAnimated: true,
                                    buttons: {
                                        ok: {
                                            text: 'OK',
                                            btnClass: 'btn-danger'
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                cancel: {
                    text: 'No',
                    btnClass: 'btn-secondary',
                    action: function () {
                        // Do nothing on cancel
                    }
                }
            }
        });
    });
});
