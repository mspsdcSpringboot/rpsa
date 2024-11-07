$(document).ready(function () {
//    if($("#res").val()=="1"){
//        alert("Successful")
//        location.href="uploadnotification.htm"
//    }else if($("#res").val()=="2"){
//        alert("Error")
//        location.href="uploadnotification.htm"
//    }
// $('#notificationslisttable').DataTable({
//        "bJQueryUI": true,
//        order: [[1, 'desc']],
//        paging: false
////                    "sPaginationType": "full_numbers",
//
//
//
//
//    });

    $('.submitNotifications').click(function (e) {
        e.preventDefault();
        const form = document.getElementById('notificationform');

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


        $.ajax({
            type: "POST",
            url: "/secure/addnotification",
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
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationid = this.id;
         alert(notificationid)
        $.ajax({
            type: "GET",
            url: "/secure/getnotification",
            data: { "notificationId": notificationid },
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                $("#notificationid").val(data.notificationid)
                $("#notificationname").val(data.notificationname)
                $("#notificationdate").val(data.notificationdate)
                $("#notificationtitle").val(data.title)
                var viewDocUrl = '/public/viewnotificationdoc/' + data.notificationid;
                $("#viewdoc").html('<a class="btn btn-primary notification" href="' + viewDocUrl + '" target="_blank">View Notification</a>');


                $("#notificationname").focus()

            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })



    $('.delete').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationid = this.id;
        alert("Are Sure that you want to delete the notification ?")
//         alert(notificationid)
        $.ajax({
            type: "DELETE",
            url: "/secure/deletenotification",
            data: { "notificationId": notificationid },
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                if(data=='deleted'){
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
