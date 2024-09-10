/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var jq = jQuery.noConflict();

//------------------ show form hints ------------------------------------------
//jq(document).ready(function(){
//    jq('input[type=text]').each(function() {
//    jq(this)
//    .data('default', jq(this).val())
//    .addClass('inactive')
//    .focus(function() {
//        jq(this).removeClass('inactive');
//        if(jq(this).val() == jq(this).data('default') || '') {
//            jq(this).val('');
//        }
//    })
//    .blur(function() {
//        if(jq(this).val() == '') {
//            jq(this).addClass('inactive');
//            jq(this).val(jq(this).data('default'));
//        }
//    });
//});
//})

//------------------ xxxxxxxxxxxxxxx ------------------------------------------


function selectAll(obj) {
    if (obj.checked) {
        jq('.selectedchbx').attr('checked', true);
    } else {
        jq('.selectedchbx').attr('checked', false);
    }
}
function checkIfAllSelected() {
    var counter = 0;
    jq('.selectedchbx').each(function() {
        if (!jq(this).attr('checked')) {
            counter += 1;
        }
    })
    if (counter > 0) {
        jq('#checkall').attr('checked', false);
    } else {
        jq('#checkall').attr('checked', true);
    }
}


function isValidDateFormat(srcval) {
    var length = srcval.length;
    if (length < 10) {
        return false;
    }

    var d, m, y;
    tmp = new Array(3);

    tmp = srcval.split("-");

    if (tmp.length != 3) {
        return false;
    }

    d = tmp[0];
    m = tmp[1];
    y = tmp[2];

    if (d < 1 || d > 31) {
        return false;
    }
    if (m < 1 || m > 12) {
        return false;
    }

    if (y < 1900 || y > 2099) {
        return false;
    }
    var format = /^\d{1,2}\-?\d{1,2}\-?\d{4}$/;
    if (format.test(srcval) == false) {
        return false;
    }
    return true;
}

//VALIDATE DATE WITH CURRENT SYSTEM DATE

function isDateAfterCurrentDate(userdate, systemdate) {

    if (userdate == null || systemdate == null) {
        alert("Invalid Date");
        return true;
    } else {

        //--------- user date -------------
        var userDt = userdate.split("-");
        var dayOfUserDate = userDt[0];
        var monthOfUserDate = userDt[1];
        var yearOfUserDate = userDt[2];
        //---------------------------------

        //--------- system date -----------
        var systemDt = systemdate.split("-");
        var dayOfSystemDate = systemDt[0];
        var monthOfSystemDate = systemDt[1];
        var yearOfSystemDate = systemDt[2];
        //---------------------------------

        var userDate = new Date(yearOfUserDate, monthOfUserDate - 1, dayOfUserDate);
        var systemDate = new Date(yearOfSystemDate, monthOfSystemDate - 1, dayOfSystemDate);

        if (userDate <= systemDate) {
            return false;
        }
        return true;
    }

}

function isDateAfterOrEqualCurrentDate(userdate, systemdate) {

    if (userdate == null || systemdate == null) {
        alert("Invalid Date");
        return true;
    } else {

        //--------- user date -------------
        var userDt = userdate.split("-");
        var dayOfUserDate = userDt[0];
        var monthOfUserDate = userDt[1];
        var yearOfUserDate = userDt[2];
        //---------------------------------

        //--------- system date -----------
        var systemDt = systemdate.split("-");
        var dayOfSystemDate = systemDt[0];
        var monthOfSystemDate = systemDt[1];
        var yearOfSystemDate = systemDt[2];
        //---------------------------------

        var userDate = new Date(yearOfUserDate, monthOfUserDate - 1, dayOfUserDate);
        var systemDate = new Date(yearOfSystemDate, monthOfSystemDate - 1, dayOfSystemDate);

        if (userDate < systemDate) {
            return false;
        }
        return true;
    }

}
function isValidAssociationName(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", ".", " ", "(", ")","/","&","'","-"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "0" && ch <= "9") || (ch >= "A" && ch <= "Z") || (ch >= "a" && ch <= "z") || ch == "" || ch == " " || ch == "." || ch == "(" || ch == ")" || ch == "/" || ch == "'" || ch == "&" || ch == "-") {
        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
} //End of function isName()

function isValidLocationName(srcval) {
    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", ".", " ", ,"/","'","-"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);

        if ( ch=="\n" ||   (ch >= "0" && ch <= "9") || (ch >= "A" && ch <= "Z") || ch == "" || ch == " " || (ch >= "a" && ch <= "z") || ch == "." || ch == "/" || ch == "(" || ch == ")" || ch == "-" || ch == ',' || ch == '\\' || ch == ',' || ch == '[' || ch == ']' || ch == '#' || ch == '' || ch == '' || ch == '-') {
            
        } else {
            checker++;
        }
    }
    if (checker == 0) {
        return true;
    } else {
        return false;
    }
} //End of function


