$(document).ready(function () {
    $('#submitbtn').attr('disabled', true);
    $('#declaration').click(function () {
        if ($('#declaration').is(':checked')) {
            $('#submitbtn').attr('disabled', false);
        } else {
            $('#submitbtn').attr('disabled', true);
        }
    });

    $("#adddoc").change(function () {
        var fileExtension = ['jpeg', 'jpg', 'png', 'pdf'];
        if ($.inArray($(this).val().split('.').pop().toLowerCase(), fileExtension) == -1) {
            alert("Only formats are allowed : " + fileExtension.join(', '));
            $("#adddoc").val("");
        }
        if (this.files[0].size > 2000000) {
            alert("Please upload file less than 2MB. Thanks!!");
            $(this).val('');
        }
    });
});
