function showLineChart(id, data, title, yAxisTitle) { //data是String eval
    Highcharts.chart(id, {
        chart: {
            zoomType: 'x' //缩放的关键
        },
        title: {
            text: title
        },
        subtitle: {
            text: null
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: {
                millisecond: '%H:%M:%S.%L',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        tooltip: {
            dateTimeLabelFormats: {
                millisecond: '%H:%M:%S.%L',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%Y-%m-%d',
                week: '%m-%d',
                month: '%Y-%m',
                year: '%Y'
            }
        },
        yAxis: {
            title: {
                text: yAxisTitle
            }
        },
        legend: {
            enabled: true
        },
        plotOptions: {
            area: {
                fillColor: {
                    linearGradient: {
                        x1: 0,
                        y1: 0,
                        x2: 0,
                        y2: 1
                    },
                    stops: [
                        [0, Highcharts.getOptions().colors[0]],
                        [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                    ]
                },
                marker: {
                    radius: 2
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
        series: eval(data)
    })
}

function pipeChart(id, data, title, yAxisTitle) {
    var chart = null;
    $(function () {
        $('#' + id).highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                spacing: [100, 0, 40, 0]
            },
            title: {
                floating: true,
                // text: '圆心显示的标题'
                text: title
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    point: {
                        events: {
                            mouseOver: function (e) {  // 鼠标滑过时动态更新标题
                                chart.setTitle({
                                    text: e.target.name + '\t' + (e.target.y*100) + ' %'
                                });
                            }
                        }
                    },
                }
            },
            series: [{
                type: 'pie',
                innerSize: '80%',
                // name: '市场份额',
                name: yAxisTitle,
                // data: [
                //     ['IE', 26.8],
                //     ['Safari', 8.5],
                //     ['Opera', 6.2]
                // ]
                data: eval(data)
            }]
        }, function (c) {
            // 环形图圆心
            var centerY = c.series[0].center[1],
                titleHeight = parseInt(c.title.styles.fontSize);
            c.setTitle({
                y: centerY + titleHeight / 2
            });
            chart = c;
        });
    });
}