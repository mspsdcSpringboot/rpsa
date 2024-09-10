$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="uploadnotification.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="uploadnotification.htm"
    }
 $('#notificationslisttable').DataTable({
        "bJQueryUI": true,
        order: [[1, 'desc']],
        paging: false
//                    "sPaginationType": "full_numbers",
        



    });
    $("#notificationdoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#notificationdoc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });

    $('.notification').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationid = this.id;
//         alert(notificationid)
        $.ajax({
            type: "GET",
            url: "./getnotification.htm",
            data: "notificationid=" + notificationid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#notificationid").val(data.notificationid)
                $("#notificationname").val(data.notificationname)
                $("#notificationdate").val(data.notificationdate)
                $("#notificationtitle").val(data.title)
                $("#viewdoc").html('<a href="viewnotification.htm?notificationid='+data.notificationid+'" target="_blank">View Notification</a>')
                
                $("#notificationname").focus()
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationid = this.id;
//         alert(notificationid)
        $.ajax({
            type: "GET",
            url: "./deletenotification.htm",
            data: "notificationid=" + notificationid,
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
