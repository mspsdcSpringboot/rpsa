$(document).ready(function () {

    if ($("#res").val() == "1") {
        alert("Successful")
        location.href = "manageonlineservices.htm"
    } else if ($("#res").val() == "2") {
        alert("Error")
        location.href = "manageonlineservices.htm"
    }
    $("#linksform").hide();
    $("#links").hide();
    $(".uploadform").hide();
    
    $("#departmentcode").change(function () {
       $("#department").val($("#departmentcode option:selected").text());
    })


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
    $('#servicestable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],

        paging: false

    });

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
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var slno = this.id;
//         alert(slno)
        $.ajax({
            type: "GET",
            url: "./getonlineservice.htm",
            data: "slno=" + slno,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
               
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
               
               
                
                
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var slno = this.id;
         alert(slno)
        $.ajax({
            type: "GET",
            url: "./deleteonlineservice.htm",
            data: "slno=" + slno,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if(data=='1'){
                    alert("Deleted Successfully")
                }else{
                    alert("Error")
                }
                location.reload()
                
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })



});
