var backButton = document.querySelector('.back-button');
var backButton2 = document.querySelector('.pass-button');
var ValidAnswersCount = 0;

$(document).ready(function() {
    var blockCards = document.querySelectorAll(".block-card");
    for (var i = 0; i < 1; i++) {
        blockCards[i].style.display = "block";
    }
    var blockNames = document.querySelectorAll(".block-link");
    for (var i = 0; i < 1; i++) {
        blockNames[i].style.color = "rgb(234, 171, 181)";
    }
    setCheckedCheckboxes();
});
/**
 * Функция для переключения состояния описания лекции
 */
function toggleDescription() {
    var descriptionElem = document.getElementById("description");
    var descriptionButton = document.getElementById("description-button");
    if (descriptionElem.classList.contains("expanded")) {
        descriptionElem.classList.remove("expanded");
        descriptionButton.textContent="Показать полное описание";
    } else {
        descriptionElem.classList.add("expanded");
        descriptionButton.textContent="Скрыть описание";
    }
}

var blockId;
/**
 * Обработчик нажатия на блок
 */
function handleBlockClick() {
    var block = event.target.closest(".name-blocks");
    blockId = block ? block.getAttribute("id") : null;
    var blockName = document.querySelectorAll(".block-link");
    const imagesContainer = document.getElementById("images-container");
    imagesContainer.innerHTML = "";
    const editorContainer = document.getElementById("editor");
    editorContainer.innerHTML = "";
    for (var i = 0; i < blockName.length; i++) {
        blockName[i].style.color = "rgb(0, 0, 0)";
    }
    if ($(event.target).hasClass("block-link")) {
        $(event.target).css("color", "rgb(234, 171, 181)");
    } else {
        $(event.target).find('.block-link').css("color", "rgb(234, 171, 181)");
    }
    var blockCards = document.querySelectorAll(".block-card");
    for (var i = 0; i < blockCards.length; i++) {
        blockCards[i].style.display = "none";
    }
    for (var i = 0; i < blockCards.length; i++) {
        var blockCardId = blockCards[i].getAttribute('id');
        if(blockId==blockCardId){
            blockCards[i].style.display = "block";
        }
    }
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
}
/**
 * Обработчик нажатия на лекцию
 */
function handleLectureClick() {
    const imagesContainer = document.getElementById("images-container");
    imagesContainer.innerHTML = "";
    const editorContainer = document.getElementById("editor");
    editorContainer.innerHTML = "";
    var lecture = event.target.closest(".lectures-names");
    var lectureName = lecture.textContent;
    var nameOfLecture = document.getElementById("name-of-lecture");
    nameOfLecture.textContent = lectureName;
    var lectureId = lecture ? lecture.getAttribute("id") : null;
    var blockCards = document.querySelectorAll(".block-card");
    for (var i = 0; i < blockCards.length; i++) {
        blockCards[i].style.display = "none";
    }
    const element = document.getElementById("lecture-info");
    element.style.display = "block";
    $.ajax({
        type: "POST",
        url: "/check-lectures-photos",
        data: { lectureId: lectureId },
        success: function (photos) {
            for (let i = 0; i < photos.length; i++) {
                const imageContainer = document.createElement("div");
                imageContainer.classList.add("image-container");
                imageContainer.style = 'padding:15px 0px 15px 0px;width: 100%; height: 100%; object-fit: cover;';
                const imageElement = document.createElement("img");
                imageElement.src = photos[i].photoPath;
                imageElement.id = photos[i].idPhoto;
                imageElement.style = 'width: 100%; height: 100%; object-fit: cover; ';
                imageContainer.appendChild(imageElement);
                imagesContainer.appendChild(imageContainer);
            }
        },
        error: function () {

        }
    });
    $.ajax({
        type: "POST",
        url: "/check-lectures-text",
        data: { lectureId: lectureId },
        success: function (content) {
            try {
                const data = JSON.parse(content);
                const quill = new Quill("#editor", {
                    readOnly: true,
                    modules: {
                        toolbar: false
                    }
                });
                quill.setContents(data);
                const testContainer = document.getElementById("existTestContainer");
                testContainer.innerHTML = "";
                backButton.style.display = "block";
                backButton2.style.display = "none";
            }catch (e) {
                loadTest();
            }
        },
        error: function () {
            alert("Ошибка при загрузке данных");
        }
    });
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
}
/**
 * Обработчик нажатия на кнопку "назад"
 */
function handleBackClick() {
    var blockName = document.querySelectorAll(".block-link");
    for (var i = 0; i < blockName.length; i++) {
        if(blockName[i].style.color == "rgb(234, 171, 181)"){
            var blockCards = document.querySelectorAll(".block-card");
            for (var i = 0; i < blockCards.length; i++) {
                blockCards[i].style.display = "none";
            }
            for (var i = 0; i < blockCards.length; i++) {
                var blockCardId = blockCards[i].getAttribute('id');
                if(blockId==blockCardId){
                    blockCards[i].style.display = "block";
                    break;
                }
                if(i == blockCards.length - 1){
                    blockCards[0].style.display = "block";
                }
            }
        }
    }
    const countEl = document.getElementById('count');
    countEl.style.display = "none";
    setCheckedCheckboxes();
    window.scrollTo({
        top: 0,
        left: 0,
        behavior: 'smooth'
    });
}
/**
 * Функция для установки значений чекбоксов в соответствии с сервером
 */
