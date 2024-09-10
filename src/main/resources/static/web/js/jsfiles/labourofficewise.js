$(document).ready(function () {

//alert($("#serviceid").val())

    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
//
//    $.ajax({
//        type: "GET",
//        url: "./getofficewiselabour.htm",
//        data: "serviceid=" + $("#serviceid").val(),
//        beforeSend: function (xhr)
//        {
//            xhr.setRequestHeader(csrfHeader, csrfToken);
//        },
//        success: function (data) {
//
//            var datalabelss2 = [], datavalues2 = [];
//            for (var i = 0; i < data.length; i++) {
//
//                datalabelss2.push(data[i].locationname)
//                datavalues2.push(parseInt(data[i].pending))
//            }
//            alert("datalabelss2 " + datalabelss2)
//            alert("datavalues2 " + datavalues2)
//
//            var barColors = ["red", "green", "blue", "orange", "brown"];
//            const ctx = document.getElementById('myChart').getContext('2d');
//            const myChart = new Chart(ctx, {
//                type: 'bar',
//                data: {
//                    labels: datalabelss2,
//                    datasets: [{
//                            label: 'Pending with Offices',
//                            data: datavalues2,
//                            backgroundColor: barColors
//                        }]
//                },
//                options: {
//                    maintainAspectRatio: false,
//                    responsive: true
//
//                }
//            });


//
//
//        },
//        error: function (jqXHR, textStatus, errorThrown) {
//
//            alert("error:" + textStatus + " - exception:" + errorThrown);
//        }
//    });
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
    $.ajax({
        type: "GET",
        url: "./getpendingwith.htm",
        data: "serviceid=" + $("#serviceid").val(),
        beforeSend: function (xhr)
        {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (data) {
            const dataSource = {
                chart: {
                    caption: "Pendency Breakup",
                    plottooltext: "<b>$Value</b> ",
                    showlegend: "1",
                    showpercentvalues: "1",
                    legendposition: "bottom",
                    usedataplotcolorforlabels: "1",
                    theme: "ocean"
                },
                data: data
            };

            FusionCharts.ready(function () {
                var myChart = new FusionCharts({
                    type: "pie2d",
                    renderAt: "chart-container2",
                    width: "100%",
                    height: "400",
                    dataFormat: "json",
                    dataSource
                }).render();
            });




        },
        error: function (jqXHR, textStatus, errorThrown) {

            alert("error:" + textStatus + " - exception:" + errorThrown);
        }
    });
//
//
//


});

$(document).ready(function () {
    var csrfHeader = $("meta[name='_csrf_header']").attr("content");
    var csrfToken = $("meta[name='_csrf']").attr("content");
    $.ajax({
        type: "GET",
        url: "./getofficewiselabour.htm",
        data: "serviceid=" + $("#serviceid").val(),
        beforeSend: function (xhr)
        {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function (data) {

            const dataSource = {
                chart: {
                    caption: "Officewise Pendency List",
                    subcaption: "",
                    xaxisname: "Name of Offices",
                    yaxisname: "No. of Applications Pending",
                    numbersuffix: "",
                    theme: "fusion"
                },
                data: data
            };

            FusionCharts.ready(function () {
                var myChart = new FusionCharts({
                    type: "column2d",
                    renderAt: "chart-container",
                    width: "100%",
                    height: "400",
                    dataFormat: "json",
                    dataSource
                }).render();
            });






        },
        error: function (jqXHR, textStatus, errorThrown) {

            alert("error:" + textStatus + " - exception:" + errorThrown);
        }
    });




















});
