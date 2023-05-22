/**
 * Обрабатывает данные курса и отправляет их на сервер для сохранения изменений
 * @function
 * @returns {void}
 */
document.getElementById("save-course").addEventListener("click", function () {
    var nameOfCourseValues = [];
    var descriptionValues = [];
    var resourcesValues = [];
    var goalValues = [];
    var tasksValues = [];
    var categoriesOfStudentsValues = [];
    $('.course').each(function () {
        var value = $(this).find('.nameOfCourse').val();
        nameOfCourseValues.push(value);
        var value1 = $(this).find('.description').val();
        descriptionValues.push(value1);
        var value2 = $(this).find('.resources').val();
        resourcesValues.push(value2);
        var value3 = $(this).find('.goal').val();
        goalValues.push(value3);
        var value4 = $(this).find('.tasks').val();
        tasksValues.push(value4);
        var value5 = $(this).find('.categoriesOfStudents').val();
        categoriesOfStudentsValues.push(value5);
    });
    var data = {
        nameOfCourse: nameOfCourseValues,
        description: descriptionValues,
        resources: resourcesValues,
        goal: goalValues,
        tasks: tasksValues,
        categoriesOfStudents: categoriesOfStudentsValues
    };
    if (nameOfCourseValues.includes(null) || nameOfCourseValues.includes("") || descriptionValues.includes(null) || descriptionValues.includes("") || resourcesValues.includes(null) || resourcesValues.includes("") || goalValues.includes(null) || goalValues.includes("") || tasksValues.includes(null) || tasksValues.includes("") || categoriesOfStudentsValues.includes(null) || categoriesOfStudentsValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/handleCourseEdit',
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