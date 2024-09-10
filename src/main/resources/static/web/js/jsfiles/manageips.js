$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="manageips.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="manageips.htm"
    }
 $('#iptable').DataTable({
        "bJQueryUI": true,
       
         paging: false
//                    "sPaginationType": "full_numbers",
        



    });
    

    $('.ipedit').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var ip = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./getipdetails.htm",
            data: "ip=" + ip,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#ip").val(data.ip);
                $("#ownername").val(data.ownername);
                $("input:radio[name=status][value=" + data.status + "]").prop("checked", true);
               
                
                
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var ip = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./deleteip.htm",
            data: "ip=" + ip,
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
