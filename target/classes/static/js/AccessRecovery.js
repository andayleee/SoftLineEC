const inputs = document.querySelectorAll(".user-box input");
const NextButton = document.getElementById("next-button");
const PhoneNumberField = document.getElementById("PhoneNumber");
const ConfirmationCode = document.getElementById("ConfirmationCode");
const NewPassword = document.getElementById("NewPassword");
const EmailBox = document.getElementById('Email');
const EmailField = document.querySelector('#emailField');
const UserEmail = document.querySelector('#userEmail');
const ConfirmationCodeField = document.querySelector('#ConfirmationCodeField');
const NewPasswordField = document.querySelector('#Password');
/**
 * Обработчик события DOMContentLoaded, выполняющий инициализацию маски для полей ввода номера телефона и кода подтверждения.
 */
document.addEventListener('DOMContentLoaded', () => {
    const inputElement = document.querySelector('.PhoneNumber')
    const inputElement2 = document.querySelector('.ConfirmationCode')
    const maskOptions = {
        mask: '+{7}(000)000-00-00'
    }
    IMask(inputElement, maskOptions)
    const maskOptions2 = {
        mask: '000 000'
    }
    IMask(inputElement2, maskOptions2)
    PhoneNumberField.style.display = "none";
});
/**
 * Обработчик события click на кнопке "Далее" для восстановления данных пользователя.
 * Отправляет AJAX-запрос на сервер и в зависимости от ответа скрывает/показывает поля ввода электронной почты и номера телефона.
 */
$('#next-button').click(function() {
    $.ajax({
        url: '/user-data-recovery',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(EmailField.value),
        success: function (data, status, xhr) {
            if (data=="Пользователь есть"){
                const userEmailValue = EmailField.value;
                UserEmail.textContent = userEmailValue;
                EmailBox.style.display = "none";
                NextButton.style.display = "none";
                PhoneNumberField.style.display = "block";
            }
            if (data=="Пользователя нет"){
                alert("Пользователь не найден");
            }
        },
        error: function (xhr, status, error) {
            console.error(xhr.status, error);
            alert("Ошибка: " + error);
        }
    });
});
/**
 * Отправляет код восстановления данных пользователю на его номер телефона
 * @function
 * @returns {void}
 */
$('#send-message-button').click(function() {
    var username = [];
    username.push(EmailField.value);
    var phone = [];
    phone.push(document.querySelector('#PhoneNumberField').value);
    var data = {
        username: username,
        phone: phone
    };
    $.ajax({
        url: '/user-data-recovery-code-send',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (data, status, xhr) {
            if (data=="Пароль отправлен"){
                PhoneNumberField.style.display = "none";
                ConfirmationCode.style.display = "block";
            };
            if (data=="Неверный номер телефона"){
                alert(data);
            };
        },
        error: function (xhr, status, error) {
            console.error(xhr.status, error);
            alert("Ошибка: " + error);
        }
    });
});
/**
 * Отправляет код подтверждения для изменения пароля пользователя
 * @function
 * @returns {void}
 */
$('#confirmation-message-button').click(function() {
    $.ajax({
        url: '/user-data-confirmation-message',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(ConfirmationCodeField.value),
        success: function (data, status, xhr) {
            if (data=="Верный код подтверждения"){
                ConfirmationCode.style.display = "none";
                NewPassword.style.display = "block";
            };
            if (data=="Неверный код подтверждения"){
                alert(data);
            };
        },
        error: function (xhr, status, error) {
            console.error(xhr.status, error);
            alert("Ошибка: " + error);
        }
    });
});
/**
 * Отправляет новый пароль пользователя на сервер
 * @function
 * @returns {void}
 */
$('#password-send-button').click(function() {
    const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
    if (!PASSWORD_REGEX.test(NewPasswordField.value)) {
        const element3 = document.getElementById("passwordError");
        element3.style.display = "block";
    }else {
        $.ajax({
            url: '/user-data-password-send',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(NewPasswordField.value),
            success: function (data, status, xhr) {
                if (data == "Пароль изменен успешно") {
                    alert(data);
                    window.location.href = "/login";
                }
                ;
                if (data == "Пароль не изменен") {
                    alert("Ошибка");
                }
                ;
            },
            error: function (xhr, status, error) {
                console.error(xhr.status, error);
                alert("Ошибка: " + error);
            }
        });
    }
});
/**
 * Добавляет класс 'focus' к родительскому элементу при получении фокуса инпутом
 * Удаляет класс 'focus' от родительского элемента при потере фокуса инпутом, если инпут пустой
 * @function
 * @returns {void} */
function addClass(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function removeClass(){
    let parent = this.parentNode.parentNode;
    if(this.value == ""){
        parent.classList.remove("focus");
    }
}

inputs.forEach(input => {
    input.addEventListener("focus", addClass);
    input.addEventListener("blur", removeClass);
});