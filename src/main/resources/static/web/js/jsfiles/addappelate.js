$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="addappelate.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="addappelate.htm"
    }
 $('#appelatelisttable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],
         paging: false
//                    "sPaginationType": "full_numbers",
        



    });
    

    $('.appelateedit').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var appelateid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./getappelate.htm",
            data: "appelateid=" + appelateid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#departmentcode").val("");
                $("#appelateid").val(data.appelateid);
                $("#officename").val(data.officename);
                $("#departmentcode").val(data.departmentcode.departmentcode);
                $("input:radio[name=officelevel][value=" + data.officelevel + "]").prop("checked", true);
               
                
                
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var appelateid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./deleteappelate.htm",
            data: "appelateid=" + appelateid,
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
