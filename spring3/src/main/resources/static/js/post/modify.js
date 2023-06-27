/**
 * 포스트 업데이트 & 삭제
 */
document.addEventListener('DOMContentLoaded', () => {
    const btnUpdate = document.querySelector('button#btnUpdate');
    const btnDelete = document.querySelector('button#btnDelete');
    
    const form = document.querySelector('#modify-form');
    
    btnUpdate.addEventListener('click', (e) => {
        e.preventDefault();
        
        const title = document.querySelector('input#title').value;
        const content = document.querySelector('textarea#content').value;
        
        if(title == '') {
            alert('제목을 입력해주세요');
            return false;
        }
        if(content == '') {
            alert('내용을 입력해주세요.');
            return false;
        }
        
        if(confirm('정말 수정하시겠습니까?')) {
            form.method='POST';
            form.action='/post/modify';
            form.submit();
        }
    });
    
    btnDelete.addEventListener('click', (e) => {
        e.preventDefault();
        
        if(confirm("정말 삭제할까요?")) {
            form.method='POST';
            form.action='/post/delete'
            form.submit();
        }
    });
});