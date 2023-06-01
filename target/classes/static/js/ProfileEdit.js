/**
 * Функция для добавления маски на поле ввода номера телефона и почтового индекса
 */
document.addEventListener('DOMContentLoaded', () => {
    const inputElement = document.querySelector('.PhoneNumber')
    const maskOptions = {
        mask: '+{7}(000)000-00-00'
    }
    IMask(inputElement, maskOptions)
    const inputElement2 = document.querySelector('.Index')
    const maskOptions2 = {
        mask: '000 000'
    }
    IMask(inputElement2, maskOptions2)
})

$('#myModal').on('shown.bs.modal', function () {
    $('#myInput').trigger('focus');
})

const profileButton = document.getElementById('profile');
profileButton.style.backgroundColor = '#eaabb5';
const authButton = document.getElementById('auth');
const deleteProfileButton = document.getElementById('deleteProfile');
/**
 * Функция для обработки нажатия на кнопку "Профиль"
 */
profileButton.addEventListener('click', function (event) {
    profileButton.style.backgroundColor = '#eaabb5';
    authButton.style.backgroundColor = '#fff';
    deleteProfileButton.style.backgroundColor = '#fff';
    const element = document.getElementById("profileForm");
    element.style.display = "block";
    const element2 = document.getElementById("authForm");
    element2.style.display = "none";
    const element3 = document.getElementById("deleteProfileForm");
    element3.style.display = "none";
    event.preventDefault();
});
/**
 * Функция для обработки нажатия на кнопку "Авторизация"
 */
authButton.addEventListener('click', function (event) {
    profileButton.style.backgroundColor = '#fff';
    authButton.style.backgroundColor = '#eaabb5';
    deleteProfileButton.style.backgroundColor = '#fff';
    const element = document.getElementById("profileForm");
    element.style.display = "none";
    const element2 = document.getElementById("authForm");
    element2.style.display = "block";
    const element3 = document.getElementById("deleteProfileForm");
    element3.style.display = "none";
    event.preventDefault();
});
/**
 * Функция для обработки нажатия на кнопку "Удалить профиль"
 */
deleteProfileButton.addEventListener('click', function (event) {
    profileButton.style.backgroundColor = '#fff';
    authButton.style.backgroundColor = '#fff';
    deleteProfileButton.style.backgroundColor = '#eaabb5';
    const element = document.getElementById("profileForm");
    element.style.display = "none";
    const element2 = document.getElementById("authForm");
    element2.style.display = "none";
    const element3 = document.getElementById("deleteProfileForm");
    element3.style.display = "block";
    event.preventDefault();
});
/**
 * Функция для сохранения изменений информации о пользователе
 */
