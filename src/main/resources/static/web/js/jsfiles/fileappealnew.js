$(document).ready(function () {
    
  new SlimSelect({
     select: "#services"
  });
    
    
    
    
    $('.autoresizing').on('input', function () {
        this.style.height = 'auto';

        this.style.height =
                (this.scrollHeight) + 'px';
    });



    $('#supportdoc2').hide();
    $('#supportdoc3').hide();
    $('#supportdoc4').hide();
    $('#supportdoc5').hide();

    $("#applicationdate").change(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");


        $.ajax({
            type: "GET",
            url: "./checkapplicationdate.htm",
            data: "subservicecode=" + $('#subservices').val() + "&applicationrefno=" + $('#applicationrefno').val() + "&applicationdate=" + $(this).val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data == '1') {
                    alert("You are not Eligible to file an Appeal for this Application as it is still within SLA")
                    location.href = "userhome.htm"
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

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

    $('#services').change(function () {
        
//        alert($('#services').val())
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");


        $.ajax({
            type: "POST",
            url: "./getsubservices.htm",
            data: "servicecode=" + $('#services').val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $('#subservices').empty();
                $('#subservices').append($('<option></option>').attr('value', "").text("Select"));
                var sla;
                data.forEach(function (item) {
                    $('#subservices').append($('<option></option>').attr('value', item.subservicecode).text(item.subservicename));
                    sla = item.stipulatedtime;
                });
                $("#sla").text("SLA: " + sla)
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });
    $('#subservices').change(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");


        $.ajax({
            type: "POST",
            url: "./getdesignatedoffices.htm",
            data: "subservicecode=" + $('#subservices').val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $('#designatedofficer').empty();
                $('#designatedofficer').append($('<option></option>').attr('value', "").text("Select"));
                data.forEach(function (item) {
                    $('#designatedofficer').append($('<option></option>').attr('value', item[0]).text(item[1]));
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
//        $.ajax({
//            type: "POST",
//            url: "./getsla.htm",
//            data: "subservicecode=" + $('#subservices').val(),
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
//            success: function (data) {
//               $('#sla').val(data);
//            },
//            error: function (jqXHR, textStatus, errorThrown) {
//
//                alert("error:" + textStatus + " - exception:" + errorThrown);
//            }
//        });

    });

    $('#designatedofficer').change(function () {

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");


        $.ajax({
            type: "GET",
            url: "./getappelateid.htm",
            data: "subservicecode=" + $('#subservices').val() + "&officeid=" + $('#designatedofficer').val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
//                            alert(data)
                $('#appelateid').val(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    });







    var num = 2;
    $('#addrow').click(function () {

        $('#supportdoc' + num).show();
        if (num < 6) {

            num++;
        } else {
            alert("Only 5 Support Documents can be uploaded")
            num = 5;

        }

    })

    $('#delrow').click(function () {


        $('#supportdoc' + num).hide();
        if (num > 1) {

            num--;
        } else {
            alert("Atleast 1 Support Document must be uploaded")
            num = 2;
        }


    })


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



    $('#notificationslisttable').DataTable();

});







