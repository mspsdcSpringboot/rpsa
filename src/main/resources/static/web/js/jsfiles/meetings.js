$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="addmeeting.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="addmeeting.htm"
    }
 $('#meetinglisttable').DataTable({
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

    $('.meeting').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var meetingid = this.id;
        
        $.ajax({
            type: "GET",
            url: "./getmeeting.htm",
            data: "meetingid=" + meetingid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#meetingid").val(data.meetingid)
                $("#details").val(data.details)
                $("#meetingdate").val(data.meetingdate)
                $("#viewdoc").html('<a href="viewmeeting.htm?meetingid='+data.meetingid+'" target="_blank">View meeting</a>')
                
                
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var meetingid = this.id;
//         alert(notificationid)
        $.ajax({
            type: "GET",
            url: "./deletemeeting.htm",
            data: "meetingid=" + meetingid,
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
