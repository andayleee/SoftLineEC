/**
 * Опции для панели инструментов Quill
 * @constant {Object[]}
 */
var toolbarOptions = [
    [{'font': []}],
    [{'size': ['small', false, 'large', 'huge']}],
    ['bold'],
    ['italic'],
    ['underline'],
    ['strike'],
    [{'color': []}, {'background': []}],
    ['blockquote', 'code-block'],
    [{'list': 'ordered'}, {'list': 'bullet'}],
    [{'script': 'sub'}, {'script': 'super'}],
    [{'indent': '-1'}, {'indent': '+1'}],
    [{'align': []}],
    ['clean']
];
/**
 * Создаёт новый экземпляр Quill и настраивает его
 * @type {Quill}
 */
var quill = new Quill('#editor', {
    modules: {
        toolbar: toolbarOptions
    },
    theme: 'snow'
});
var container = document.getElementById('container');
var countOfButton = 0;
const imagesContainer = document.getElementById("images-container");
const textLectureButton = document.getElementById('textLectureButton');
const testLectureButton = document.getElementById('testButton');
const textLectureForm = document.getElementById("textLectureForm");
const testLectureForm = document.getElementById("testLectureForm");
const existTestLectureForm = document.getElementById("existTestLectureForm");
const buttonRow = document.getElementById("button-row");
var loadTest = 0;
/**
 * Обработчик клика по кнопке "Текст"
 * @function
 * @param {Event} event - Событие клика
 * @returns {void}
 */
textLectureButton.addEventListener('click', function(event) {
    textLectureButton.style.backgroundColor = '#eaabb5';
    testLectureButton.style.backgroundColor = '#fff';
    textLectureForm.style.display = "block";
    testLectureForm.style.display = "none";
    existTestLectureForm.style.display = "none";
    event.preventDefault();
});
/**
 * Обработчик клика по кнопке "Тест"
 * @function
 * @param {Event} event - Событие клика
 * @returns {void}
 */
const testLectureHandler = function(event) {
    textLectureButton.style.backgroundColor = '#fff';
    testLectureButton.style.backgroundColor = '#eaabb5';
    textLectureForm.style.display = "none";
    if (loadTest == 0) {
        loadTest++;
        $.ajax({
            type: "POST",
            url: "/check-lectures-test",
            success: function (content) {
                if (content && content.hasOwnProperty("idTest")) {
                    existTestLectureForm.style.display = "block";
                    const existTestContainer = document.getElementById("existTestContainer");
                    const testTitle = document.createElement("h2");
                    testTitle.textContent = content.nameOfTest;
                    testTitle.className ="title1";
                    testTitle.id = content.idTest;
                    existTestContainer.appendChild(testTitle);
                    const questionsList = document.createElement("div");
                    questionsList.className = "questions-list";
                    var index = 0;
                    content.tenants.forEach((question, questionIndex) => {
                        const questionItem = document.createElement("div");
                        questionItem.className = "question-item";
                        const questionTitle = document.createElement("h3");
                        questionTitle.textContent = `${questionIndex + 1}. ${question.nameOfQuestion}`;
                        const questionScore = document.createElement("h3");
                        questionScore.textContent = `${content.tenants[index].score} бал.`;
                        const questionTitleItem = document.createElement("div");
                        index++;
                        questionTitleItem.style.display = "flex";
                        questionTitleItem.style.flexDirection = "row";
                        questionTitleItem.style.justifyContent = "space-between";
                        questionTitleItem.appendChild(questionTitle);
                        questionTitleItem.appendChild(questionScore);
                        questionItem.appendChild(questionTitleItem);
                        const answersList = document.createElement("ul");
                        answersList.className = "answers-list";
                        question.tenants.forEach((answer, answerIndex) => {
                            const answerItem = document.createElement("li");
                            answerItem.className = "answer-item";
                            answerItem.style.display = "flex";
                            const answerInput = document.createElement("input");
                            answerInput.type = "checkbox";
                            if (answer.valid === true) {
                                answerInput.checked = true;
                            }
                            answerInput.disabled = true;
                            answerInput.name = `question-${questionIndex + 1}-answer-${answerIndex + 1}`;
                            answerInput.id = `question-${questionIndex + 1}-answer-${answerIndex + 1}`;
                            const answerLabel = document.createElement("p");
                            answerLabel.setAttribute("id", `question-${questionIndex + 1}-answer-${answerIndex + 1}`);
                            answerLabel.textContent = answer.content;
                            answerItem.appendChild(answerInput);
                            answerItem.appendChild(answerLabel);
                            answersList.appendChild(answerItem);

                        });
                        questionItem.appendChild(answersList);
                        questionsList.appendChild(questionItem);
                    });
                    existTestContainer.appendChild(questionsList);
                    buttonRow.remove();
                } else {
                    loadTest = 0;
                    testLectureForm.style.display = "block";
                }
            },
            error: function () {
                alert("Ошибка при загрузке данных");
            }
        });
    }else {
        existTestLectureForm.style.display = "block";
    }
    event.preventDefault();
};
/**
 * Обработчик клика по кнопке "Тест"
 * @type {HTMLButtonElement}
 */
