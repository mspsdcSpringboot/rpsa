$(document).ready(function() {

    $('#services').select2({
            allowClear: true
        });


//    alert('Appeal')

    $('#supportdoc2').hide();
    $('#supportdoc3').hide();
    $('#supportdoc4').hide();
    $('#supportdoc5').hide();


    $("#file9").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#file9").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#file3").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#file3").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#file1").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#file1").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#supportdoc").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#supportdoc").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#supportdoc2").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#supportdoc2").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#supportdoc3").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#supportdoc3").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#supportdoc4").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#supportdoc4").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });
        $("#supportdoc5").change(function () {
            var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
            if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
                alert("Only formats are allowed : " + fileExtension.join(', '));
                $("#supportdoc5").val("");
            }
            if (this.files[0].size > 2000000) {
                alert("Please upload file less than 2MB. Thanks!!");
                $(this).val('');
            }
        });

        $('#submitbtn').attr('disabled', true);
        $('#ordercopy').hide();
        $('#declaration').click(function () {
            if ($('#declaration').is(':checked')) {
                $('#submitbtn').attr('disabled', false);
            } else {
                $('#submitbtn').attr('disabled', true);
            }
        });

        $('#groundcode').change(function () {
            if ($('#groundcode').val() == '2') {
                $('#ordercopy').show();
    //                        $("#file3").attr("required",true)
            } else {
                $('#ordercopy').hide();
    //                        $("#file3").attr("required",false)
            }
        })

        var num = 2;
            $('#addrow').click(function () {

                $('#supportdoc' + num).show();
                if (num < 6) {

                    num++;
                } else {
                    $.confirm({
                            title: 'Upload Limit Reached',
                            content: 'Only 5 Support Documents can be uploaded',
                            type: 'red',
                            buttons: {
                                ok: {
                                    text: "OK",
                                    btnClass: 'btn-red',
                                    action: function(){
                                    }
                                }
                            }
                        });
                    num = 5;

                }

            })

            $('#delrow').click(function () {


                $('#supportdoc' + num).hide();
                if (num > 1) {

                    num--;
                } else {
                    $.confirm({
                            title: 'Upload Required',
                            content: 'Atleast 1 Support Document must be uploaded',
                            type: 'red',
                            buttons: {
                                ok: {
                                    text: "OK",
                                    btnClass: 'btn-red',
                                    action: function(){
                                    }
                                }
                            }
                        });
                    num = 2;
                }


            })



    $('#services').change(function() {
        var serviceCode = $(this).val();
        if(serviceCode) {
            $.ajax({
                url: '/secure/getSubServices/' + serviceCode,
                type: "GET",
                success: function(data) {
                    console.log("Data for sub service array", data)
                    $('#subservices').empty();
                    $('#subservices').append('<option value="">Select</option>');


                    const subservicesMap = new Map();
                    data.map(item => subservicesMap.set(item.subservicecode, { subservicename: item.subservicename, stipulatedtime: item.stipulatedtime }));

                    // Populate the dropdown
                    subservicesMap.forEach((value, key) => {
                        $('#subservices').append(`<option value="${key}">${value.subservicename}</option>`);
                    });

                    // Handle change event to display stipulated time
                    $('#subservices').on('change', function() {
                        const selectedValue = $(this).val();
                        console.log("Selected Value:", selectedValue);

                        if (selectedValue) {
                            const subservice = subservicesMap.get(selectedValue);
                            if (subservice) {
                                $('#sla').empty();
                                $('#sla').append('SLA: ' + subservice.stipulatedtime + ' Days');
                                console.log("Subservices", subservice);

                                $.ajax({
                                    url: '/secure/getDesignatedOffices/' + selectedValue,
                                    type: "GET",
                                    success: function(data) {
                                        console.log(data)
                                        $('#designatedofficer').empty();
                                        $('#designatedofficer').append('<option value="">Select</option>');
                                        data.forEach(function(office) {
                                            $('#designatedofficer').append(`<option value="${office.officeid}">${office.officename}</option>`);
                                        });

                                    },
                                    error: function(error) {
                                        console.log('Error:', error);
                                    }
                                });

                            } else {
                                console.error("Subservice not found for selected value:", selectedValue);
                                $('#sla').text('SLA: Not available');
                            }
                        } else {
                            $('#sla').text('');
                        }
                    });
                    $('#designatedofficer').change(function () {
                        const doSelectedValue = $(this).val();
                        const subServiceValue = $('#subservices').val();

                        console.log("Selected value:", subServiceValue, doSelectedValue);
                            $.ajax({
                                type: "GET",
                                url: "/secure/getAppellateId",
                                data: {
                                      doSelectedValue: doSelectedValue,
                                      subServiceValue: subServiceValue
                                  },
                                success: function (data) {
                                    $('#appelateid').val(data);
                                },
                                error: function (jqXHR, textStatus, errorThrown) {

                                    alert("error:" + textStatus + " - exception:" + errorThrown);
                                }
                            });
                        });
                },
                error: function(error) {
                    console.log('Error:', error);
                }
            });
        } else {
            $('#subservices').empty();
            $('#subservices').append('<option value="">Select</option>');
        }
    });

    $('#submitbtn').click(function () {
        if ($('#groundcode').val() == '2' && $("#file3").val() == "") {

            alert("Please Upload Copy of the Order from the Designated Office")
            $("#file3").focus()
            return false;


        } else if ($("#file1").val() == "") {

            alert("Please Upload Identification Proof (Epic Card/Aadhar Card/PAN Card/Driving License)")
            $("#file1").focus()
            return false;


        } else if ($("#supportdoc").val() == "") {

            alert("Please Upload Atleast One Supporting document to avail the service")
            $("#supportdoc").focus()
            return false;


        } else if ($("#file9").val() == "") {

            alert("Please Upload Form I")
            $("#file9").focus()
            return false;

        }
    })

//    document.getElementById('appealform').addEventListener('submit', async function(event) {
//        event.preventDefault();
//
//        let data;
//
//        data = {
//            subservicecode: $('#subservices').val(),
//            officeid: $('#designatedofficer').val(),
//            appelateid: $('#appelateid').val(),
//            applicationrefno: $('#applicationrefno').val(),
//            applicationdate: $('#applicationdate').val(),
//            groundcode1: $('#groundcode').val(),
//            relief1: $('#relief').val(),
//            otherinfo1: $('#otherinfo1').val(),
//            hearingtype: $('#hearingtype').val(),
//            ordercopy: $('#ordercopy').val(),
//            idproof: $('#idproof').val(),
//            supportdoc: $('#supportdoc').val(),
//            supportdoc2: $('#supportdoc2').val(),
//            supportdoc3: $('#supportdoc3').val(),
//            supportdoc4: $('#supportdoc4').val(),
//            supportdoc5: $('#supportdoc5').val(),
//            firstorderdoc: $('#firstorderdoc').val()
//        };
//
//
//        // Convert files to base64
//
//
//        // Wait for all file conversions to complete
//        await Promise.all(promises);
//
//        console.log(data);
////        console.log(JSON.stringify(data));
//
//        var appealDataJson = JSON.stringify(data)
//        console.log(appealDataJson)
//
//
//
//        // Send data using AJAX
//        $.ajax({
//            url: '/secure/submitAppeal',
//            type: 'POST',
//            contentType: 'application/json',
//            data: appealDataJson,
//            success: function(response) {
//                console.log("data  " + typeof(data))
//                console.log("data  " + data)
//                console.log("Success  " + response)
//                // Handle success (e.g., show a success message)
//            },
//            error: function(error) {
//                console.error('Error:', error);
//                // Handle error (e.g., show an error message)
//                alert('There was an error submitting your appeal: ' + error.responseText);
//            }
//        });
//    });

    function fileToBase64(file) {
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result);
            reader.onerror = error => reject(error);
        });
    }


    document.getElementById('submitbtn').addEventListener('click', function(event) {
        // Prevent the default form submission
        event.preventDefault();

        // Get the form element
        const form = document.getElementById('appealform');

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
        $.ajax({
            type: 'POST',
            url: '/secure/appealSubmission',
            data: formData,
            processData: false, // Important for FormData
            contentType: false, // Important for FormData
            success: function(data) {
                console.log('Success:', data);
                alert(data);
                $('#appealform')[0].reset();
//                window.location.href = '/secure/initiatepayment';

                var pathVar = data;
                $.ajax({
                    type: 'GET',
                    url: '/secure/initiatepayment/' + pathVar,
                    success: function(response) {

                        window.location.href = '/secure/initiatepayment/' + pathVar;
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.error('Second request error:', textStatus, errorThrown);
                        alert('There was a problem initiating the payment.');
                    }
                });
                // Optionally reset the form or redirect
                $('#appealform')[0].reset();

            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
                alert('There was a problem submitting your appeal.');
            }
        });
    });





});
