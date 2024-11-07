//$(document).ready(function () {
//
//        $(document).on('click', '.doedit', function (e) {
//                e.preventDefault();
//                var officeId = $(this).attr('id');
////                    alert(officeId);
//
//                // Fetch the appellate details
//                $.ajax({
//                    type: "GET",
//                    url: "/secure/getdos/" + officeId,
//                    success: function (officeData) {
//                        console.log(officeData);
//
//                        // Populate modal fields with appellate data
//                        $('#officeid').val(officeData.officeid);
//                        $('#officename').val(officeData.officename);
//                        $('#officername').val(officeData.officername);
//                        $('#email').val(officeData.email);
//                        $('#mobile').val(officeData.mobile);
//
//                    },
//                    error: function (jqXHR, textStatus, errorThrown) {
//                        alert("Error loading appellate: " + textStatus + " - Exception: " + errorThrown);
//                    }
//                });
//
//                // Show the edit appellate modal
//                $('#editAppelateModal').modal('show');
//            });
//
//
//          // Update the DO's Details
//          $(document).on('click', '#updateDO', function (e) {
//                alert('Update DO')
//                e.preventDefault();
//
//                var officeid = $('#officeid').val();
//                var officename = $('#officename').val();
//                var officername = $('#officername').val();
//                var email = $('#email').val();
//                var mobile = $('#mobile').val();
//
//                var doDatas = {
//                    officeid: officeid,
//                    officename: officename,
//                    officername: officername,
//                    email: email,
//                    mobile: mobile,
//                };
//
//
//
//                alert(JSON.stringify(doDatas));
//
////                $.ajax({
////                    type: "POST",
////                    url: "/secure/updatedos",
////                    data: JSON.stringify(doData),
////                    success: function (data) {
////                        console.log(data);
////                        location.reload();
////                    },
////                    error: function (jqXHR, textStatus, errorThrown) {
////                        alert("Error updating DO: " + textStatus + " - Exception: " + errorThrown);
////                    }
////                });
//          });
//
//
//
//
//
//
//
////    $('.doedit').click(function () {
////        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
////        var csrfToken = $("meta[name='_csrf']").attr("content");
////        var officeid = this.id;
//////         alert(whatsnewid)
////        $.ajax({
////            type: "GET",
////            url: "./getdo.htm",
////            data: "officeid=" + officeid,
////            beforeSend: function (xhr)
////            {
////                xhr.setRequestHeader(csrfHeader, csrfToken);
////            },
////            success: function (data) {
////                $("#officeid").val(data.officeid);
////                $("#officename").val(data.officename);
////                $("#officername").val(data.officername);
////                $("#email").val(data.email);
////                $("#mobile").val(data.mobile);
////
////
////            },
////            error: function (jqXHR, textStatus, errorThrown) {
////
////                alert("error:" + textStatus + " - exception:" + errorThrown);
////            }
////        });
////    })
//
//    $('.delete').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
//        var officeid = this.id;
////         alert(whatsnewid)
//        $.ajax({
//            type: "GET",
//            url: "./deletedo.htm",
//            data: "officeid=" + officeid,
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
//            success: function (data) {
//                if(data=='1'){
//                    alert("Deleted Successfully")
//                }else{
//                    alert("Error")
//                }
//                location.reload()
//
//
//            },
//            error: function (jqXHR, textStatus, errorThrown) {
//
//                alert("error:" + textStatus + " - exception:" + errorThrown);
//            }
//        });
//    })
//
//
//});


$(document).ready(function () {

    // Handle the Edit button click
    $(document).on('click', '.doedit', function (e) {
        e.preventDefault();
        var officeId = $(this).attr('id');

        // Fetch the DO details using AJAX
        $.ajax({
            type: "GET",
            url: "/secure/getdos/" + officeId,
            success: function (officeData) {
                console.log(officeData);

                // Populate modal fields with DO data
                $('#officeid').val(officeData.officeid);
                $('#officename').val(officeData.officename);
                $('#officername').val(officeData.officername);
                $('#email').val(officeData.email);
                $('#mobile').val(officeData.mobile);

                // Show the edit modal
                $('#editAppelateModal').modal('show');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error loading DO: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });

    // Handle the Update DO button click
    $(document).on('click', '#updateAppellateAuthority', function (e) {
        e.preventDefault();

        // Collect form data
        var officeid = $('#officeid').val();
        var officename = $('#officename').val();
        var officername = $('#officername').val();
        var email = $('#email').val();
        var mobile = $('#mobile').val();

        // Data object to send
        var doData = {
            officeid: officeid,
            officename: officename,
            officername: officername,
            email: email,
            mobile: mobile
        };

        alert(JSON.stringify(doData));
        console.log(doData);

        // Send the update request via AJAX
        $.ajax({
            type: "POST",
            url: "/secure/updatedos",
            contentType: "application/json",
            data: JSON.stringify(doData),
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
                alert("Error adding DO: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });


    $(document).ready(function () {

        // Handle the Add New DO form submission
        $(document).on('click', '#addNewDO', function (e) {
            e.preventDefault();

            // Collect form data
            var officename = $('#new_officename').val();
            var officername = $('#new_officername').val();
            var email = $('#new_email').val();
            var mobile = $('#new_mobile').val();

            // Create a data object for sending
            var newDOData = {
                officename: officename,
                officername: officername,
                email: email,
                mobile: mobile
            };

            alert(JSON.stringify(newDOData))

            // Send the form data via AJAX POST
            $.ajax({
                type: "POST",
                url: "/secure/adddos", // Adjust this URL to match your backend endpoint
                contentType: "application/json",
                data: JSON.stringify(newDOData),
                success: function (data) {
                    if (data === 'added') {
                        $.confirm({
                            title: 'Added!',
                            content: 'Added Successfully',
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
                    alert("Error adding DO: " + textStatus + " - Exception: " + errorThrown);
                }
            });
        });

    });


    // Handle the Delete button click
    $('.delete').click(function () {
        var officeid = this.id;


        // Confirm deletion
        if (confirm("Are you sure you want to delete this DO?")) {
            $.ajax({
                type: "DELETE",
                url: "/secure/deletedos/" + officeid,
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(csrfHeader, csrfToken); // Set CSRF token
                },
                success: function (data) {
                    if (data == '1') {
                        alert("Deleted Successfully");
                    } else {
                        alert("Error deleting DO");
                    }
                    location.reload(); // Reload the page to reflect changes
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error deleting DO: " + textStatus + " - Exception: " + errorThrown);
                }
            });
        }
    });

});
