var currentPage = 1;
var itemsPerPage = 5;
var coursesData;
var userCoursesData;
var idCourse;
/**
 * Обработчик клика на кнопку "Создать сертификат" на карточке курса
 * @function
 * @returns {void}
 */
$(document).ready(function() {
    $(document).on('click', '.cert-button', function() {
        idCourse = $(this).closest('.cardPhone').attr('id');
    });
    $('#myModal').on('shown.bs.modal', function () {
        $('#myInput').trigger('focus');
    })
    $('.create-button').click(function() {
        const pdfUrl = '/create-certificate?idCourse=' + idCourse;
        window.open(pdfUrl, '_blank');
    });
});


document.addEventListener('DOMContentLoaded', () => {
    $.ajax({
        url: '/check-courses',
        type: 'POST',
        success: function(courses) {
            displayItems(courses);
            displayPagination(courses);
            coursesData = courses;
        },
        error: function() {
            console.error('Error retrieving courses from the server.');
        }
    });
});

const mainPageButton = document.getElementById('mainPage');
const inProgressButton = document.getElementById('inProgress');
const CompletedProfileButton = document.getElementById('Completed');
/**
 * Обработчик клика на кнопку "На главную" на странице кабинета
 * @function
 * @param {object} event - Событие клика на кнопку
 * @returns {void}
 */
mainPageButton.addEventListener('click', function(event) {
    mainPageButton.style.backgroundColor = '#eaabb5';
    inProgressButton.style.backgroundColor = '#fff';
    CompletedProfileButton.style.backgroundColor = '#fff';
    const element = document.getElementById("mainPageDiv");
    element.style.display = "block";
    const element2 = document.getElementById("inProgressDiv");
    element2.style.display = "none";
    const element3 = document.getElementById("completedDiv");
    element3.style.display = "none";
    event.preventDefault();
});
/**
 * Функция проверки объекта на пустоту
 * @function
 * @param {object} obj - Проверяемый объект
 * @returns {boolean} - true, если объект пуст, иначе - false
 */
function isEmpty(obj) {
    if (obj == null) return true;
    if (Array.isArray(obj) || typeof obj === 'string') return obj.length === 0;
    for (var key in obj) {
        if (Object.prototype.hasOwnProperty.call(obj, key))
            return false;
    }
    return true;
}
inProgressButton.addEventListener('click', function(event) {
    mainPageButton.style.backgroundColor = '#fff';
    inProgressButton.style.backgroundColor = '#eaabb5';
    CompletedProfileButton.style.backgroundColor = '#fff';
    const element = document.getElementById("mainPageDiv");
    element.style.display = "none";
    const element2 = document.getElementById("inProgressDiv");
    element2.style.display = "block";
    const element3 = document.getElementById("completedDiv");
    element3.style.display = "none";
    /**
     * Функция отображения курсов, на которые пользователь подписан и которые он проходит в данный момент
     * @function
     * @param {object} data - Данные курсов
     * @returns {void}
     */
    $.ajax({
        url: '/check-inProgress-courses',
        type: 'POST',
        success: function(data) {
            var i = 0;
            var coursesList = $('#courses-inProgress-list');
            coursesList.empty();
            userCoursesData = data;
            if (isEmpty(data)) {
                var noneCourses = $('#noneCourses');
                noneCourses.css('display', 'block');
            } else {
                userCoursesData.forEach(function(course) {
                    var card = $('<div>').addClass('card').css({
                        'height': '100%'
                    }).attr('id', course.idCourse);
                    var cardTitle = $('<h5>').addClass('card-title course-card').text(course.nameOfCourse).css({
                        'bottom': '0',
                        'left': '0',
                        'right': '0',
                        'color': '#fff',
                        'padding': '10px',
                        'background-color': 'rgb(199,199,199)',
                        'margin-bottom': '0',
                        'text-align':'start'
                    });
                    var cardBody = $('<div>').addClass('card-body').css({
                        'display': 'flex',
                        'flex-direction': 'column',
                        'justify-content': 'space-between',
                        'height': '100%'
                    });
                    var cardText = $('<p>').addClass('card-text').text(course.description).css({
                        'text-align':'start',
                        'margin': '0 20px 0 20px'
                    });
                    var cardButtonContainer = $('<div>').css({
                        'display':'flex',
                        'align-items':'center',
                        'justify-content':'end',
                        'margin-top':'0'
                    });
                    var cardButton = $('<a>').addClass('btn btn-course').attr('href', '/main/Courses-' + course.idCourse).text('Продолжить').css('width','150px');
                    var cardLine = $('<hr>').css({
                        'border': 'none',
                        'height': '1px',
                        'background-color': 'rgb(189,189,189)',
                        'margin': '20px 0 0 0'
                    });
                    cardButtonContainer.append(cardButton);
                    cardBody.append(cardText, cardLine, cardButtonContainer);
                    card.addClass("cardPhone");
                    card.append(cardTitle, cardBody);
                    coursesList.append(card);
                    i++;
                });
            }
        },
        error: function() {
            console.error('Error retrieving courses from the server.');
        }
    });
    event.preventDefault();
});

