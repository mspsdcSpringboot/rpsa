$(document).ready(function () {
//   alert('albums')
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="managealbums.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="managealbums.htm"
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
    
    $(".editalbum").click(function () {
        var id =this.id;
        $("#albumid").val($("#albumid_"+id).val())
        $("#albumname").val($("#albumname_"+id).val())
        $("#albumdate").val($("#albumdate_"+id).val())
        $("#imgsrc").attr('src','data:image/png;base64,'+$("#thumbnail_"+id).val())
        
    })
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

   

});
