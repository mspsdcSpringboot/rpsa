$(document).ready(function () {
    $('#updatedo').hide()

$('#userstable').DataTable({
       
        paging: false,
       
    });

    $('.editdo').click(function () {
        
        var officeid = this.id;
//        alert(officeid);
        $("#officeid").val(officeid);
        $("#officename").val($("#officename_"+officeid).val());
        $("#officername").val($("#officername_"+officeid).val());
        $("#mobile").val($("#mobile_"+officeid).val());
        $("#email").val($("#email_"+officeid).val());
        $("#username").val($("#username_"+officeid).val());
//        $("#officeid").val($("#officeid_"+officeid).val());
        $("#officename").attr("readonly",true)
        $("#officername").focus()
$('#updatedo').show()

    })
    $('.createdo').click(function () {
        
        var officeid = this.id;
//        alert(officeid);
        $("#officeid").val(officeid);
        $("#officename").val($("#officename_"+officeid).val());
        $("#officername").val($("#officername_"+officeid).val());
        $("#mobile").val($("#mobile_"+officeid).val());
        $("#email").val($("#email_"+officeid).val());
//        $("#officeid").val($("#officeid_"+officeid).val());
        $("#officename").attr("readonly",true)
        $("#officername").focus()


    })
    $('#resetbtn').click(function () {
        
       location.reload()

    })
    
    
    
    $('#updatedo').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
//        alert("asergv")


        var dodetails;

        dodetails = {
            officeid: $('#officeid').val(),
            officername: $('#officername').val(),
            officename: $('#officename').val(),
            email: $('#email').val(),
            username: $('#username').val(),
            mobile: $('#mobile').val()

        };

        var userdetailsjson = JSON.stringify(dodetails);
//                    alert(userdetailsjson);
        $.ajax({
            type: "POST",
            url: "./updatedo.htm",
            data: "appjson=" + userdetailsjson,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Data has been saved successfully");
                    location.reload()
                } else {
                    alert("Unsuccessfull");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
});
