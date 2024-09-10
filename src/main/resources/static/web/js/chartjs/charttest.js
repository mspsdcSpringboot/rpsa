$(document).ready(function () {


//    Chart.register(ChartDataLabels);
//    var barColors = ["red", "green", "blue", "orange", "brown"];
//    const ctx = document.getElementById('myChart').getContext('2d');
//    const myChart = new Chart(ctx, {
//        type: 'bar',
//        data: {
//            labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
//            datasets: [{
//                    label: '# of Votes',
//                    data: [12, 19, 3, 5, 2, 3],
//                    backgroundColor: barColors
//                }]
//        },
//        options: {
//            maintainAspectRatio: false,
//            responsive: true,
//            plugins: {
//                datalabels: {// This code is used to display data values
//                    anchor: 'end',
//                    align: 'top',
//                    formatter: Math.round,
//                    color: 'blue',
//                    font: {
//                        weight: 'bold',
//                        size: 16
//                    }
//                }
//            }
//        }
//    });
//    var ctx2 = document.getElementById("pie_chart").getContext('2d');
//    var myChart2 = new Chart(ctx2, {
//        type: 'pie',
//        data: {
//            labels: ['Point 1', 'Point 2', 'Point 3', 'Point 4'],
//            datasets: [{
//                    datalabels: {
//                        color: 'black',
//                        backgroundColor: 'white'
//                    },
//                    labels: "Pie chart",
//                    data: [20, 50, 40, 30],
//                    backgroundColor: ["red", "blue", "orange", "green"]
//                }]
//
//        }
//    });

//var FusionCharts = require('fusioncharts');
//var Charts = require('fusioncharts/fusioncharts.charts');
//var FusionTheme = require('fusioncharts/themes/fusioncharts.theme.fusion');




//    $("#chart-container").insertFusionCharts({
//        type: "column2d",
//        width: "100%",
//        height: "100%",
//        dataFormat: "json",
//        dataSource: {
//            chart: {
//                caption: "Countries With Most Oil Reserves [2017-18]",
//                subcaption: "In MMbbl = One Million barrels",
//                xaxisname: "Country",
//                yaxisname: "Reserves (MMbbl)",
//                numbersuffix: "K",
//                theme: "candy"
//            },
//            data: [
//                {
//                    label: "Venezuela",
//                    value: "290"
//                },
//                {
//                    label: "Saudi",
//                    value: "260"
//                },
//                {
//                    label: "Canada",
//                    value: "180"
//                },
//                {
//                    label: "Iran",
//                    value: "140"
//                },
//                {
//                    label: "Russia",
//                    value: "115"
//                },
//                {
//                    label: "UAE",
//                    value: "100"
//                },
//                {
//                    label: "US",
//                    value: "30"
//                },
//                {
//                    label: "China",
//                    value: "30"
//                }
//            ]
//        }
//    });


const dataSource = {
  chart: {
    caption: "Market Share of Web Servers",
    plottooltext: "<b>$percentValue</b> of web servers run on $label servers",
    showlegend: "1",
    showpercentvalues: "1",
    legendposition: "bottom",
    usedataplotcolorforlabels: "1",
    theme: "candy"
  },
  data: [
    {
      label: "Apache",
      value: "32647479"
    },
    {
      label: "Microsoft",
      value: "22100932"
    },
    {
      label: "Zeus",
      value: "14376"
    },
    {
      label: "Other",
      value: "18674221"
    }
  ]
};

FusionCharts.ready(function() {
  var myChart = new FusionCharts({
    type: "pie2d",
    renderAt: "chart-container2",
    width: "100%",
    height: "500",
    dataFormat: "json",
    dataSource
  }).render();
});

    






});


$(document).ready(function () {
        const dataSource = {
  chart: {
    caption: "Countries With Most Oil Reserves [2017-18]",
    subcaption: "In MMbbl = One Million barrels",
    xaxisname: "Country",
    yaxisname: "Reserves (MMbbl)",
    numbersuffix: "K",
    theme: "fusion"
  },
  data: [
    {
      label: "Licenses / Authorizations required for sale / storage of commodities (other than fertilizers).",
      value: "290"
    },
    {
      label: "Saudi",
      value: "260"
    },
    {
      label: "Canada",
      value: "180"
    },
    {
      label: "Iran",
      value: "140"
    },
    {
      label: "Russia",
      value: "115"
    },
    {
      label: "UAE",
      value: "100"
    },
    {
      label: "US",
      value: "30"
    },
    {
      label: "China",
      value: "30"
    }
  ]
};

FusionCharts.ready(function() {
  var myChart = new FusionCharts({
    type: "column2d",
    renderAt: "chart-container",
    width: "100%",
    height: "550",
    dataFormat: "json",
    dataSource
  }).render();
});
});