document.getElementById("save-info-button").addEventListener("click", function () {
    var surnameValues = [];
    var nameValues = [];
    var patrValues = [];
    var dateOfBirthValues = [];
    var countryValues = [];
    var indexValues = [];
    var cityValues = [];
    var regionValues = [];
    var streetValues = [];
    var houseValues = [];
    var frameValues = [];
    var apartmentValues = [];
    var phoheNumberValues = [];
    var edInstitutionValues = [];
    $('.info').each(function () {
        var value = $(this).find('.userSur').val();
        surnameValues.push(value);
        var value1 = $(this).find('.userNamee').val();
        nameValues.push(value1);
        var value2 = $(this).find('.userPatr').val();
        patrValues.push(value2);
        var value3 = $(this).find('.date').val();
        dateOfBirthValues.push(value3);
        var value4 = $(this).find('.country').val();
        countryValues.push(value4);
        var value5 = $(this).find('.Index').val();
        indexValues.push(value5);
        var value6 = $(this).find('.city').val();
        cityValues.push(value6);
        var value7 = $(this).find('.region').val();
        regionValues.push(value7);
        var value8 = $(this).find('.street').val();
        streetValues.push(value8);
        var value9 = $(this).find('.house').val();
        houseValues.push(value9);
        var value10 = $(this).find('.frame').val();
        frameValues.push(value10);
        var value11 = $(this).find('.apartment').val();
        apartmentValues.push(value11);
        var value12 = $(this).find('.PhoneNumber').val();
        phoheNumberValues.push(value12);
        var value13 = $(this).find('.edInstitution').val();
        edInstitutionValues.push(value13);
    });
    var data = {
        surname: surnameValues,
        name: nameValues,
        patr: patrValues,
        dateOfBirth: dateOfBirthValues,
        country: countryValues,
        index: indexValues,
        city: cityValues,
        region: regionValues,
        street: streetValues,
        house: houseValues,
        frame: frameValues,
        apartment: apartmentValues,
        phoneNumber: phoheNumberValues,
        edInstitution: edInstitutionValues,
    };
    if (surnameValues.includes(null) || surnameValues.includes("") || nameValues.includes(null) || nameValues.includes("") || dateOfBirthValues.includes(null) || dateOfBirthValues.includes("") || countryValues.includes(null) || countryValues.includes("") || indexValues.includes(null) || indexValues.includes("") || cityValues.includes(null) || cityValues.includes("")
        || regionValues.includes(null) || regionValues.includes("") || streetValues.includes(null) || streetValues.includes("") || houseValues.includes(null) || houseValues.includes("") || apartmentValues.includes(null) || apartmentValues.includes("") || phoheNumberValues.includes(null) || phoheNumberValues.includes("") || edInstitutionValues.includes(null) || edInstitutionValues.includes("")) {
        alert("Введите все данные");
    } else {
        $.ajax({
            url: '/handleProfileInfoEdit',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json;charset=UTF-8",
            success: function () {
                $('#myToast').toast('show');
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.error(textStatus, errorThrown);
                alert("Данные невалидны!");
            }
        });
    }
});
/**
 * Функция для сохранения изменений информации об авторизации пользователя
 */
document.getElementById("save-auth-info").addEventListener("click", function () {
    const element2 = document.getElementById("usernameError");
    element2.style.display = "none";
    const element3 = document.getElementById("passwordError");
    element3.style.display = "none";
    const element4 = document.getElementById("oldPasswordError");
    element4.style.display = "none";
    var username = [];
    var oldPassword = [];
    var newPassword = [];
    $('.authForm').each(function () {
        var value = $(this).find('.username').val();
        username.push(value);
        var value1 = $(this).find('.oldPassword').val();
        oldPassword.push(value1);
        var value2 = $(this).find('.newPassword').val();
        newPassword.push(value2);
    });
    var data = {
        username: username,
        oldPassword: oldPassword,
        newPassword: newPassword
    };
    if (username.includes(null) || username.includes("") || oldPassword.includes(null) || oldPassword.includes("") || newPassword.includes(null) || newPassword.includes("")) {
        alert("Введите все данные");
    } else {
        const PASSWORD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
        if (!PASSWORD_REGEX.test(newPassword[0])) {
            const element3 = document.getElementById("passwordError");
            element3.style.display = "block";
        } else {
            $.ajax({
                url: '/handleProfileAuthInfoEdit',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (data, status, xhr) {
                    if (data == "Пароль не совпадает с настоящим") {
                        const element4 = document.getElementById("oldPasswordError");
                        element4.style.display = "block";
                    }
                    if (data == "Пользователь с таким логином уже существует") {
                        const element2 = document.getElementById("usernameError");
                        element2.style.display = "block";
                    }
                    if (data == "Данные успешно сохранены") {
                        $('#myToastAuth').toast('show');
                        const element1 = document.getElementById("oldPassword");
                        const element2 = document.getElementById("newPassword");
                        element1.value = "";
                        element2.value = "";
                    }
                },
                error: function (xhr, status, error) {
                    console.error(xhr.status, error);
                    alert("Ошибка: " + error);
                }
            });
        }
    }
});
/**
 * Функция для удаления профиля пользователя
 */
document.getElementById("deleteProfileButton").addEventListener("click", function () {
    $.ajax({
        url: '/handleUserDelete',
        method: 'DELETE',
        success: function (data) {
            window.location.href = "/login";
        },
        error: function (jqXHR, textStatus) {
            alert(textStatus);
        }
    });
});