/**
 * Created by Kuba on 9. 6. 2015.
 */

$(function () {
    var resultData = JSON.parse("[" + $("#0").data("result") + "]");
    var sourceData = JSON.parse("[" + $("#0").data("source") + "]");
    var chartOptions = {
        chart: {
            renderTo: "chart-container",
            zoomType: "x"
        },
        title: {
            text: 'DNA sequence time series comparision'
        },
        xAxis: {
            title: {
                text: "Sequence length"
            }
        },
        yAxis: {
            title: {
                text: 'Sum value'
            }
        },
        plotOptions: {
            area: {
                marker: {
                    radius: 0.5
                },
                lineWidth: 1,
                states: {
                    hover: {
                        lineWidth: 1
                    }
                },
                threshold: null
            }
        },

        series: [{
            type: 'area',
            name: $("#0 td.name").text(),
            pointInterval: 1,
            pointStart: 0,
            data: resultData,
            color: "#0040DB",
            fillColor: {
                linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                stops: [
                    [0, "rgba(0,174,219,1)"],
                    [1, "rgba(0,174,219,.3)"]
                ]
            }
        },
            {
                type: 'area',
                name: $("#tested-sequence-name").text(),
                pointInterval: 1,
                pointStart: 0,
                data: sourceData,
                color: "#FF5725",
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, "rgba(255,196,37,1)"],
                        [1, "rgba(255,196,37,.3)"]
                    ]
                }
            }]
    };

    var chart = new Highcharts.Chart(chartOptions);

    $("tr").click(function(){
        var resultData = JSON.parse("[" + $(this).data("result") + "]");
        var sourceData = JSON.parse("[" + $(this).data("source") + "]");
        chart.series[0].update({name: $(this).children("td.name").text()});
        chart.series[0].setData(resultData);
        chart.series[1].setData(sourceData, true);
    });
});
