/**
 * Функция для получения и обработки данных статистики с сервера и отображения на графике
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/get-statistics-data",
        success: function (content) {
            var courseShortsNames = [];
            var courseNames = [];
            var usersCounts = [];
            var usersCompleteCounts = [];

            for (var i = 0; i < content.length; i++) {
                var courseName = content[i].courseName;
                if (courseName.length > 15) {
                    courseName = courseName.substring(0, 15) + '...';
                }
                courseShortsNames.push(courseName);
                usersCounts.push(content[i].usersCount);
                courseNames.push(content[i].courseName);
                usersCompleteCounts.push(content[i].completeCount);
            }

            var completionPercentages = usersCompleteCounts.map((count, index) => {
                var totalUsersCount = usersCounts[index];
                return count / totalUsersCount;
            });

            var ctx = document.getElementById("myChart").getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'horizontalBar',
                data: {
                    labels: courseShortsNames,
                    datasets: [{
                        label: 'Количество пользователей на курсе',
                        data: usersCounts,
                        backgroundColor: 'rgba(208,45,73,0.26)',
                        borderColor: 'rgba(147,32,52,0.49)',
                        borderWidth: 1
                    },{
                        label: 'Количество пользователей завершивших курс',
                        data: usersCompleteCounts,
                        backgroundColor: 'rgba(217,179,74,0.26)',
                        borderColor: 'rgba(147,32,52,0.49)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                stepSize: 10
                            },
                            position: 'top'
                        }],
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }]
                    },
                    tooltips: {
                        mode: 'index',
                        callbacks: {
                            label: function(tooltipItem, data) {
                                var courseIndex = tooltipItem.index;
                                var datasetIndex = tooltipItem.datasetIndex;
                                var value = data.datasets[datasetIndex].data[tooltipItem.index];
                                var label = data.datasets[datasetIndex].label;
                                return `${courseNames[courseIndex]}: ${label}: ${value} пользователей`;
                            }
                        }
                    },
                    animation: {
                        duration: 2000
                    },
                    interactions: {
                        intersect: false,
                        mode: 'index'
                    }
                }
            });
        },
        error: function () {
            alert("Ошибка при загрузке данных");
        }
    });
})