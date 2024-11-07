$(document).ready(function () {
//   alert('albums')
//    if($("#res").val()=="1"){
//        alert("Successful")
//        location.href="managephotos.htm"
//    }else if($("#res").val()=="2"){
//        alert("Error")
//        location.href="managephotos.htm"
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
    
   $(".deletephoto").click(function () {
       var photoid = $(this).attr("id");  // Fetch the photoid from the button ID

       // Ask for confirmation before deleting the photo
       if (confirm("Are you sure you want to delete this photo?")) {

           // Fetch CSRF token and header from meta tags
//           var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//           var csrfToken = $("meta[name='_csrf']").attr("content");

           // Send AJAX DELETE request
           $.ajax({
               type: "DELETE",
               url: "/secure/deletealbumphoto/" + photoid,
//               beforeSend: function (xhr) {
//                   // Set CSRF token in the request header
//                   xhr.setRequestHeader(csrfHeader, csrfToken);
//               },
               success: function (data) {
                   alert(data);
                   location.reload();  // Reload the page to reflect changes
               },
               error: function (jqXHR, textStatus, errorThrown) {
                   alert("Error: " + textStatus + " - Exception: " + errorThrown);
               }
           });
       }
   });



    $('.uploadAlbumPhoto').click(function (e) {
            e.preventDefault();
            const form = document.getElementById('photos');

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
                url: "/secure/addalbumphoto",
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
