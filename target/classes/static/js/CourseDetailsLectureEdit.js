/**
 * Обработчик клика по кнопке "Сохранить лекцию"
 * @function
 * @returns {void}
 */
document.getElementById("save-lecture").addEventListener("click", function () {
    var nameOfLectureValues = [];
    var descriptionValues = [];
    var additionalLiteratureValues = [];
    $('.lecture').each(function () {
        var value = $(this).find('.nameOfLecture').val();
        nameOfLectureValues.push(value);
        var value1 = $(this).find('textarea').val();
        descriptionValues.push(value1);
        var value2 = $(this).find('.additionalLiterature').val();
        additionalLiteratureValues.push(value2);
    });
    var data = {
        nameOfLecture: nameOfLectureValues,
        description: descriptionValues,
        additionalLiterature: additionalLiteratureValues
    };
    if (nameOfLectureValues.includes(null) || nameOfLectureValues.includes("") || descriptionValues.includes(null) || descriptionValues.includes("") || additionalLiteratureValues.includes(null) || additionalLiteratureValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/handleLectureEdit',
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