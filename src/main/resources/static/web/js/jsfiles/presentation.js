$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="addpresentation.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="addpresentation.htm"
    }
 $('#presentationlisttable').DataTable({
        "bJQueryUI": true,
        order: [[1, 'desc']]
//                    "sPaginationType": "full_numbers",
        



    });
    $("#doc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#doc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });

    $('.presentation').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var presentationid = this.id;
        
        $.ajax({
            type: "GET",
            url: "./getpresentation.htm",
            data: "presentationid=" + presentationid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#presentationid").val(data.presentationid)
                $("#details").val(data.details)
                $("#presentationdate").val(data.presentationdate)
                $("#viewdoc").html('<a href="viewpresentation.htm?presentationid='+data.presentationid+'" target="_blank">View Presentation</a>')
                
                
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var presentationid = this.id;
//         alert(notificationid)
        $.ajax({
            type: "GET",
            url: "./deletepresentation.htm",
            data: "presentationid=" + presentationid,
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
