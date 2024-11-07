$(document).ready(function () {
   


    $("#photodoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#photodoc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });


    document.getElementById("photodoc").addEventListener("change", function (event) {
        var file = event.target.files[0];
        var imagePreviewContainer = document.getElementById("imagePreviewContainer");

        if (file) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var previewImg = document.getElementById("previewImg");
                previewImg.src = e.target.result;
                imagePreviewContainer.style.display = "block"; // Show preview div
            };
            reader.readAsDataURL(file);
        } else {
            imagePreviewContainer.style.display = "none"; // Hide preview if no file selected
        }
    });


    $('.submitHomePhoto').click(function (e) {
        e.preventDefault();
        const form = document.getElementById('homepagephotos');

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
            url: "/secure/addhomepagephoto",
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
