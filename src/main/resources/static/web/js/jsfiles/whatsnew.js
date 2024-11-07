$(document).ready(function () {

//    alert('Please wait')
//    if($("#res").val()=="1"){
//        alert("Successful")
//        location.href="addwhatsnew.htm"
//    }else if($("#res").val()=="2"){
//        alert("Error")
//        location.href="addwhatsnew.htm"
//    }
// $('#whatsnewlisttable').DataTable({
//        "bJQueryUI": true,
//        order: [[1, 'desc']]
////                    "sPaginationType": "full_numbers",
//
//
//
//
//    });
    

    $('.whatsnewedit').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var whatsnewid = this.id;

        $.ajax({
            type: "GET",
            url: "/secure/getwhatsnew",
            data: { id: whatsnewid },
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
//                alert(JSON.stringify(data));
                $("#whatsnewid").val(data.whatsnewid);
                $("#heading").val(data.heading);
                $("#newbody").val(data.newbody);
//                alert(data.whatsnewdate)
                var d = new Date(data.whatsnewdate);

                // Format the date as yyyy-MM-dd
                var year = d.getFullYear();
                var month = ('0' + (d.getMonth() + 1)).slice(-2); // Add leading zero for single digit months
                var day = ('0' + d.getDate()).slice(-2); // Add leading zero for single digit days

                var formattedDate = year + '-' + month + '-' + day;

                // Set the formatted date in the input field
                $("#whatsnewdate").val(formattedDate);
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
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var whatsnewid = this.id;
        alert("Are you sure, you want to delete?")
        alert(whatsnewid)
        $.ajax({
            type: "DELETE",
            url: "/secure/deletewhatsnew",
            data: { id: whatsnewid },
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                if(data=='Success'){
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


    $('.submitWhatsNew').click(function (e) {
        e.preventDefault();

        // Create an object instead of an array
        var whatsNewData = {
            whatsnewid: $("#whatsnewid").val(),
            heading: $("#heading").val(),
            newbody: $("#newbody").val(),
            whatsnewdate: $("#whatsnewdate").val()
        };

        alert(JSON.stringify(whatsNewData));

        $.ajax({
            type: "POST",
            url: "/secure/addwhatsnew",
            contentType: "application/json",  // Make sure you're sending JSON
            data: JSON.stringify(whatsNewData), // Directly stringify the object
            success: function (data) {
                if (data == 'success') {
                    alert("Saved Successfully");
                } else {
                    alert("Error: ");
                }
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Error: " + textStatus + " - Exception: " + errorThrown);
            }
        });
    });









});
