

$(document).ready(function () {
    var dept="";
    var options = $("#dept option");                    // Collect options         
    options.detach().sort(function (a, b) {               // Detach from select, then Sort
        var at = $(a).text();
        var bt = $(b).text();
        return (at > bt) ? 1 : ((at < bt) ? -1 : 0);            // Tell the sort function how to order
    });
    options.appendTo("#dept");
    $("#dept").val("0")

    $("select").change(function () {
        $(this).find("option:selected").each(function () {
            var optionValue = $(this).attr("value");
            if (optionValue) {
                $(".box").not("." + optionValue).hide();
                $("." + optionValue).show();
                
            } else {
                $(".box").hide();
            }
        });
        
    }).change();
    
    $("#dept").change(function () {
        
        dept=$("#dept option:selected").text();
         $('.datatable').DataTable().destroy();
         $('.datatable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'LIST OF SERVICES NOTIFIED UNDER RTPSA 2020 FROM '+dept.toUpperCase()
            },
            {
                extend: 'pdfHtml5',
                title: 'LIST OF SERVICES NOTIFIED UNDER RTPSA 2020 FROM '+dept.toUpperCase()
            }
        ],
        "ordering": false
    });
        
    })

    $('.datatable').DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Data export'
            },
            {
                extend: 'pdfHtml5',
                title: 'LIST OF SERVICES NOTIFIED UNDER RTPSA 2020 FROM '+dept
            }
        ],
        "ordering": false
    });

});