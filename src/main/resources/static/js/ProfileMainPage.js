/**
 * Функция для проверки информации о пользователе и отображения ее на странице профиля
 */
document.addEventListener('DOMContentLoaded', () => {
    $.ajax({
        url: '/check-user-info',
        type: 'GET',
        success: function (info) {
            if (info.userSur !== null && info.userNamee !== null) {
                if (info.userPatr !== null) {
                    $('#FIO').text(info.userSur + " " + info.userNamee + " " + info.userPatr);
                } else {
                    $('#FIO').text(info.userSur + " " + info.userNamee);
                }
            }
            if (info.photoLink !== "" && info.photoLink !== null) {
                const image = document.querySelector('.imageProfile');
                image.src = info.photoLink;
            } else {
                const image = document.querySelector('.imageProfile');
                image.src = "/images/Camera.png";
            }
            if(info.phoneNumber !== null&&info.phoneNumber !== ''){
                $('#PhoneNumber').text(info.phoneNumber);
            }else {
                $('#PhoneNumberRow').css('display', 'none');
            }
            if(info.edInstitution !== null&&info.edInstitution !== ''){
                $('#edInstitution').text(info.edInstitution);
            }else {
                $('#edInstitutionRow').css('display', 'none');
            }
            if (info.tenants2.length > 0) {
                if (info.tenants2[0].cityOfAddress !== null && info.tenants2[0].countyOfAddress !== null && info.tenants2[0].cityOfAddress !== '' && info.tenants2[0].countyOfAddress !== '') {
                    $('#CountryAndCity').text(info.tenants2[0].countyOfAddress + ", " + info.tenants2[0].cityOfAddress);
                }
            }else {
                $('#CountryAndCityRow').css('display', 'none');
            }
            if(info.dateOfBirth !== null&&info.dateOfBirth !== ''){
                $('#DateOfBirth').text(info.dateOfBirth);
            }else {
                $('#DateOfBirthRow').css('display', 'none');
            }
        },
        error: function (info) {
        }
    });
});
/**
 * Функция для сохранения фотографии пользователя
 */
document.getElementById("save-photo").addEventListener("click", function () {
    var inputPhoto = document.getElementById('photo-upload');
    var file = inputPhoto.files[0];
    var formData = new FormData();
    formData.append('file', file);

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/handleUserPhotoEdit');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(xhr.responseText);
            window.location.href = "/ProfileInfo";
        } else {
            console.log('Ошибка ' + xhr.status + ': ' + xhr.statusText);
        }
    };
    xhr.send(formData);
});