$(document).ready(function () {
//    var myModal = new bootstrap.Modal(document.getElementById('downloadmodal'), options)

    $('#downloadmodal').modal('show');

    $('#printButton').click(function () {
        printDiv('divToPrint');
    });

    function printDiv(divID) {
        var divContents = $('#' + divID).html();
        //                var printWindow = window.open('', '', 'height=800,width=800');
        var printWindow = window.open();
        printWindow.document.write('<html><head><title>Print</title></head><body>');
        printWindow.document.write(divContents);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();
    }
});