const studyingButton = document.getElementById('studying');
/**
 * Обработчик события клика на кнопку "Начать обучение"
 * @function
 * @param {object} event - Объект события
 * @returns {void}
 */
studyingButton.addEventListener('click', function(event) {
    let nameOfCourse = document.getElementsByClassName("nameOfCourse");
    for (let i = 0; i < nameOfCourse.length; i++) {
        var idCourse = nameOfCourse[i].id;
    }
    $.post("/create-users-courses", {idCourse: idCourse}, function (data) {
        if (data=="Успешное добавление курса"){
            window.location.href = "/passingTheCourse";
        }
        if (data=="Данный курс уже есть"){
            window.location.href = "/passingTheCourse";
        }
    });
});
/**
 * Функция загрузки блоков на странице
 * @function
 * @returns {void}
 */
$(document).ready(function() {
    $.ajax({
        type: "POST",
        url: "/check-blocks-main",
        success: function(blocks) {
            if (blocks.length > 0) {
                var element = document.getElementById("tableLabel");
                element.style.display = "block";
                var element2 = document.getElementById("blockTable");
                element2.style.display = "block";
                var table = "<table id='blockTableTable' class='blockTableTable'><thead><tr><th>Название блока</th><th>Описание блока</th><th style='width: 5vw'>Всего часов</th></thead>";
                table += "<tbody>";
                let allDuration = 0;
                $.each(blocks, function(index, block) {
                    table += '<tr id-data='+block.idBlock+'>';
                    table += "<td>" + block.nameOfBlock + "</td>";
                    table += "<td>" + block.description + "</td>";
                    table += "<td style='text-align: center'>" + block.duration + "</td>";
                    table += "</tr>";
                    allDuration += parseInt(block.duration);
                });
                table += '<tr>';
                table += "<td>" + "</td>";
                table += "<td style='text-align: right; color: #d02d49'>" + "Итого часов:" + "</td>";
                table += "<td style='text-align: center; color: #d02d49'>" + allDuration + "</td>";
                table += "</tr>";
                table += "</tbody></table>";
                $("#blockTable").html(table);
            } else {
                $("#blockTable").html("Нет блоков в базе данных");
            }
        },
        error: function() {
            alert("Ошибка при загрузке данных");
        }
    });
});