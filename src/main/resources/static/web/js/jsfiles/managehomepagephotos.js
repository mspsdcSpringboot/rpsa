$(document).ready(function () {
   
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="managehomepagephotos.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="managehomepagephotos.htm"
    }

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

   

});
