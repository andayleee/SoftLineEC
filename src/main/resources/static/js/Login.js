const inputs = document.querySelectorAll(".user-box input");
/**
 * Добавляет класс focus родительскому элементу при фокусировке на инпуте
 * @function
 * @returns {void}
 */
function addClass(){
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}
/**
 * Удаляет класс focus у родительского элемента при потере инпутом фокуса, если инпут пустой
 * @function
 * @returns {void}
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