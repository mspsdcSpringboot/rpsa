$(document).ready(function () {

    const pathParts = window.location.pathname.split('/'); // Split the URL path
    const subservicecode = pathParts[pathParts.length - 1];
//    alert(subservicecode);

    if(subservicecode != "mapservices"){
        $.ajax({
                        type: "GET",
                        url: "/secure/getmappeddata/" + subservicecode,
            //            data: "subservicecode=" + $('#subservice').val(),
                        success: function (data) {


                                 if (data && data.length > 0) {
                                     console.log("Data :", data);
                                     let subserviceId = data[0].subservice.subservicecode;
                                     $("#subservice").val(subserviceId);
                                     // Automatically select the appellate authority based on the response
                                     let appellateId = data[0].appelate.appelateid;
                                     $("#appellate").val(appellateId);  // Set the appellate dropdown

                                     // Loop through the offices in the response and check the corresponding checkboxes
                                     data.forEach(function(item) {
                                         let officeId = item.offices.officeid;
                                         // Check the checkbox for this office
                                         $("#office" + officeId).prop('checked', true);
                                         toggleCardSelection($("#office" + officeId)[0]);
                                     });
                                 } else {
                                     alert("No mapping data found.");
                                 }

                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            alert("error:" + textStatus + " - exception:" + errorThrown);
                        }
                    });
    }




//    $("#boxes").hide();
//    if ($('#subservicecodeedit').val() != "") {
//        $('#subservice').val($('#subservicecodeedit').val());
//        $.ajax({
//            type: "GET",
//            url: "/secure/getmappeddata",
//            data: "subservicecode=" + $('#subservice').val(),
//            success: function (data) {
//                if (data.length > 0) {
//                    $("input:radio[name=appelateradio][value=" + data[0][0] + "]").prop("checked", true);
//                    for (var n = 0; n < data.length; n++) {
//                        $("#office" + data[n][1]).prop('checked', true);
//
//                    }
//                } else {
//                    alert("Not Mapped")
//                }
//
//            },
//            error: function (jqXHR, textStatus, errorThrown) {
//                alert("error:" + textStatus + " - exception:" + errorThrown);
//            }
//        });
//    }

    $("#subservice").change(function () {

        $(".office").each(function() {
                $(this).prop('checked', false);  // Uncheck all checkboxes
                toggleCardSelection(this);       // Deselect the card visually
        });
        $(".appelate").prop('checked', false);

        $.ajax({
            type: "GET",
            url: "/secure/getmappeddata/" + $('#subservice').val(),
//            data: "subservicecode=" + $('#subservice').val(),
            success: function (data) {
                     console.log(data);

                     if (data && data.length > 0) {
//                        $("#dislogue").hide();
//                        $("#boxes").show();
                         // Automatically select the appellate authority based on the response
                         let appellateId = data[0].appelate.appelateid;
                         $("#appellate").val(appellateId);  // Set the appellate dropdown

                         // Loop through the offices in the response and check the corresponding checkboxes
                         data.forEach(function(item) {
                             let officeId = item.offices.officeid;
                             // Check the checkbox for this office
                             $("#office" + officeId).prop('checked', true);
                             toggleCardSelection($("#office" + officeId)[0]);
                         });
                     } else {
                         alert("No mapping data found.");
                     }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })

    $("#savemapping").click(function () {
        var params = [];
        var $boxes = $('input[name=office]:checked');
        var appelateid = $("#appellate").val();
//        var appelateid = $("input[name=appelateradio]:checked").val();
//        alert($boxes)

        $boxes.each(function () {
//            alert($(this).val())
            params.push({
                appelateid: appelateid,
                subservicecode: $("#subservice").val(),
                officeid: $(this).val()
            });
        });
        var jsonString = encodeURIComponent(JSON.stringify(params));
        alert(JSON.stringify(params))
        console.log(JSON.stringify(params))
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            type: "POST",
            url: "/secure/saveappellatemap",
            data: JSON.stringify(params),
            contentType: "application/json",
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {

                if (data == 'updated') {
                    alert("Saved")
                    location.reload();
                } else {
                    alert("Error")
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })




})
function savemapping() {

}

