$(document).ready(function () {
    $('#firstappeal').hide();
    $('#secondappeal').hide();
    $('#fileappeal2').hide();
    $('#recall').hide();

    if ($('#appeallevel').val() === '1') {

        $('#firstappeal').show();
        $('#secondappeal').hide();
        if ($('#statusid').val() == '5' || $('#statusid').val() == '3') {
            if ($('#roleid').val() === '0') {
                $('#fileappeal2').show();
            }


        }
        if ($('#daysleft').val() < '0') {
            if ($('#roleid').val() === '0') {
                $('#fileappeal2').show();
            }
        }
        if ($('#roleid').val() === '2') {
            if ($('#statusid').val() == '1') {
                $('#recall').show();
            } else {
                $('#recall').hide();
            }
        } else {
            $('#recall').hide();
        }





    } else {
        $('#firstappeal').show();
        $('#secondappeal').show();

    }



    $('#forwarddo').click(function () {
        $('#forwarddomodal').modal('show');
    })



    $('#recall').click(function () {
        if (confirm("Are you sure you want to Recall this Appeal?") == true) {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var appealcode = $("#appealcode").val();

//        alert(transactioncode)
            $.ajax({
                type: "POST",
                url: "./recallappeal.htm",
                data: "appealcode=" + appealcode,
                beforeSend: function (xhr)
                {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {
                    alert("Recalled Successfully")
                    location.href = 'viewap.htm?appealcode=' + appealcode;
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert("error:" + textStatus + " - exception:" + errorThrown);
                }
            });
        } else {

        }





    })

    $('#submitbtn').click(function () {
        if (confirm("Are you sure you want forward this Appeal?") == true) {
            var csrfHeader = $("meta[name='_csrf_header']").attr("content");
            var csrfToken = $("meta[name='_csrf']").attr("content");
            var appealcode = $("#appealcode").val();
            var dosub = $("#dosub").val();
            
            var appjson = {
            appealcode: $('#appealcode').val(),
            usercode: $('#dosub').val()
           
        };

        var userdetailsjson = JSON.stringify(appjson);

//        alert(transactioncode)
            $.ajax({
                type: "POST",
                url: "./forwarddoappeal.htm",
                data: "appjson=" + userdetailsjson,
                beforeSend: function (xhr)
                {
                    xhr.setRequestHeader(csrfHeader, csrfToken);
                },
                success: function (data) {
                    alert("Forwarded Successfully")
                    location.href = 'appealstatus.htm?appealcode=' + appealcode;
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert("error:" + textStatus + " - exception:" + errorThrown);
                }
            });
        } else {

        }



    })





});
