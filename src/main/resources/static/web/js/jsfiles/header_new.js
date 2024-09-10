$(document).ready(function () {
    //alert("srg")
    $("#forgotpassword").click(function () {
        $('#forgotpasswordmodal').modal('toggle');

    })
   

    $(".closemodal").click(function () {
        $('#forgotpasswordmodal').modal('toggle');

    })


    $("#showfirstappealmodal").click(function () {
//function showfirstappealmodal() {
//alert("f")
        $('#secondappealmodal1').modal('hide');
        $('#firstappealmodal').modal('toggle');

    })
    $("#showsecondappealmodal1").click(function () {
        $('#secondappealmodal1').modal('toggle');

    })
    $("#showmodal").click(function () {
        $('#secondappealmodal1').modal('hide');
        $('#exampleModal').modal('toggle');

    })
    $("#hidemodal").click(function () {
        $('#exampleModal').modal('hide');

    })
    $("#hidefirstappealmodal").click(function () {
        $('#firstappealmodal').modal('hide');

    })
    $("#hidesecondappealmodal1").click(function () {
        $('#secondappealmodal1').modal('hide');

    })
    $("#showyes1appeal").click(function () {
        $('#no1appeal').attr("hidden", true);
        $('#yes1appeal').attr("hidden", false);

    })
    $("#showno1appeal").click(function () {
        $('#no1appeal').attr("hidden", false);
        $('#yes1appeal').attr("hidden", true);

    })

    $('#servicestable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],

        paging: false
//        rowReorder: {
//            selector: 'td:nth-child(2)'
//        },
//        responsive: true
    });

//    $('#servicestable').DataTable({
//        "bJQueryUI": true,
//        order: [[0, 'asc']],
//
//        paging: false
//
//
//
//    });
    $('#cdpolisttable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],
//                    "sPaginationType": "full_numbers",
        paging: false



    });
    $('#offlineservicestable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],
//                    "sPaginationType": "full_numbers",
        paging: false



    });

    $("#err_secucode").hide().fadeOut(3000);
    $('#vctable').DataTable({
        "bJQueryUI": true,
        order: [[0, 'asc']],
//                    "sPaginationType": "full_numbers",
        paging: false


    });

    $("#backbtn").click(function () {
        history.back();
    })


    $("#btnclick").click(function () {
        var secucode = $.trim($("#secucode").val());
//                alert(secucode)
        if (secucode.length == 0) {

            $("#secucode").focus();
            return false;
        } else if (checkcaptcha(secucode) == 0) {
            $("#err_secucode").show().fadeOut(3000);
            $("#secucode").val("");
            changeCaptcha();
            return false;
        } else {

            var pass, get, slt;
            pass = document.getElementById('userpassword').value;
//                    alert(pass)
            if ((pass.toString()).length == 0)
            {
                document.getElementById('userpassword').value = "";
            } else
            {
                slt = document.getElementById('randomKey').value;
                get = sha256_digest(sha256_digest(pass) + slt);
//                        alert(get)
                document.getElementById('userpassword').value = get;

            }
            $("#randomKey").attr("disabled", "disabled");
            return true;
        }

    });

    //captcha
    var t = 120; // interval in seconds
    image = "simpleImg"; //name of the image
    function Start() {

        tmp = new Date();
        tmp = "?" + tmp.getTime();
        document.images["refresh"].src = image + tmp;
        //setTimeout("Start()")
    }
    //********** Capcha
//            function maskfn() {
//                window.open("images/materials/finalmask.pdf", '_blank');
//            }
//            function leftbtn() {
//                window.open("login.htm", '_blank');
//            }
    $("#showpass").click(function () {

        var newpw = document.getElementById("userpassword");

        if (newpw.type === "password") {
            newpw.type = "text";

        } else {
            newpw.type = "password";

        }
    });

    $(".changeCaptcha1").click(function () {

        changeCaptcha();
    });
    $(".changeCaptcha2").click(function () {
        changeCaptcha();
    });


    $('#getpassword').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            type: "POST",
            url: "./generatepassword.htm",
            data: "mobile=" + $("#regmobile").val(),
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {
                $('#forgotpasswordmodal').modal('hide');
                if (data == '1') {
                    alert("A new password has been sent to your Registered Mobile Number.\n Please change your password after logging in.")

                } else if (data == '0') {
                    alert("Invalid Mobile Number. Please check the number and try again")

                } else {
                    alert("An error has occured. Please Try again.")

                }


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    
     $('.contactstable').DataTable({
          "bJQueryUI": true,
//        order: [[0, 'asc']],
//                    "sPaginationType": "full_numbers",
        paging: false,


        responsive: true
    });
     $('#notificationstable').DataTable({
          "bJQueryUI": true,
//        order: [[0, 'asc']],
//                    "sPaginationType": "full_numbers",
        paging: false,
        "ordering": false,


        responsive: true
    });




})

function checkcaptcha(seccode)
{
    var res;
    var param = jQuery("meta[name='_csrf_parameter']").attr("content") + "=" + jQuery("meta[name='_csrf']").attr("content") + "&seccode=" + seccode;

    jQuery.ajax({
        url: "getcaptcha.htm",
        dataType: 'json',
        data: param,
        async: false,
        success: function (data)
        {
            res = data;
//                        alert(data)
        },
        error: function (e) {
            alert(e.status);
        }
    });
    return res;
}

function changeCaptcha() {
    var src = "./jcaptcha.jpg?date=" + new Date();
    $("#jcaptchaimg").attr("src", src);
}