function isName(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", "." " ", "(", ")"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "A" && ch <= "Z") || (ch >= "a" && ch <= "z") || ch == "" || ch == " " || ch == "." || ch == "(" || ch == ")") {

        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
} //End of function isName()

function isValidApplicationCode(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", "0-9"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "A" && ch <= "Z") || (ch >= "a" && ch <= "z") || (ch >= "0" && ch <= "9")) {

        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
} //End of function isValidApplicationCode()

function isMobileNumber(e2) {
    var number = /^([1-9]){1}([0-9]){9}$/
    if (e2.search(number) == -1)
    {
        return false;
    }
    return true;
}

function isEmail(value)
{
    var number = /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/
    if (value.search(number) == -1)
    {
        return false;
    }
    return true;
}

function numberTextBox() {
    //allows only ascii of 48->57 (ascii codes of 0 - 9
    if (event.keyCode >= 48 && event.keyCode <= 57) {

    } else {
        event.keyCode = -1;
    }
}

function dateTextBox() {
    //allows only ascii of 48->57 (ascii codes of 0 - 9
    if ((event.keyCode >= 48 && event.keyCode <= 57) || event.keyCode <= 45) {

    } else {
        event.keyCode = -1;
    }
}


function isValidChallnNo(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", "." " ", "(", ")"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "0" && ch <= "9") || (ch >= "A" && ch <= "Z") || (ch >= "a" && ch <= "z") || ch == "" || ch == " " || ch == "." || ch == "/" || ch == "-") {

        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
}

function isFloatingPointNumber(srcval) {
    var amount = /^[+]?\d{1,7}(\.\d{1,2})?$/
    if (amount.test(srcval)) {
        return true
    }
    return false;
}
function isNumber(srcval) {
    var amount = /^[0-9][0-9]*$/
    if (amount.test(srcval)) {
        return true
    }
    return false;
}

function isValidString(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", "." , "_", " ", "!" ,"@","0-9","-"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "0" && ch <= "9") || (ch >= "A" && ch <= "Z") || ch == " " || (ch >= "a" && ch <= "z") || ch == "." || ch == "_" || ch == "!" || ch == "@" || ch == "-" || ch == ",") {
        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
}


function isValidPremisesName(srcval) {

    /*
     Can also be applied to other text fields
     where only permissible characters are : "a-z", "A-Z", "." , "_", " ", "!" ,"@","0-9","-"
     */
    var checker = 0;
    var length = srcval.length;

    for (var i = 0; i < length; i++) {
        var ch = srcval.substring(i, i + 1);
        if ((ch >= "0" && ch <= "9") || (ch >= "A" && ch <= "Z") || ch == " " || (ch >= "a" && ch <= "z") || ch == "." || ch == "," || ch == "/" || ch == "(" || ch == ")" || ch == "-") {
        } else {
            checker++;
        }
    }

    if (checker == 0) {
        return true;
    } else {
        return false;
    }
}


function daysBetweenTwoDates(fromdate, todate) {

    if (fromdate == null || todate == null) {
        alert("Invalid Date");
        return true;
    } else {

        var oneDay = 24*60*60*1000;
        //--------- user date -------------
        var userDt = fromdate.split("-");
        var dayOfUserDate = userDt[0];
        var monthOfUserDate = userDt[1];
        var yearOfUserDate = userDt[2];
        //---------------------------------

        //--------- system date -----------
        var systemDt = todate.split("-");
        var dayOfSystemDate = systemDt[0];
        var monthOfSystemDate = systemDt[1];
        var yearOfSystemDate = systemDt[2];
        //---------------------------------

      

        if (fromdate < todate) {
            alert("Start Date cannot be ahead of End Date");
            return 0;
        }else if(fromdate==todate){
            return 1;
        }else{
           return Math.round(Math.abs((fromdate.getTime() - todate.getTime())/(oneDay)));
        }
        return true;
    }

}


function StringToDate(date){
    
    var parts =date.split('-');
//please put attention to the month (parts[0]), Javascript counts months from 0:
// January - 0, February - 1, etc
    var mydate = new Date(parts[2],parts[1]-1,parts[0]); 
    
    return mydate;
}


function addDaysToDate(date,numberOfDaysToAdd){
    
    date.setDate(date.getDate() + numberOfDaysToAdd); 
//    Formatting to dd/mm/yyyy :

    var dd=date.getDate();;
    if(date.getDate()<=9){
        dd="0"+date.getDate().toString();
    }   
    var mm = date.getMonth() + 1;
    if((date.getMonth() + 1)<=9){
         mm = "0"+(date.getMonth() + 1).toString();
    }
    var y = date.getFullYear();

    
    var someFormattedDate = dd + '-'+ mm + '-'+ y;
    return  someFormattedDate;
}