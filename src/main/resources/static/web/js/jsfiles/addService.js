
$(document).ready(function () {



    $('#submitbtn').click(function () {
        
        var service = {
            service_name: $("#service_name").val(),
            dept_to_map: $("#dept_to_map").val(),
            subservices: []
    };
        
        $('#subservicesgrid > tr').each(function (index, tr) {
        service.subservices.push({
            "sub_service": $(this).find("input[name='sub_service_name']").val(),
            "sla": $(this).find("input[name='sla']").val(),
            "department": $(this).find("input[name='department']").val(),
            "designated_officials": $(this).find("input[name='officials']").val(),
            "appellate_authority": $(this).find("input[name='appellate']").val(),
            "mode": $(this).find("input[name='sub_service_mode']").val()
        });

        });
        var jsonString = JSON.stringify(service);

        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

//        alert(jsonString);
        $.ajax({
            type: "POST",
            url: "./savenewservice.htm",
            data: "jsonString=" + jsonString,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                if (data == '1') {
                    alert("Saved")
                    location.reload()
                } else {
                    alert("Error")
                }


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    });
    
    
    
    
    //Adding new row for subservice
    $('#addsubservice').click(function () {
        var row = "";

        row = "<tr><td><input name='sub_service_name' class='form-control'/></td><td><input name='sla' class='form-control'/></td><td><input name='department' class='form-control'/></td><td><input name='officials' class='form-control'/></td><td><input name='appellate' class='form-control'/></td><td><input name='sub_service_mode' class='form-control'/></td></tr>";
        $("#subservicesgrid").append(row);

    });
    
    
    
    //Deleting the last row of sub services
    
    $('#delcontactbtn').click(function () {
        $("#subservicesgrid tr:last").remove();

    });
    
    
    
    
    
//    $('.remove').click(function () {
//        $(this).parent().parent().remove();
//
//    });
    
    
    

//    $("#service").change(function () {
//
// $("#subservicesgrid").html("");
//
//        $.ajax({
//            type: "GET",
//            url: "./getsubservices.htm",
//            data: "servicecode=" + $('#service').val(),
//            success: function (data) {
//                var row = "";
//                $("#servicecode").val($('#service').val())
//                $("#servicename").val($('#service :selected').text())
//               
//                for (var n = 0; n < data.length; n++) {
//                    row = row + "<tr><td><input hidden='true' name='subservicecode' value='"+data[n].subservicecode+"' class='form-control'/><input name='subservicename' value='"+data[n].subservicename+"' class='form-control'/></td><td><input name='stipulatedtime' value='"+data[n].stipulatedtime+"' class='form-control'/></td></tr>";
//
//                }
//                $("#subservicesgrid").append(row);
//
//
//            },
//            error: function (jqXHR, textStatus, errorThrown) {
//                alert("error:" + textStatus + " - exception:" + errorThrown);
//            }
//        });
//
//
//
//    })


});
