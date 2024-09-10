$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="adddos.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="adddos.htm"
    }
 $('#dotable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],
         paging: false
//                    "sPaginationType": "full_numbers",
        



    });
    

    $('.doedit').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var officeid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./getdo.htm",
            data: "officeid=" + officeid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#officeid").val(data.officeid);
                $("#officename").val(data.officename);
                $("#officername").val(data.officername);
                $("#email").val(data.email);
                $("#mobile").val(data.mobile);
              
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var officeid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./deletedo.htm",
            data: "officeid=" + officeid,
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
