var blockNum = 0;
var lectureNum = 0;
containerLecture = document.getElementById('containerLecture');
/**
 * Обрабатывает клик по кнопке "Добавить блок" и создаёт новый блок с уникальным ID
 * @function
 * @returns {void}
 */
document.getElementById("load-block").addEventListener("click", function () {
    $.get("/blockAddSample", {num: blockNum}, function (data) {
        if (blockNum < 15) {
            const newBlock = $("#container").append(data);
            $(".block").each(function (index) {
                const inputElement = document.querySelectorAll('.number')[index];
                const maskOptions = {
                    mask: '000'
                }
                IMask(inputElement, maskOptions);
                $(this).find(".block-num").text(index + 1);
                $(this).attr("data-block-num", index + 1);
                $(this).attr('id', `block-${index + 1}`);
                var col = index + 1;
                $(".col").each(function () {
                    $(this).find(".blockView").text("Блоков: " + col);
                    blockNum = col;
                });
            });
        } else {
            alert("Достигнуто максимальное количество блоков");
        }
    });
    document.getElementById("save-block").style.display = "block";
});
/**
 * Удаляет выбранный блок из списка блоков
 * @function
 * @returns {void}
 */
$("#container").on("click", ".delete-block", function () {
    if (blockNum > 1) {
        $(this).closest(".block").remove();
        $(".block").each(function (index) {
            $(this).find(".block-num").text(index + 1);
            $(this).attr("data-block-num", index + 1);
            $(this).attr('id', `block-${index + 1}`);
            var col = index + 1;
            $(".col").each(function () {
                $(this).find(".blockView").text("Блоков: " + col);
                blockNum = col;
            });
        });
    } else {
    }
});
/**
 * Обрабатывает клик по кнопке "Добавить лекцию" и создаёт новую лекцию в выбранном блоке
 * @function
 * @param {*} event - событие клика
 * @returns {void}
 */
$("#container").on("click", ".add-lecture", function handleClick(event) {
    const blockId = $(event.target).closest('[id^="block-"]').attr('id');
    var nameOfLecture = "";
    $("#" + blockId + ' .no-gutters').each(function () {
        nameOfLecture = $(this).find('input').val();
        $(this).find('input').val("");
    });
    var repeat = 0;
    if (nameOfLecture !== "") {
        $("#" + blockId + " .lecture").each(function (index2) {
            var nameOfLectur = $(this).find(".nameOfLectur").text();
            if (nameOfLectur == nameOfLecture) {
                repeat++;
            }
        });
        if (repeat == 0) {
            var indexMax = 0;
            $.get("/lectureAddSample", {num: lectureNum, nameOfLectur: nameOfLecture}, function (data) {
                const Block = $('#container').find('#' + blockId);
                if (indexMax < 5) {
                    $(".block").each(function (index) {
                        if (blockId == `block-${index + 1}`) {
                            Block.append(data);
                            indexMax++;
                            lectureNum++;
                            $(this).find(".block-num").text(index + 1);
                            $("#" + blockId + " .lecture").each(function (index2) {
                                $(this).find(".lecture-num").text(index2 + 1);
                            });
                        }
                    });
                } else {
                    alert("Достигнуто максимальное количество лекций на 1 блок");
                }
            });
        } else {
            alert("Введите уникальное значение");
        }
    } else {
        alert("Введите наименование лекции");
    }
});
/**
 * Удаляет выбранную лекцию из списка лекций блока
 * @function
 * @param {*} event - событие клика
 * @returns {void}
 */
$("#container").on("click", ".delete-lecture", function handleClick(event) {
    const blockId = $(event.target).closest('[id^="block-"]').attr('id');
    if (lectureNum >= 1) {
        $(this).closest(".lecture").remove();
        lectureNum--;
        $(".block").each(function (index) {
            if (blockId == `block-${index + 1}`) {
                $(this).find(".block-num").text(index + 1);
                $("#" + blockId + " .lecture").each(function (index2) {
                    $(this).find(".lecture-num").text(index2 + 1);
                });
            }
        });
    } else {
    }
});
/**
 * Сохраняет изменения в блоках и лекциях курса на сервере
 * @function
 * @returns {void}
 */
document.getElementById("save-block").addEventListener("click", function () {
    var nameOfBlockValues = [];
    var descriptionValues = [];
    var durationValues = [];
    var nameOfLectureValues = [];
    var blockNumValues = [];
    var blockTrueNumValues = [];
    var blockId = 0;
    $('.block').each(function () {
        blockId++;
        var value = $(this).find('input').val();
        nameOfBlockValues.push(value);
        var value1 = $(this).find('textarea').val();
        descriptionValues.push(value1);
        var value2 = $(this).find('.number').val();
        durationValues.push(value2);
        var value5 = $(this).find('.block-num:eq(0)').text();
        blockTrueNumValues.push(value5);
        $("#block-" + blockId + " .lecture").each(function () {
            var value3 = $(this).find('.nameOfLectur').text();
            nameOfLectureValues.push(value3);
            var value4 = $(this).find('.block-num').text();
            blockNumValues.push(value4);
        });
    });
    var data = {
        nameOfBlock: nameOfBlockValues,
        description: descriptionValues,
        duration: durationValues,
        nameOfLecture: nameOfLectureValues,
        blockNum: blockNumValues,
        blockTrueNum: blockTrueNumValues
    };
    if (nameOfBlockValues.includes(null) || nameOfBlockValues.includes("") || descriptionValues.includes(null) || descriptionValues.includes("") || durationValues.includes(null) || durationValues.includes("") || nameOfLectureValues.includes(null) || nameOfLectureValues.includes("") || blockNumValues.includes(null) || blockNumValues.includes("") || blockTrueNumValues.includes(null) || blockTrueNumValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/blockAddSample',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json;charset=UTF-8",
            success: function (data) {
                window.location.href = "/CreateNewCourse/details";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(textStatus, errorThrown);
                alert("Данные невалидны!");
            }
        });
    }
});