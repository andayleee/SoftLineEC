/**
 * Функция для добавления маски на поле ввода пароля
 */
document.addEventListener('DOMContentLoaded', () => {
    const inputElement = document.querySelector('[data-mask="password"]')
    const maskOptions = {
        mask: '********************'
    }
    IMask(inputElement, maskOptions)
})

const inputs = document.querySelectorAll(".user-box input");
/**
 * Функция для добавления класса "focus" родительскому элементу при фокусировке на поле ввода
 */
function addClass(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}
/**
 * Функция для удаления класса "focus" из родительского элемента при потере фокуса на поле ввода, если значение поля пустое
 */
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