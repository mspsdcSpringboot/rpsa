$(document).ready(function () {
//   alert('albums')
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="managephotos.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="managephotos.htm"
    }

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
        var photoid =this.id;
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        
        $.ajax({
            type: "GET",
            url: "./deletephoto.htm",
            data: "photoid=" + photoid,
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
