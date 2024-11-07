$(document).ready(function () {

//    if ($("#res").val() == "1") {
//        alert("Successful")
//        location.href = "manageonlineservices.htm"
//    } else if ($("#res").val() == "2") {
//        alert("Error")
//        location.href = "manageonlineservices.htm"
//    }
    $("#linksform").hide();
    $("#links").hide();
    $(".uploadform").hide();
//
//    $("#departmentcode").change(function () {
//       $("#department").val($("#departmentcode option:selected").text());
//    })


    $("#formdoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#formdoc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });
//    $('#servicestable').DataTable({
//        "bJQueryUI": true,
//        order: [[0, 'asc']],
//
//        paging: false
//
//    });

    $("#online").change(function () {
        $("#linksform").show();
        if ($("#online").val() == '0') {
            $("#linkslabel").text("Upload Form:")
            $(".uploadform").show();
            $("#links").hide();
        } else if ($("#online").val() == '1' || $("#online").val() == '2') {
            $("#linkslabel").text("Enter URL:")
            $(".uploadform").hide();
            $("#links").show();
        } else if ($("#online").val() == '-1') {
            $("#linkslabel").text("Enter Message:")
            $(".uploadform").hide();
            $("#links").show();
        }
    })
    
    
    $('.edit').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var slno = this.id;
//        alert(slno)
        $.ajax({
            type: "GET",
            url: "/secure/getonlineservice",
            data: {"Id" : slno},
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
//                alert(JSON.stringify(data));
               
                $("#slno").val(data.slno);
                $("#servicename").val(data.servicename);
                $("#sla").val(data.sla);
                $("#dos").val(data.dos);
                $("#aas").val(data.aas);
                $("#online").val(data.online);
                $("#online").trigger('change');
                $("#links").val(data.links);
                $("#department").val(data.department);

                $("#enclosures").val(data.enclosures);
                $("#departmentcode").val(data.departmentcode.departmentcode);
               
               $('html, body').animate({
                   scrollTop: $("#onlineservices").offset().top  // Change "#appelate" to your form ID
               }, 50);
                
                
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })


    $('.delete').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var slno = this.id;
        alert(slno)
        $.ajax({
            type: "DELETE",
            url: "/secure/deleteonlineservice/" + slno,
//            data: "slno=" + slno,
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                alert(data)
//                if(data=='1'){
//                    alert("Deleted Successfully")
//                }else{
//                    alert("Error")
//                }
                location.reload()
                
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

    $('.submitOnlineServices').click(function (e) {
            e.preventDefault();
            const form = document.getElementById('onlineservices');

            // Create a FormData object from the form
            const formData = new FormData(form);

            // Collect the data from FormData into a plain object (optional)
            const dataObject = {};
            formData.forEach((value, key) => {
                dataObject[key] = value;
            });

            console.log("Appeal Form Data Object - ", form);
            console.log("Appeal Form - ", dataObject);
            alert(JSON.stringify(dataObject));
            console.log(dataObject);


            $.ajax({
                type: "POST",
                url: "/secure/addonlineservices",
                data: formData,
                processData: false, // Important for FormData
                contentType: false, // Important for FormData
                success: function (data) {
                    alert(data);
                    location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert("error:" + textStatus + " - exception:" + errorThrown);
                }
            });
        })



});
