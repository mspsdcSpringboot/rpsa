var paydet;
$(document).ready(function () {
//    alert("js")
    $('#uappealtable').DataTable();
//                var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//                var csrfToken = $("meta[name='_csrf']").attr("content");
//
//
//                $.ajax({
//                    type: "POST",
//                    url: "./initiate.htm",
//                    data: "appealcode=" + $('#appealcode').val(),
//                    beforeSend: function (xhr)
//                    {
//                        xhr.setRequestHeader(csrfHeader, csrfToken);
//                    },
//                    success: function (data) {
//
//                        paydet = data;
//                    },
//                    error: function (jqXHR, textStatus, errorThrown) {
//
//                        alert("error:" + textStatus + " - exception:" + errorThrown);
//                    }
//                });


    $("#pay").click(function () {


        $.ajax({
            type: "POST",
            url: "/secure/initiate",
            data: "appealcode=" + $('#appealcode').val(),

            success: function (data) {

                var newForm = jQuery('<form>', {
                    'action': 'https://megepayment.gov.in/challan/views/frmgrnfordept.php',
//                    'action': 'https://grasuat.megonline.nic.in/challan/views/frmgrnfordept.php',
                    'method': 'post'
                });
                var obj = jQuery.parseJSON(data);
//                var obj = data;
                console.log("Gras response - ", obj)
                jQuery.each(obj, function (key, value) {
                    newForm.append(jQuery('<input>', {
                        'name': key,
                        'value': value,
                        'type': 'hidden'
                    }));
                });

                console.log(newForm.html());
                jQuery(document.body).append(newForm);
                newForm.submit();
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

});


