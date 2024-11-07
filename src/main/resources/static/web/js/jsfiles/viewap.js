$(document).ready(function () {


    document.getElementById('submitprocessbtn').addEventListener('click', function(event) {
        // Prevent the default form submission
        event.preventDefault();

        const submitButton = document.getElementById('submitprocessbtn');
        submitButton.disabled = true;
        submitButton.innerHTML = "Processing...";

        // Get the form element
        const form = document.getElementById('processappeal');
        var appealCode = document.getElementById('appealCodes').value;


        // Create a FormData object from the form
        const formData = new FormData(form);

        // Convert the FormData into a plain object
        const dataObject = {};
        formData.forEach((value, key) => {
            dataObject[key] = value;
        });

        // Log the form data object to the console in a formatted manner
        console.log("Appeal Form Data -", dataObject);
        console.log("Appeal Code -", appealCode);


        $.ajax({
            type: 'POST',
            url: '/secure/processAppeal/' + appealCode,
            data: formData,
            processData: false, // Important for FormData
            contentType: false, // Important for FormData
            success: function(data) {
                window.location.href = '/secure/appealProcessSuccess';

            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error('Error:', textStatus, errorThrown);
                alert('There is a problem problem in process the appeal !');
            }
        });

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
    $('#processappeal').hide();
    $('#forwardto').hide();
    $('#link1').hide();
    $('#sendalert').hide();

    $('.callforhearingaction').hide();
    $('#rejectaction').hide();



    var hdate = new Date($("#hearingdate").val());

//    if ($('input[name="appelateactioncode"]:checked').val()) {
////        alert("value")
//        $('#actions').val("2")
//        $('#actions').prop("disabled", true)
//        $('#processappeal').show();
//        $('#forwardto').hide();
//        if ($('input[name="appelateactioncode"]:checked').val() == '1') {
//            if ($("#hearingdate").val() == '' || hdate >= new Date()) {
//                $('.dontdispose').show();
//                $('.dispose').hide();
//                $('.dontdisposelabel').show();
//                $('.disposelabel').hide();
//                $('.callforhearingaction').show();
//                $('#rejectaction').hide();
//                if ($("#hearingtype").val() == '2') {
//                    $('#link1').show();
//                } else {
//                    $('#link1').hide();
//                }
//
//            } else if (hdate < new Date()) {
//                $('.dontdispose').hide();
//                $('.dispose').show();
//                $('.dontdisposelabel').hide();
//                $('.disposelabel').show();
//                $('#rejectaction').show();
//            }
//
//
//        } else if ($('input[name="appelateactioncode"]:checked').val() == '2' || $('input[name="appelateactioncode"]:checked').val() == '3') {
//            $('.dontdispose').hide();
//            $('.dispose').hide();
//            $('.dontdisposelabel').hide();
//            $('.disposelabel').hide();
//            $('.callforhearingaction').hide();
//            $('#rejectaction').hide();
//            $('#submitprocessbtn').hide();
//            $('#rejected').show();
//            $('#processappeal').hide();
//            $('#forwardto').hide();
//            $('#takeaction').hide();
//            $('#updatedo').hide();
//
//
//        }
//
//
//    } else {
//        $('.dontdispose').show();
//        $('.dispose').show();
//        $('.dontdisposelabel').show();
//        $('.disposelabel').show();
//        $('.callforhearingaction').hide();
//        $('#rejectaction').hide();
//    }

    $('#sendalertbtn').click(function () {
//        alert($("#alertdo").val())

//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");



        var userdetails;

        userdetails = {
            officeid: $('#officeid').val(),
            appealcode: $('#appealCodes').val(),
            email: $('#email').val(),
            content: $('#alertcontent').val()
            

        };

        var userdetailsjson = JSON.stringify(userdetails);
        alert(userdetailsjson);
        $.ajax({
            type: "POST",
            url: "/secure/sendAlertToDo",
            data: userdetailsjson,
            contentType: "application/json",
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                alert(data);
                location.href = '/secure/aainbox';

//                if (data != '-1') {
//                    alert("Alert has sent saved successfully");
//                    location.href = 'inboxa.htm';
//                } else {
//                    alert("Unsuccessfull");
//                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })


    $('#actions').change(function () {

        if ($('#actions').val() == '1') {
            $('#forwardto').show();
            $('#processappeal').hide();
            $('#sendalert').hide();

        } else if ($('#actions').val() == '2') {
            $('#processappeal').show();
            $('#forwardto').hide();
            $('#sendalert').hide();
        } else if ($('#actions').val() == '3') {
            $('#processappeal').hide();
            $('#forwardto').hide();
            $('#sendalert').show();
        }

    });

    $('#selectremarks').change(function () {
        if ($('#selectremarks').val() == '0') {
            $('#remarks').show();
            $('#remarks').focus();
            $('#remarks').val("");
        } else {

            $('#remarks').hide();
            $('#remarks').val($('#selectremarks :selected').text());
        }
    })


    $('.actions').on("click", function () {
        var a = $('input[name="appelateactioncode"]:checked').val();
        if (a == '1') {
            $('.callforhearingaction').show();
            $('#rejectaction').hide();
            if ($("#hearingtype").val() == '1') {
                $('#offline').show();
            } else {
                $('#offline').hide();
            }

        } else if (a == '2') {

            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#selectremarks').hide();
            $('#remarks').show();


        } else if (a == '3') {

            $('#rejectaction').show();
            $('.callforhearingaction').hide();
            $('#selectremarks').show();
            $('#remarks').hide();
        }

    });






    $('#forwardbtn').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");



        var userdetails;

        userdetails = {
            fusercode: $('input[name="users"]:checked').val(),
            forwardremarkstxt: $('#forwardremarkstxt').val()

        };

        var userdetailsjson = JSON.stringify(userdetails);
        var appealCode = $('#appealCodes').val()

        alert(userdetailsjson);
        alert(appealCode);

        $.ajax({
            type: "POST",
            url: "/secure/saveforwardedusers/" + appealCode,
            data: "appjson=" + userdetailsjson,

            success: function (data) {

                if (data == '-1') {
                    alert("Data has been saved successfully");
                    location.href = '/secure/aainbox';
                } else {
                    alert("Unsuccessfull");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });

    });

    $('#updatedo').click(function () {
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
        var csrfToken = $("meta[name='_csrf']").attr("content");



        var dodetails;

        dodetails = {
            officeid: $('#officeid').val(),
            officername: $('#officername').val(),
            officename: $('#officename').val(),
            email: $('#email').val(),
            username: $('#username').val(),
            mobile: $('#mobile').val()

        };

        var userdetailsjson = JSON.stringify(dodetails);
//                    alert(userdetailsjson);
        $.ajax({
            type: "POST",
            url: "./updatedo.htm",
            data: "appjson=" + userdetailsjson,
            beforeSend: function (xhr)
            {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (data) {

                if (data == '1') {
                    alert("Data has been saved successfully");
                    location.reload()
                } else {
                    alert("Unsuccessfull");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })
    $('#showmodal').click(function () {
        $('#exampleModal').modal('toggle')
    })
    $('#hidemodal').click(function () {
        $('#exampleModal').modal('hide')
    })
    $('#saveuser').click(function () {


            var userdetails;
            var generatedPassword = generateRandomString(8);

            userdetails = {
                fullname: $('#fullname').val(),
                contact: $('#contact').val(),
                username: $('#email').val(),
                password: generatedPassword,
                pw: encryptPassword(generatedPassword),
                role: "10",
                email: $('#email').val(),
                designation: $('#designation').val()
            };

            var userdetailsjson = JSON.stringify(userdetails);
            alert(userdetailsjson);

            $.ajax({
                type: "POST",
                url: "/secure/createaasubordinate",
                data: userdetailsjson,
                contentType: "application/json",
                success: function (data) {
                    alert(data);
                    location.reload();
    //                if (data != '-1') {
    //                    alert("Data has been saved successfully");
    //                    location.reload();
    //                } else {
    //                    alert("Username Already Exists");
    //                }
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    alert("error:" + textStatus + " - exception:" + errorThrown);
                }
            });

        });

});





function makeid(length) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() *
                charactersLength));
    }
    return result;
}


function viewAndOpenPdf() {
    var referenceNo = "LRWDL/2023/00108";
    var submissionDate = "10/08/2023"; // Format: "yyyy-MM-dd"
    var apiUrl = "https://megedistrict.gov.in/getApplicationDetailPdf";

    var requestData = {
        referenceNo: referenceNo,
        submissionDate: submissionDate
    };

    var xhr = new XMLHttpRequest();
    xhr.open("POST", apiUrl, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.responseType = "blob"; // Treat response as binary data (PDF content)

    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var blob = xhr.response;
                var pdfUrl = URL.createObjectURL(blob);

                // Open PDF in a new tab
                window.open(pdfUrl);
            } else {
                console.error("Error fetching PDF:", xhr.status, xhr.statusText);
            }
        }
    };

    xhr.send(JSON.stringify(requestData));
}


    function generateRandomString(length) {
       const specialChars = "@#$%";
       const upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
       const lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
       const numbers = "0123456789";

       // Helper function to get a random character from a string
       const getRandomChar = (str) => str[Math.floor(Math.random() * str.length)];

       // Ensure at least one of each required character
       let randomString = [
           getRandomChar(specialChars),         // At least one special character
           getRandomChar(upperCaseLetters),     // At least one uppercase letter
           getRandomChar(lowerCaseLetters),     // At least one lowercase letter
           getRandomChar(numbers)               // At least one number
       ];

       // Fill the remaining characters with random choices from all categories
       const allChars = specialChars + upperCaseLetters + lowerCaseLetters + numbers;
       for (let i = randomString.length; i < length; i++) {
           randomString.push(getRandomChar(allChars));
       }

       // Shuffle the array to ensure randomness of character placement
       randomString = randomString.sort(() => Math.random() - 0.5);

       // Convert array to string and return
       return randomString.join('');
   }

   function encryptPassword(pass) {
          if (pass.length === 0) {
              return "";
          } else {
              var hashedPassword = CryptoJS.SHA256(pass).toString();
              return hashedPassword;
          }
      }