testLectureButton.addEventListener('click', testLectureHandler);

$(document).ready(function () {
    $.ajax({
        type: "POST",
        url: "/check-lectures-test",
        success: function (content) {
            if (!content || content === null || content === undefined || content === '') {
                textLectureButton.style.backgroundColor = '#eaabb5';
                $.ajax({
                    type: "POST",
                    url: "/check-lectures-details",
                    success: function (photos) {
                        for (let i = 0; i < photos.length; i++) {
                            const imageContainer = document.createElement("div");
                            imageContainer.classList.add("image-container");
                            imageContainer.style = 'padding-top:30px;width: 40%; height: 100%; object-fit: cover;';
                            const imageElement = document.createElement("img");
                            imageElement.src = photos[i].photoPath;
                            imageElement.id = photos[i].idPhoto;
                            imageElement.style = 'width: 100%; height: 100%; object-fit: cover; ';
                            const deleteButton = document.createElement("button");
                            deleteButton.innerText = "Удалить";
                            deleteButton.addEventListener("click", () => {
                                $.ajax({
                                    type: "DELETE",
                                    url: "/photo/" + photos[i].idPhoto,
                                    success: function () {
                                        imageContainer.remove();
                                    },
                                    error: function () {
                                        console.error("Ошибка при удалении блока");
                                    }
                                });
                            });
                            imageContainer.appendChild(imageElement);
                            imageContainer.appendChild(deleteButton);
                            imagesContainer.appendChild(imageContainer);
                        }
                    },
                    error: function () {
                        alert("Ошибка при загрузке данных");
                    }
                });
                $.ajax({
                    type: "POST",
                    url: "/check-lectures-details-text",
                    success: function (content) {
                        const data = JSON.parse(content);
                        quill.setContents(data);
                        buttonRow.remove();
                    },
                    error: function () {
                        alert("Ошибка при загрузке данных");
                    }
                });
            } else {
                testLectureHandler();
            }
        },
        error: function () {
            alert("Ошибка при загрузке данных");
        }
    });

});

var questionsNum = 0;
var ansewrOptionsNum = 0;
containerLecture = document.getElementById('containerLecture');
/**
 * Обработчик клика по кнопке "Загрузить вопрос"
 * @function
 * @returns {void}
 */
document.getElementById("load-question").addEventListener("click", function () {
    $.get("/questionAddSample", {num: questionsNum}, function (data) {
        if (questionsNum < 15) {
            const newBlock = $("#testContainer").append(data);
            $(".block").each(function (index) {
                const inputElement = document.querySelectorAll('.number')[index];
                const maskOptions = {
                    mask: /^[1-9]$/
                }
                IMask(inputElement, maskOptions);
                $(this).find(".block-num").text(index + 1);
                $(this).attr("data-block-num", index + 1);
                $(this).attr('id', `block-${index + 1}`);
                var col = index + 1;
                $(".col").each(function () {
                    $(this).find(".questionsView").text("Вопросов: " + col);
                    questionsNum = col;
                });
            });
            buttonRow.remove();
        } else {
            alert("Достигнуто максимальное количество блоков");
        }
    });
    document.getElementById("save-test").style.display = "block";
});
/**
 * Обработчик клика по кнопке "Удалить блок"
 * @function
 * @returns {void}
 */
$("#testContainer").on("click", ".delete-block", function () {
    if (questionsNum > 1) {
        $(this).closest(".block").remove();
        $(".block").each(function (index) {
            $(this).find(".block-num").text(index + 1);
            $(this).attr("data-block-num", index + 1);
            $(this).attr('id', `block-${index + 1}`);
            var col = index + 1;
            $(".col").each(function () {
                $(this).find(".questionsView").text("Вопросов: " + col);
                questionsNum = col;
            });
        });
    } else {
    }
});
/**
 * Обработчик клика по кнопке "Добавить лекцию"
 * @function
 * @param {Event} event - Событие клика
 * @returns {void}
 */
