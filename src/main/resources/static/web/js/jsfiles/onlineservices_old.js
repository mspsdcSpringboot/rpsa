$(document).ready(function () {
    alert("hello")

    $('#btnmodal').click(function () {
        alert("Clicked modal");
//        $('#exampleModal'+$(this).attr('id')).modal('toggle')
    })

//    $("#btnmodal").on('click', '.btnmodal', function() {
//        alert("Clicked modal");
////        var slno = $(this).attr('id');
////        $('#exampleModal' + slno).modal('toggle');
//    });




    $('.btnapply').click(function () {
//        alert($(this).attr('id'))
        var btnid = $(this).attr('id')
        var id = btnid.split("_")
//        alert(id[1])
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            type: "GET",
            url: "/public/applybtnclick",
            data: "slno=" + id[1],
            success: function (data) {
            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    })




    $('.trackbtn').click(function () {
//       alert($(this).attr('id'));
       var id=$(this).attr('id');
//        var csrfHeader = $("meta[name='_csrf_header']").attr("content");
//        var csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            type: "GET",
            url: "./trackapplicationsp.htm",
            data: "applicationrefno=" + $("#track_" + $(this).attr('id')).val(),
//            beforeSend: function (xhr)
//            {
//                xhr.setRequestHeader(csrfHeader, csrfToken);
//            },
            success: function (data) {
                $("#span_"+id).html("<b>"+data+"</b>")
//                alert(id)
//                alert(data)


            },
            error: function (jqXHR, textStatus, errorThrown) {

                alert("error:" + textStatus + " - exception:" + errorThrown);
            }
        });
    });




});




