$(document).ready(function () {

//    alert("Loading...");
    $('#userstable').DataTable();

    $('#savebtn').click(function () {


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
//        alert(userdetailsjson);

        $.ajax({
            type: "POST",
            url: "/secure/createdosubordinate",
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

    $('.deactivateuser').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
        var usercode = this.id;

//        alert(usercode)
        $.ajax({
            type: "POST",
            url: "/secure/deactivedosubordinate",
            data: {"id" : usercode},
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                alert(data);
                location.reload();
//                if (data == '1') {
//                    alert("Data has been saved successfully");
//                    location.reload();
//                } else {
//                    alert("Error");
//                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

    $('.activateuser').click(function () {
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");
         var usercode = this.id;
//         alert(usercode)
        $.ajax({
            type: "POST",
            url: "/secure/activedosubordinate",
            data: {"id" : usercode},
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                alert(data);
                location.reload();
//                if (data == '1') {
//                    alert("Data has been saved successfully");
//                    location.reload();
//                } else {
//                    alert("Error");
//                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })

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

    function encryptPassword(pass) {
       if (pass.length === 0) {
           return "";
       } else {
           var hashedPassword = CryptoJS.SHA256(pass).toString();
           return hashedPassword;
       }
   }

   function generateRandomString(length) {
       const specialChars = "!@#$%^&*()_+{}:<>?|[];,./-=~";
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

