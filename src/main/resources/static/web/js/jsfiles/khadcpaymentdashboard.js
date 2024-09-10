$(document).ready(function () {


    $('#emptytable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']]
//                    "sPaginationType": "full_numbers",
//        paging: false



    });
    $('#deliveredregtable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']]
//                    "sPaginationType": "full_numbers",
//        paging: false



    });
    $('#deliverederltable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']]
//                    "sPaginationType": "full_numbers",
//        paging: false



    });

    $('#regdiv').hide()
    $('#erldiv').hide()
    $('#alldiv').hide()

    $("#btnsearch").click(function () {

        alert($("#datefrom").val())
        alert($("#dateto").val())

        if ($("#datefrom").val() == "") {
            alert("Please select date from which you want to filter")
        } else {
            if ($("#dateto").val() == "") {
                alert("Please select date to which you want to filter")
            } else {
               window.location.href = "khadcpaymentbydate.htm?datefrom="+$("#datefrom").val()+"&dateto="+$("#dateto").val();
            }
        }



    })





})






