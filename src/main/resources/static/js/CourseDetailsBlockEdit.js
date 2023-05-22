/**
 * Сохраняет изменения в блоках курса на сервере
 * @function
 * @returns {void}
 */
document.getElementById("save-block").addEventListener("click", function () {
    var nameOfBlockValues = [];
    var descriptionValues = [];
    var durationValues = [];
    $('.block').each(function () {
        var value = $(this).find('input').val();
        nameOfBlockValues.push(value);
        var value1 = $(this).find('textarea').val();
        descriptionValues.push(value1);
        var value2 = $(this).find('.number').val();
        durationValues.push(value2);
    });
    var data = {
        nameOfBlock: nameOfBlockValues,
        description: descriptionValues,
        duration: durationValues
    };
    if (nameOfBlockValues.includes(null) || nameOfBlockValues.includes("") || descriptionValues.includes(null) || descriptionValues.includes("") || durationValues.includes(null) || durationValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/handleBlockEdit',
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