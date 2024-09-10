$(document).ready(function () {
    if($("#res").val()=="1"){
        alert("Successful")
        location.href="addwhatsnew.htm"
    }else if($("#res").val()=="2"){
        alert("Error")
        location.href="addwhatsnew.htm"
    }
 $('#whatsnewlisttable').DataTable({
        "bJQueryUI": true,
        order: [[1, 'desc']]
//                    "sPaginationType": "full_numbers",
        



    });
    

    $('.whatsnewedit').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var whatsnewid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./getwhatsnew.htm",
            data: "whatsnewid=" + whatsnewid,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $("#whatsnewid").val(data.whatsnewid);
                $("#heading").val(data.heading);
                $("#newbody").val(data.newbody);
                alert(data.whatsnewdate)
                var d=new Date(data.whatsnewdate);
                
                $("#whatsnewdate").val(d);
//                var $datepicker=$("#whatsnewdate");
//                $datepicker.datepicker();
//                $datepicker.datepicker('setDate',data.whatsnewdate);
               
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('.delete').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var whatsnewid = this.id;
//         alert(whatsnewid)
        $.ajax({
            type: "GET",
            url: "./deletewhatsnew.htm",
            data: "whatsnewid=" + whatsnewid,
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