CompletedProfileButton.addEventListener('click', function(event) {
    mainPageButton.style.backgroundColor = '#fff';
    inProgressButton.style.backgroundColor = '#fff';
    CompletedProfileButton.style.backgroundColor = '#eaabb5';
    const element = document.getElementById("mainPageDiv");
    element.style.display = "none";
    const element2 = document.getElementById("inProgressDiv");
    element2.style.display = "none";
    const element3 = document.getElementById("completedDiv");
    element3.style.display = "block";
    /**
     * Функция отображения курсов, на которые пользователь подписан и которые он успешно прошел
     * @function
     * @param {object} data - Данные курсов
     * @returns {void}
     */
    $.ajax({
        url: '/check-completes-courses',
        type: 'POST',
        success: function(data) {
            var i = 0;
            var coursesList = $('#courses-Completes-list');
            coursesList.empty();
            userCoursesData = data;
            if (isEmpty(data)) {
                var noneCourses = $('#noneCoursesComplete');
                noneCourses.css('display', 'block');
            } else {
                userCoursesData.forEach(function(course) {
                    var card = $('<div>').addClass('card').css({
                        'height': '100%'
                    }).attr('id', course.idCourse);
                    var cardTitle = $('<h5>').addClass('card-title course-card').text(course.nameOfCourse).css({
                        'bottom': '0',
                        'left': '0',
                        'right': '0',
                        'color': '#fff',
                        'padding': '10px',
                        'background-color': 'rgb(199,199,199)',
                        'margin-bottom': '0',
                        'text-align':'start'
                    });
                    var cardBody = $('<div>').addClass('card-body').css({
                        'display': 'flex',
                        'flex-direction': 'column',
                        'justify-content': 'space-between',
                        'height': '100%'
                    });
                    var cardText = $('<p>').addClass('card-text').text(course.description).css({
                        'text-align':'start',
                        'margin': '0 20px 0 20px'
                    });
                    var cardButtonContainer = $('<div>').css({
                        'display':'flex',
                        'align-items':'center',
                        'justify-content':'end',
                        'margin-top':'0'
                    });
                    var cardButton = $('<a>').addClass('btn btn-course').attr('href', '/main/Courses-' + course.idCourse).text('Подробнее').css('width','150px');
                    var makeCertificateButton = $('<a>').addClass('btn btn-course cert-button').text('Сертификат').css('width','150px').attr('data-toggle', 'modal').attr('data-target', '#myModal');
                    var testResultsTest = $('<p>').addClass('card-text testResultsTest').css({
                        'text-align':'start',
                        'margin': '0 20px 0 20px'
                    });
                    var cardLine = $('<hr>').css({
                        'border': 'none',
                        'height': '1px',
                        'background-color': 'rgb(189,189,189)',
                        'margin': '20px 0 0 0'
                    });
                    cardButtonContainer.append(testResultsTest,makeCertificateButton,cardButton);
                    cardBody.append(cardText, cardLine, cardButtonContainer);
                    card.addClass("cardPhone");
                    card.append(cardTitle, cardBody);
                    coursesList.append(card);
                    i++;
                    /**
                     * Функция проверки успешности прохождения теста
                     * @function
                     * @param {number} course.idCourse - Идентификатор курса
                     */
                    $.ajax({
                        type: "POST",
                        url: "/check-test-success-main",
                        data: JSON.stringify(course.idCourse),
                        contentType: "application/json",
                        success: function (content) {
                            $(`#${content[3]}`).find('.testResultsTest').text(content[2]);
                        },
                        error: function () {

                        }
                    });
                });
            }
        },
        error: function() {
            console.error('Error retrieving courses from the server.');
        }
    });
    event.preventDefault();
});
/**
 * Функция для обработки нажатия кнопки "Найти"
 * @function
 * @returns {void}
 */
$('#search-button').click(function() {
    var query = $('#search-input').val().toLowerCase();
    var filteredCourses = coursesData.filter(function(course) {
        return course.nameOfCourse.toLowerCase().includes(query);
    });
    currentPage = 1;
    displayItems(filteredCourses);
    displayPagination(filteredCourses);
});
/**
 * Функция отображения элементов на странице
 * @function
 * @param {object} courses - Массив курсов
 * @returns {void}
 */
