$(document).ready(function () {


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
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var presentationid = this.id;
        
        $.ajax({
            type: "GET",
            url: "/secure/getpresentation",
            data: {"presentationId" : presentationid},
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
//                alert(data)
                $("#presentationid").val(data.presentationid)
                $("#details").val(data.details)
                $("#presentationdate").val(data.presentationdate)
                // Assume data.presentationid contains the new presentation ID from your Ajax response
                $("#viewdoc").html('<a class="btn btn-primary" href="/public/viewpresentationdoc/' + data.presentationid + '" target="_blank">View Presentation</a>');

                
                
                
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })


    $('.delete').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var presentationid = this.id;
//         alert(notificationid)
        alert("Are you sure, you want to delete?")
        $.ajax({
            type: "DELETE",
            url: "/secure/deletepresentation",
            data: {"presentationId" : presentationid},
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

    $('.submitPresentations').click(function (e) {
            e.preventDefault();
            const form = document.getElementById('presentation');

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
                url: "/secure/addpresentation",
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
