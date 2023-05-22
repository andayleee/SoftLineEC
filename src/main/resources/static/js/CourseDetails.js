/**
 * Выполняет запрос на сервер для получения списка блоков и создает таблицу со списком блоков
 * @function
 * @returns {void}
 */
$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/check-blocks",
        success: function (blocks) {
            if (blocks.length > 0) {
                var element = document.getElementById("tableLabel");
                element.style.display = "block";
                var element2 = document.getElementById("blockTable");
                element2.style.display = "block";
                var element3 = document.getElementById("nonText");
                element3.style.display = "none";
                var table = "<table id='blockTableTable'><thead><tr><th>Название блока</th><th>Описание блока</th><th style='width: 10vw'>Продолжительность (ч.)</th><th style='width: 1vw'></th><th style='width: 1vw'></th><th style='width: 1vw'></th></tr></thead>";
                table += "<tbody>";
                $.each(blocks, function (index, block) {
                    table += '<tr id-data=' + block.idBlock + '>';
                    table += "<td>" + block.nameOfBlock + "</td>";
                    table += "<td>" + block.description + "</td>";
                    table += "<td>" + block.duration + "</td>";

                    table += "<td><button onclick='editBlock(" + block.idBlock + ")'>✎</button></td>";
                    table += "<td><button onclick='deleteBlock(" + block.idBlock + ")'>х</button></td>";
                    table += "<td><button onclick='showLectures(" + block.idBlock + ")'>Лекции</button></td>";
                    table += "</tr>";
                });
                table += "</tbody></table>";
                $("#blockTable").html(table);
            } else {
                $("#blockTable").html("Нет блоков в базе данных");
                var element3 = document.getElementById("nonText");
                element3.style.display = "block";
            }
        },
        error: function () {
            alert("Ошибка при загрузке данных");
        }
    });
});
/**
 * Удаляет блок курса по его ID
 * @function
 * @param {number} id - ID удаляемого блока
 * @returns {void}
 */
function deleteBlock(id) {
    const table = document.getElementById("blockTableTable");
    $.ajax({
        type: "DELETE",
        url: "/blocks/" + id,
        success: function () {
            var element = document.getElementById("lectureLabel");
            element.style.display = "none";
            var element2 = document.getElementById("lectureTable");
            element2.style.display = "none";
            var element = document.getElementById("tableLabel");
            element.style.display = "none";
            $("#blockTable tr[id-data='" + id + "']").remove();
            if (table.rows.length == 1) {
                table.style.display = 'none';
                var element3 = document.getElementById("nonText");
                element3.style.display = "block";
            } else {
                table.style.display = '';
                var element3 = document.getElementById("nonText");
                element3.style.display = "none";
            }
        },
        error: function () {
            console.error("Ошибка при удалении блока");
        }
    });
}
/**
 * Отправляет на сервер запрос на редактирование блока курса по его ID
 * @function
 * @param {number} id - ID редактируемого блока
 * @returns {void}
 */
function editBlock(id) {
    $.ajax({
        url: "/CreateNewCourse/details/Block/Edit",
        type: "POST",
        data: {id: id},
        success: function () {
            window.location.href = "/CreateNewCourse/details/Block/Edit";
        }
    });
}
/**
 * Выполняет запрос на сервер для получения списка лекций блока и создает таблицу со списком лекций
 * @function
 * @param {number} id - ID блока, для которого необходимо получить список лекций
 * @returns {void}
 */
function showLectures(id) {

    $.ajax({
        type: "POST",
        url: "/check-lectures/" + id,
        success: function (lectures) {
            if (lectures.length > 0) {
                var element = document.getElementById("lectureLabel");
                element.style.display = "block";
                var element2 = document.getElementById("lectureTable");
                element2.style.display = "block";
                var table1 = "<table id='lectureTableTable'><thead><tr><th>Название лекции</th><th style='width: 1vw'></th><th style='width: 1vw'></th><th style='width: 1vw'></th></tr></thead>";
                table1 += "<tbody>";
                $.each(lectures, function (index, lecture) {
                    table1 += '<tr id-data=' + lecture.idLecture + '>';
                    table1 += "<td>" + lecture.nameOfLecture + "</td>";

                    table1 += "<td><button onclick='editLecture(" + lecture.idLecture + ")'>✎</button></td>";
                    table1 += "<td><button onclick='deleteLecture(" + lecture.idLecture + ")'>х</button></td>";
                    table1 += "<td><button onclick='showDetails(" + lecture.idLecture + ")'>Детали</button></td>";
                    table1 += "</tr>";
                });
                table1 += "</tbody></table>";
                $("#lectureTable").html(table1);
            } else {
                $("#lectureTable").html("Нет лекций в базе данных");
            }
        },
        error: function () {
            alert("Ошибка при загрузке данных");
        }
    });
}
/**
 * Удаляет лекцию по ее ID
 * @function
 * @param {number} id - ID удаляемой лекции
 * @returns {void}
 */
function deleteLecture(id) {
    const table = document.getElementById("lectureTableTable");
    $.ajax({
        type: "DELETE",
        url: "/lecture/" + id,
        success: function () {
            $("#lectureTable tr[id-data='" + id + "']").remove();
            if (table.rows.length == 1) {
                table.style.display = 'none';
                var element = document.getElementById("lectureLabel");
                element.style.display = "none";
            } else {
                table.style.display = '';
                var element = document.getElementById("lectureLabel");
                element.style.display = "block";
            }
        },
        error: function () {
            console.error("Ошибка при удалении лекции");
        }
    });
}
/**
 * Отправляет на сервер запрос на редактирование лекции курса по ее ID
 * @function
 * @param {number} id - ID редактируемой лекции
 * @returns {void}
 */
function editLecture(id) {
    $.ajax({
        url: "/CreateNewCourse/details/Lecture/Edit",
        type: "POST",
        data: {id: id},
        success: function () {
            window.location.href = "/CreateNewCourse/details/Lecture/Edit";
        }
    });
}
/**
 * Показывает детали лекции по ее ID
 * @function
 * @param {number} id - ID лекции, для которой необходимо показать детали
 * @returns {void}
 */
function showDetails(id) {
    $.ajax({
        url: "/CreateNewCourse/details/Lecture/Edit/Content",
        type: "POST",
        data: {id: id},
        success: function () {
            window.location.href = "/CreateNewCourse/details/Lecture/Edit/Content";
        }
    });
}