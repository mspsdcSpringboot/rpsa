$(document).ready(function () {

    $('.autoresizing').on('input', function () {
        this.style.height = 'auto';

        this.style.height =
                (this.scrollHeight) + 'px';
    });

    $("#hearingendtime1").blur(function () {
        var starttimestr = $("#hearingtime1").val()
        starttimestr = starttimestr.replace(":", "")
        var endtimestr = $("#hearingendtime1").val()
        endtimestr = endtimestr.replace(":", "")

        var starttime = parseInt($("#hearingtime1").val())
        var endtime = parseInt($("#hearingendtime1").val())
        if (starttime >= endtime) {
            alert("End Time should be Greater than Start time")
            $("#hearingendtime1").val("")
        }
    })

    $('#rejected').hide();

    $('#link1').hide();
    $('#directions').hide();
    $('#extrasla').hide();

    $('.callforhearingaction').hide();
    $('#rejectaction').hide();



    $('.callforhearingaction').hide();
    $('#rejectaction').hide();
    var hdate = new Date($("#hearingdate").val());
//                alert($('input[name="commissionactioncode.actioncode"]:checked').val())
    if ($("#statusid").val() != "5" && $("#statusid").val() != "3" && $("#statusid").val() != "8") {
        if ($('input[name="commissionactioncode.actioncode"]:checked').val()) {
//                    alert("value")
            $('#cprocessappeal').hide();
//$('#updatedo').hide();

            if ($('input[name="commissionactioncode.actioncode"]:checked').val() == '1') {
                if ($("#hearingdate").val() == '' || hdate > new Date()) {
                    $('.dontdispose').show();
                    $('.dispose').hide();
                    $('.dontdisposelabel').show();
                    $('.disposelabel').hide();
                    $('.callforhearingaction').show();
                    $('#rejectaction').hide();
                    if ($("#hearingtype").val() == '2') {
                        $('#link1').show();
                    } else {
                        $('#link1').hide();
                    }

                } else if (hdate < new Date()) {
                    $('.dontdispose').hide();
                    $('.dispose').show();
                    $('.dontdisposelabel').hide();
                    $('.disposelabel').show();
                    $('#rejectaction').show();
                    $('#cprocessappeal').show();


                }


            } else if ($('input[name="commissionactioncode.actioncode"]:checked').val() == '2' || $('input[name="commissionactioncode.actioncode"]:checked').val() == '3') {
                $('.dontdispose').hide();
                $('.dispose').hide();
                $('.dontdisposelabel').hide();
                $('.disposelabel').hide();
                $('.callforhearingaction').hide();
                $('#rejectaction').hide();
                $('#submitprocessbtn').hide();
                $('#rejected').show();


            } else if ($('input[name="commissionactioncode.actioncode"]:checked').val() == '7' || $('input[name="commissionactioncode.actioncode"]:checked').val() == '8') {
                if ($('#doaction').val() == '1') {
                    $('.dontdispose').hide();
                    $('.dispose').show();
                    $('.dontdisposelabel').hide();
                    $('.disposelabel').show();
                    $('#rejectaction').show();
                    $('#cprocessappeal').show();
                } else {
                    $('#cprocessappeal').hide();
                }

            }


        } else {
            $('.dontdispose').show();
            $('.dispose').show();
            $('.dontdisposelabel').show();
            $('.disposelabel').show();
            $('.callforhearingaction').hide();
            $('#rejectaction').hide();
        }
    } else {
        $('.dontdispose').hide();
        $('.dispose').hide();
        $('.dontdisposelabel').hide();
        $('.disposelabel').hide();
        $('.callforhearingaction').hide();
        $('#rejectaction').hide();
        $('#submitprocessbtn').hide();
        $('#rejected').show();
    }




    $('.actions').change(function () {

        var a = $('input[name="commissionactioncode.actioncode"]:checked').val();
//                    alert(a)
        if (a == '1') {
            $('.callforhearingaction').show();
            $('#rejectaction').hide();
            $('#directions').hide();

            $('#extrasla').hide();
            if ($("#hearingtype").val() == '1') {
                $('#offline').show();
            } else {
                $('#offline').hide();
            }

        } else if (a == '2') {

            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#directions').hide();

            $('#extrasla').hide();

        } else if (a == '7' || a == '8') {
            $('#rejectaction').hide();
            $('.callforhearingaction').hide();
            $('#directions').show();

            $('#extrasla').show();
//                        $('#extrasla').val("0");

        } else {
            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#directions').hide();

            $('#extrasla').hide();
        }

    });




//                $('#callforhearingaction').hide();
//                $('#rejectaction').hide();
//
//               
//
//
//
//                $('.actions').change(function () {
//                    
//                    var a = $('input[name="commissionactioncode.actioncode"]:checked').val();
//                   
//                    if (a == '1') {
//                        $('#callforhearingaction').show();
//                        $('#rejectaction').hide();
//
//                    } else if(a == '2') {
//
//                        $('#rejectaction').show();
//                        $('#callforhearingaction').hide();
//
//                    }
//                });







});

