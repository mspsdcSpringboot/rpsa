$(document).ready(function () {
  


   
    $("#notificationdoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#notificationdoc").val("");
        }
//        if (this.files[0].size > 2000000) {
//            alert("Please upload file less than 2MB. Thanks!!");
//            $(this).val('');
//        }
        
       
      

    });


    $('#upload').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var notificationid = this.id;
         alert($("#notificationdoc").val())
         
         
         var fileName = $("#notificationdoc").val().split('\\').pop(); // Extracts only the filename
         alert(fileName)
        $.ajax({
            type: "POST",
            url: "./upload.htm",
            data: "filepath=" + fileName,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if(data=='1'){
                    alert("Uploaded Successfully")
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