function setCheckedCheckboxes() {
    $.ajax({
        type: "POST",
        url: "/check-checkbox",
        success: function (content) {
            $('.check').each(function () {
                var checkboxId = $(this).attr('id');
                if (content.includes(checkboxId)) {
                    $(this).prop('checked', true);
                }
            });
            const checkboxes = $('.check');
            let allChecked = true;
            checkboxes.each(function() {
                if (!this.checked) {
                    allChecked = false;
                    return false;
                }
            });
            if (allChecked) {
                $.ajax({
                    type: "POST",
                    url: "/set-course-end",
                    success: function (data) {
                        if(data){
                            const alertDiv = $('.alert:first');
                            alertDiv.css('display', 'block');
                            const courseName = $('.course-complete');
                            const text = courseName.text() + 'Данный курс завершен, вы успешно изучили его';
                            courseName.text(text);
                        }
                    },
                    error: function () {

                    }
                });
            }
        },
        error: function () {

        }
    });
}
/**
 * Функция для загрузки теста
 */
function loadTest(){
    $.ajax({
        type: "POST",
        url: "/check-test-success",
        success: function (content) {
            if (content==""){
                $.ajax({
                    type: "POST",
                    url: "/check-lectures-passing-test",
                    success: function (content) {
                        var existTestContainer = document.getElementById("existTestContainer");
                        if (existTestContainer.querySelector(".testTitle")) {
                        } else {
                            if (content && content.hasOwnProperty("idTest")) {
                                backButton.style.display = "none";
                                backButton2.style.display = "block";
                                const existTestContainer = document.getElementById("existTestContainer");
                                const testTitle = document.createElement("h2");
                                testTitle.textContent = content.nameOfTest;
                                testTitle.className ="title1 testTitle mt-2";
                                testTitle.id = content.idTest;
                                existTestContainer.appendChild(testTitle);
                                const questionsList = document.createElement("div");
                                questionsList.className = "questions-list";
                                var index=0;
                                content.tenants.forEach((question, questionIndex) => {
                                    const questionItem = document.createElement("div");
                                    questionItem.className = "question-item";
                                    const questionTitle = document.createElement("h3");
                                    questionTitle.textContent = `${questionIndex + 1}. ${question.nameOfQuestion}`;
                                    const questionScore = document.createElement("h3");
                                    questionScore.textContent = `${content.tenants[index].score} бал.`;
                                    ValidAnswersCount = ValidAnswersCount + content.tenants[index].score;
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
                                        answerInput.name = `question-${questionIndex + 1}-answer-${answerIndex + 1}`;
                                        answerInput.id = `${answer.idAnswerOptions}`;
                                        answerInput.style.display = 'flex';
                                        answerInput.style.alignItems = 'center';
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
                            } else {
                                loadTest = 0;
                                testLectureForm.style.display = "block";
                            }
                        }

                    },
                    error: function () {
                        alert("Ошибка при загрузке данных");
                    }
                });
            }else {
                const existTestContainer = document.getElementById("existTestContainer");
                existTestContainer.innerHTML = "<p style='text-align: start' class='mt-2 mb-2'>" + content[2] + "</p>";
            }
        },
        error: function () {

        }
    });
}
/**
 * Обработчик нажатия на кнопку "Пройти тест"
 */
function handlePassTheTestClick(){
    let answersCount = 0;
    var questions = document.querySelectorAll('.question-item');
    var allAnswers = [];
    for (var i = 0; i < questions.length; i++) {
        var question = questions[i];
        var checkboxes = question.querySelectorAll('input[type="checkbox"]');
        var answers = [];
        for (var j = 0; j < checkboxes.length; j++) {
            if (checkboxes[j].checked) {
                answers.push(checkboxes[j].id);
            }
            allAnswers.push(checkboxes[j].id);
        }
        var data = {
            answers: answers,
            allAnswers: allAnswers
        };
        $.ajax({
            url: '/check-correct-answers',
            type: 'POST',
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (content) {
                const answerItems = document.querySelectorAll('.answer-item');
                for (let i = 0; i < answerItems.length; i++) {
                    const checkbox = answerItems[i].querySelector('input[type="checkbox"]');
                    checkbox.disabled = true;
                    const answerId = parseInt(checkbox.id);
                    const tenant = content.flatMap(q => q.tenants).find(t => t.idAnswerOptions === answerId);
                    if (tenant) {
                        if (tenant.valid==true) {
                            answerItems[i].classList.add('valid-answer');
                        } else {
                            answerItems[i].classList.add('invalid-answer');
                        }
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(textStatus, errorThrown);
            }
        });
        $.ajax({
            url: '/handlePassTheTestClick',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json;charset=UTF-8",
            async: false,
            success: function (data) {
                answersCount = answersCount + parseInt(data);
                const countEl = document.getElementById('count');
                countEl.style.display = "block";
                countEl.textContent = answersCount+" баллов из " + ValidAnswersCount + " возможных";
                const count = countEl.textContent;
                $.ajax({
                    url: '/test-results-save',
                    type: 'POST',
                    data: JSON.stringify(count),
                    contentType: "application/json;charset=UTF-8",
                    async: false,
                    success: function (content) {

                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.error(textStatus, errorThrown);
                    }
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(textStatus, errorThrown);
            }
        });
    }
    backButton.style.display = "block";
    backButton2.style.display = "none";
}
