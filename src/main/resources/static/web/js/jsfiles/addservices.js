$(document).ready(function () {



    $('#submitbtn').click(function (e) {
        e.preventDefault();
        var params = [];
        $('#subservicesgrid > tr').each(function (index, tr) {


            params.push({

                subservicecode: $(this).find("input[name='subservicecode']").val(),
                subservicename: $(this).find("input[name='subservicename']").val(),
                stipulatedtime: $(this).find("input[name='stipulatedtime']").val(),
                servicecode: $("#servicecode").val(),
                servicename: $("#servicename").val()


            });

        });
        var jsonString = JSON.stringify(params);

        alert(jsonString);


        $.ajax({
            type: "POST",
            url: "/secure/saveservices",
            data: jsonString,
            contentType: "application/json",
            success: function (data) {
                if (data == 'added') {

                    alert("Saved")
                    location.reload()
                }
                else if (data == 'updated') {
                    alert("Updated")
                    location.reload()
                }else{
                    alert("Error")
                    location.reload()
                }


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })





    $('#addcontactbtn').click(function () {
            var row = ""
            row = "<tr><td><input name='subservicecode' hidden='true' class='form-control'/><input name='subservicename' class='form-control'/></td><td><input name='stipulatedtime' class='form-control'/></td><td><a class='btn btn-danger remove'>Remove</a></td></tr>";
            $("#subservicesgrid").append(row);
        });
    $('#delcontactbtn').click(function () {
        $("#subservicesgrid tr:last").remove();

    });
    $(document).on('click', '.remove', function () {
            $(this).closest('tr').remove();  // Remove the closest row (tr)
        });






    $("#service").change(function () {

    $("#subservicesgrid").html("");

        $.ajax({
            type: "GET",
            url: "/secure/getsubservices/" + $('#service').val(),
            success: function (data) {
                var row = "";
                $("#servicecode").val($('#service').val())
                $("#servicename").val($('#service :selected').text())
               
                if (Array.isArray(data)) {
                    for (var n = 0; n < data.length; n++) {
                        row += buildTableRow(data[n]);
                    }
                } else {
                    // If the response is a single object, handle it directly
                    row += buildTableRow(data);
                }

                // Append the new rows to the table body
                $("#subservicesgrid").append(row);


            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });



    })



    function buildTableRow(subservice) {
        var row = "<tr>";
        row += "<td><input type='hidden' name='subservicecode' value='" + subservice.subservicecode + "' class='form-control' />";
        row += "<input name='subservicename' value='" + subservice.subservicename + "' class='form-control' /></td>";
        row += "<td><input name='stipulatedtime' value='" + subservice.stipulatedtime + "' class='form-control' /></td>";
        row += "<td><a class='btn btn-danger remove'>Remove</a></td>";
        row += "</tr>";
        return row;
    }
    function removeSubservice(subserviceCode) {
            // Implement your removal logic here
            const row = document.querySelector(`input[value="${subserviceCode}"]`).closest('tr');
            if (row) {
                row.remove(); // Remove the row from the table
            }
        }


});
