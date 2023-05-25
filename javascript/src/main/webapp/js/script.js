/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
    const btnInput = document.getElementById('btnInput');
    const list = document.getElementById('itemList');
        
    btnInput.addEventListener('click', function() {
        const itemInput = document.querySelector('input#itemInput').value;
            
        list.innerHTML += '<li>' + itemInput + '</li>';
            
        document.querySelector('input#itemInput').value = '';
            
        document.querySelector('input#itemInput').focus();
    });
    
    const list2 = document.querySelector('ul#itemList2');
    const itemInput = document.getElementById('itemInput2');
    
    itemInput.addEventListener('keydown', function(event) {
        // 모든 이벤트 핸들러 함수(콜백)은 이벤트 객체를 argument로 전달받음
        if(event.key === 'Enter') {
            list2.innerHTML += '<li>' + itemInput.value + '</li>';
            itemInput.value = '';
        }
    });
    
    const username = document.querySelector('input#username');
    const age = document.querySelector('input#age');
    const result = document.querySelector('div#result');
    
    username.addEventListener('change', function(e) {
        updateNameAndAge();
    });
    
    age.addEventListener('change', function(e) {
        updateNameAndAge();
    });
    
    function updateNameAndAge() {
        const name = username.value;
        const age2 = age.value;
        const text = `<b>이름:</b> ${name}, <b>나이:</b> ${age2}`;
        result.innerHTML = text;
    }
    
});