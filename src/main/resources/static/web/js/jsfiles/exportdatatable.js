

$(document).ready(function () {
    
    
    $("#servicewisetabledo").DataTable({
        dom: 'Bfrtip',
        order: [[0, 'asc']],
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Department Wise Report'
            },
            {
                extend: 'pdfHtml5',
                title: 'Department Wise Report'
            }
        ]
//        "ordering": false
    });
    $("#userhompagetable").DataTable({
        dom: 'Bfrtip',
        
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Department Wise Report'
            },
            {
                extend: 'pdfHtml5',
                title: 'Department Wise Report'
            }
        ]
//        "ordering": false
    });
    $("#reportstable").DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Department/Office Wise Appeals List'
            },
            {
                extend: 'pdfHtml5',
                title: 'Department/Office Wise Appeals List'
            }
        ]
//        "ordering": false
    });
    $("#appelateserviceappeals").DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: $("#deptname").val() + ' Appeals List'
            },
            {
                extend: 'pdfHtml5',
                title: $("#deptname").val() + ' Appeals List'
            }
        ]
//        "ordering": false
    });
    $("#appelatestatuslist").DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: $("#deptname").val() + ' Appeals List'
            },
            {
                extend: 'pdfHtml5',
                title: $("#deptname").val() + ' Appeals List'
            }
        ]
//        "ordering": false
    });
    $("#servicewisetable").DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Service Wise Report'
            },
            {
                extend: 'pdfHtml5',
                title: 'Service Wise Report'
            }
        ]
//        "ordering": false
    });
    $("#officewisetable").DataTable({
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: 'Office Wise Report'
            },
            {
                extend: 'pdfHtml5',
                title: 'Office Wise Report'
            }
        ]
//        "ordering": false
    });

});