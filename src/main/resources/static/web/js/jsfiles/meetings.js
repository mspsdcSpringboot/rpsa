$(document).ready(function () {
//    if($("#res").val()=="1"){
//        alert("Successful")
//        location.href="addmeeting.htm"
//    }else if($("#res").val()=="2"){
//        alert("Error")
//        location.href="addmeeting.htm"
//    }
// $('#meetinglisttable').DataTable({
//        "bJQueryUI": true,
//        order: [[1, 'desc']]
////                    "sPaginationType": "full_numbers",
//
//
//
//
//    });


    $('.submitMeeting').click(function (e) {
            e.preventDefault();
            const form = document.getElementById('meeting');

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
                url: "/secure/addmeeting",
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
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var meetingid = this.id;
        alert(meetingid);
        $.ajax({
            type: "GET",
            url: "/secure/getmeeting",
            data: {"meetingId": meetingid},
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                $("#meetingid").val(data.meetingid)
                $("#details").val(data.details)
                $("#meetingdate").val(data.meetingdate)
                $("#viewdoc").html('<a class="btn btn-primary mt-3" href="/public/viewmeetingdoc/' + data.meetingid + '" target="_blank">View Meeting</a>');



            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var meetingid = this.id;
         alert(meetingid);
        alert("Are Sure that you want to delete the meeting review ?");
        $.ajax({
            type: "DELETE",
            url: "/secure/deletemeeeting",
            data: { "meetingId": meetingid },
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                alert(data)
                location.reload()


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })


});
