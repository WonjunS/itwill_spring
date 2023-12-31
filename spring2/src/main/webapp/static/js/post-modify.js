/**
 * 
 */

document.addEventListener('DOMContentLoaded', function() {
   const form = document.querySelector('#postModifyForm');
    
   const btnUpdate = document.querySelector('#btnUpdate');
   const btnDelete = document.querySelector('#btnDelete');
   
   btnUpdate.addEventListener('click', (e) => {
       e.preventDefault();
       
       const title = document.querySelector('input#title').value;
       const content = document.querySelector('textarea#content').value;
       if(title === '' || content === '') {
           alert('제목과 내용은 반드시 입력하세요.');
           return;
       }
       form.action='./update';
       form.method='POST';
       form.submit();
   });
   
   btnDelete.addEventListener('click', (e) => {
       e.preventDefault();
       
       const check = confirm('정말 삭제할까요?');
       if(check) {
           form.action='delete';
           form.method='POST';
           form.submit();
       }
   });
});