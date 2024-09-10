$(document).ready(function () {
    $('.validatepay').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var transactioncode = this.id;

//        alert(transactioncode)
        $.ajax({
            type: "GET",
            url: "./updatepayment.htm",
            data: "transactioncode=" + transactioncode,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                alert("Page will be Reloaded. Please check the Status of the Payment")
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

    $('.pay').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");
        var appealcode = this.id;

        $.ajax({
            type: "POST",
            url: "./initiate.htm",
            data: "appealcode=" + appealcode,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                var newForm = jQuery('<form>', {
                    'action': 'https://megepayment.gov.in/challan/views/frmgrnfordept.php',
                    'method': 'post'
                });
                var obj = jQuery.parseJSON(data);
//                var obj = data;
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
})