$("#testContainer").on("click", ".add-lecture", function handleClick(event) {
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
            $.get("/answerOptionsAddSample", {num: ansewrOptionsNum, nameOfLectur: nameOfLecture}, function (data) {

                const Block = $('#testContainer').find('#' + blockId);

                if (indexMax < 5) {
                    $(".block").each(function (index) {
                        if (blockId == `block-${index + 1}`) {
                            Block.append(data);
                            indexMax++;
                            ansewrOptionsNum++;
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
 * Обработчик клика по кнопке "Удалить лекцию"
 * @function
 * @param {Event} event - Событие клика
 * @returns {void}
 */
$("#testContainer").on("click", ".delete-lecture", function handleClick(event) {
    const blockId = $(event.target).closest('[id^="block-"]').attr('id');
    if (ansewrOptionsNum >= 1) {
        $(this).closest(".lecture").remove();
        ansewrOptionsNum--;
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
 * Обработчик клика по кнопке "Удалить тест"
 * @function
 * @returns {void}
 */
document.getElementById("deleteTestButton").addEventListener("click", function () {
    const title1El = document.querySelector(".title1");
    $.ajax({
        type: "DELETE",
        url: "/delete-test/" + title1El.id,
        success: function (response) {
            if (response=="Тест успешно удален"){
                location.reload(false);
            };
            if (response=="Тест не удален"){
                alert(response);
            };
        },
        error: function () {
            alert("Ошибка при удалении теста");
        }
    });
    event.preventDefault();
});
/**
 * Обработчик клика по кнопке "Сохранить тест"
 * @function
 * @returns {void}
 */
document.getElementById("save-test").addEventListener("click", function () {
    var nameOfTestValues = [];
    var nameOfQuestionValues = [];
    var scoreValues = [];
    var nameOfAnswerOptionsValues = [];
    var isValidValues = [];
    var blockNumValues = [];
    var blockTrueNumValues = [];
    var blockId = 0;
    $('.nameOfTest').each(function() {
        var value0 = $(this).val();
        nameOfTestValues.push(value0);
    });
    $('.block').each(function () {
        blockId++;
        var value = $(this).find('input').val();
        nameOfQuestionValues.push(value);
        var value2 = $(this).find('.number').val();
        scoreValues.push(value2);
        var value5 = $(this).find('.block-num:eq(0)').text();
        blockTrueNumValues.push(value5);
        $("#block-" + blockId + " .lecture").each(function () {
            var value3 = $(this).find('.nameOfLectur').text();
            nameOfAnswerOptionsValues.push(value3);
            var value4 = $(this).find('.block-num').text();
            blockNumValues.push(value4);
            var value6 = $(this).find('.isValid').prop('checked');
            isValidValues.push(value6);
        });
    });
    var data = {
        nameOfTest: nameOfTestValues,
        nameOfQuestion: nameOfQuestionValues,
        score: scoreValues,
        nameOfAnswerOptions: nameOfAnswerOptionsValues,
        isValid: isValidValues,
        blockNum: blockNumValues,
        blockTrueNum: blockTrueNumValues
    };
    if (nameOfTestValues.includes(null) || nameOfTestValues.includes("") || nameOfQuestionValues.includes(null) || nameOfQuestionValues.includes("") ||
        scoreValues.includes(null) || scoreValues.includes("") || nameOfAnswerOptionsValues.includes(null) || nameOfAnswerOptionsValues.includes("") ||
        blockNumValues.includes(null) || blockNumValues.includes("") || blockTrueNumValues.includes(null) || blockTrueNumValues.includes("") ||
        isValidValues.includes(null) || isValidValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/questionAddSample',
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
/**
 * Функция добавления поля выбора фото
 * @function
 * @returns {void}
 */
document.getElementById("add-photo-button").addEventListener("click", function () {
    if (countOfButton < 6) {
        var newdiv = document.createElement('div');
        var deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'x';
        deleteBtn.id = countOfButton.toString();
        deleteBtn.className = countOfButton.toString();
        deleteBtn.addEventListener('click', function () {
            deletePhoto(deleteBtn.id);
        });
        var newInput = document.createElement('input');
        newInput.type = 'file';
        newInput.accept = '.png, .jpg, .jpeg, .svg';
        newInput.id = countOfButton.toString();
        newInput.style = 'margin-right: 5vw';
        newInput.className = 'custom-btn btn-16 inputPhoto';
        newdiv.style = 'display: flex';
        newdiv.className = 'photo';
        newdiv.appendChild(newInput);
        newdiv.appendChild(deleteBtn);
        container.appendChild(newdiv);
        countOfButton++;
    } else {
        alert("Достигнуто максимальное кол-во фото");
    }
});
/**
 * Функция удаления выбранной фотографии
 * @function
 * @param {string} id - Идентификатор элемента, связанного с конкретной фотографией
 * @returns {void}
 */
function deletePhoto(id) {
    if (countOfButton > 1) {
        var element = document.getElementsByClassName(id)[0];
        var imgButton = document.getElementById(id);
        imgButton.remove();
        element.remove();
        countOfButton--;
    } else {
    }
}
/**
 * Обработчик клика по кнопке "Сохранить лекцию"
 * @function
 * @returns {void}
 */
document.getElementById("save-lecture").addEventListener("click", function () {
    var content = quill.getContents();
    var contentText = quill.getText().replace(/\s/g, '');
    var formData = new FormData();
    if (contentText.length === 0) {
        alert("Заполните текст лекции");
    } else {
        formData.append('content', JSON.stringify(content));
        const files = document.querySelectorAll('#container input[type="file"]');
        if (files.length != 0) {
            for (let i = 0; i < files.length; i++) {
                const file = files[i].files[0];
                formData.append('files', file);
            }
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/handleLectureContentEdit');
            xhr.send(formData);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    window.location.href = "/CreateNewCourse/details";
                } else {
                    console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
                }
            }
        } else {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', '/handleLectureContentEditWithoutPhoto');
            xhr.send(formData);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    window.location.href = "/CreateNewCourse/details";
                } else {
                    console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
                }
            }
        }
    }
});