function displayItems(courses) {
    var i = 0;
    var startIndex = (currentPage - 1) * itemsPerPage;
    var endIndex = startIndex + itemsPerPage;
    var coursesToDisplay = courses.slice(startIndex, endIndex);
    var coursesList = $('#courses-list');
    coursesList.empty();
    coursesToDisplay.forEach(function(course) {
        $.ajax({
            url: '/check-coursesType-' + course.idCourse,
            type: 'POST',
            success: function(courseType) {
                $.ajax({
                    url: '/check-formOf-' + course.idCourse,
                    type: 'POST',
                    success: function(formOfEducation) {
                        var card = $('<div>').addClass('card').css({
                            'height': '100%'
                        }).attr('id', coursesToDisplay[i].idCourse);
                        var cardTitle = $('<h5>').addClass('card-title course-card').text(course.nameOfCourse).css({
                            'bottom': '0',
                            'left': '0',
                            'right': '0',
                            'color': '#fff',
                            'padding': '10px',
                            'background-color': 'rgb(199,199,199)',
                            'margin-bottom': '0',
                            'text-align':'start'
                        });
                        var cardBody = $('<div>').addClass('card-body').css({
                            'display': 'flex',
                            'flex-direction': 'column',
                            'justify-content': 'space-between',
                            'height': '100%'
                        });
                        var cardText = $('<p>').addClass('card-text').text(course.description).css({
                            'text-align':'start',
                            'margin': '0 20px 0 20px'
                        });
                        var cardButtonContainer = $('<div>').css({
                            'display':'flex',
                            'align-items':'center',
                            'justify-content':'end',
                            'margin-top':'-15px'
                        });
                        var cardButton = $('<a>').addClass('btn btn-course').attr('href', '/main/Courses-' + course.idCourse).text('Подробнее').css('width','150px');
                        var cardLine = $('<hr>').css({
                            'border': 'none',
                            'height': '1px',
                            'background-color': 'rgb(189,189,189)',
                            'margin': '20px 0 0 0'
                        });
                        var typeOfCourse = $('<p>').addClass('card-text').text(courseType).css({
                            'color':'#d02d49'
                        });
                        var FormOfEducation = $('<p>').addClass('card-text').text("Форма обучения: " + formOfEducation).css({
                            'color':'#d02d49'
                        });
                        var typeOfCourseAndFormOfEducation = $('<div>').addClass('card-body').css({
                            'display': 'flex',
                            'align-items':'end',
                            'justify-content': 'space-between',
                            'height': '100%'
                        });
                        typeOfCourseAndFormOfEducation.append(FormOfEducation,typeOfCourse);
                        cardButtonContainer.append(cardButton);
                        cardBody.append(cardText, cardLine,typeOfCourseAndFormOfEducation, cardButtonContainer);
                        card.addClass("cardPhone");
                        card.append(cardTitle, cardBody);
                        coursesList.append(card);
                        i++;
                    },
                    error: function() {
                        console.error('Error retrieving courses from the server.');
                    }});
            },
            error: function() {
                console.error('Error retrieving courses from the server.');
            }
        });
    });
}
/**
 * Функция отображения пагинации на странице
 * @function
 * @param {object} courses - Массив курсов
 * @returns {void}
 */
function displayPagination(courses) {
    var totalPages = Math.ceil(courses.length / itemsPerPage);
    var paginationContainer = $('#pagination-container');
    paginationContainer.empty();
    for (var i = 1; i <= totalPages; i++) {
        var button = $('<button>').addClass('btn btn-secondary paginationButton').attr('type', 'button').text(i).css({
            'margin-left': '20px',
            'width':'50px'
        });;
        if (i === currentPage) {
            button.addClass('active');
        }
        if (totalPages > 10 && i != 1 && i != totalPages && (i < currentPage - 2 || i > currentPage + 2)) {
            button.css('display','none');
        }
        button.click(function() {
            currentPage = parseInt($(this).text());
            displayItems(courses);
            displayPagination(courses);
        });
        paginationContainer.append(button);
    }
    var prevButton = $('<button>').addClass('btn btn-course').attr('type', 'button').text('Назад').css('margin-left','20px');
    if (currentPage === 1) {
        prevButton.prop('disabled', true);
    }
    else {
        prevButton.click(function() {
            currentPage--;
            displayItems(courses);
            displayPagination(courses);
        });
    }

    var nextButton = $('<button>').addClass('btn btn-course').attr('type', 'button').text('Вперед').css('margin-left','20px');
    if (currentPage === totalPages) {
        nextButton.prop('disabled', true);
    }
    else {
        nextButton.click(function() {
            currentPage++;
            displayItems(courses);
            displayPagination(courses);
        });
    }

    paginationContainer.prepend(prevButton);
    paginationContainer.append(nextButton);
}

var button = document.getElementById("go-to");

/**
 * Обработчик события клика на кнопку "Подняться вверх"
 * @function
 * @param {object} event - Объект события
 * @returns {void}
 */
button.addEventListener("click", function(event) {
    event.preventDefault();
    var paginationcontainer = document.getElementById("pagination-container");
    var scrollTopPos = paginationcontainer.getBoundingClientRect().top + window.pageYOffset - 0.5 * window.innerHeight;
    document.documentElement.scrollTop = scrollTopPos;
});