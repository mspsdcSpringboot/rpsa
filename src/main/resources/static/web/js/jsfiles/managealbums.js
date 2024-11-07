$(document).ready(function () {
//   alert('albums')
//    if($("#res").val()=="1"){
//        alert("Successful")
//        location.href="managealbums.htm"
//    }else if($("#res").val()=="2"){
//        alert("Error")
//        location.href="managealbums.htm"
//    }

    $("#photodoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#photodoc").val("");
        }
        if (this.files[0].size > 10000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
        
    });
    
    $(".editalbum").click(function () {
        var id = this.id; // Get the album ID
//        alert(id);

        $.ajax({
            type: "GET",
            url: "/secure/getalbum/" + id,
            success: function (data) {
                // Set form values using the returned data
                $("#albumid").val(data.albumid);
                $("#albumname").val(data.albumname);
                $("#albumdate").val(data.albumdate);

                // Check if the thumbnail data exists and is not null
                if (data.thumbnail) {
                    // Set the image source by converting the thumbnail (Base64) into an image
                    $("#imgsrc").attr('src', 'data:image/png;base64,' + data.thumbnail);
                    // Show the preview container
                    $("#previewContainer").show();
                } else {
                    // If no image exists, hide the preview
                    $("#imgsrc").attr('src', '');
                    $("#previewContainer").hide();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error: " + textStatus + " - exception: " + errorThrown);
            }
        });
    });

    $(".managephotos").click(function () {
            var id = this.id; // Get the album ID
    //        alert(id);

            $.ajax({
                type: "GET",
                url: "/secure/managealbumphotos/" + id,
                success: function (data) {
                    location.href="/secure/managealbumphotos/" + id
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("error: " + textStatus + " - exception: " + errorThrown);
                }
            });
        });


    $(".deletealbum").click(function () {
        var albumid =this.id;
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        
        $.ajax({
            type: "GET",
            url: "./deletealbum.htm",
            data: "albumid=" + albumid,
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


    $('.submitAlbum').click(function (e) {
            e.preventDefault();
            const form = document.getElementById('albums');

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
                url: "/secure/addalbum",
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

        $("#photodoc").change(function (event) {
                var input = event.target;

                if (input.files && input.files[0]) {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        // Set the preview image with the uploaded file's Base64 data
                        $("#imgsrc").attr('src', e.target.result);
                        $("#previewContainer").show();
                    };
                    reader.readAsDataURL(input.files[0]);
                }
            });

   

});
