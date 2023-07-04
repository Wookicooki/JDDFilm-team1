$(document).ready(() => {
    $('#btn-search').on('click', () => {
        const keyword = $('#keyword').val();
        location.href = "/movie/search/" + keyword;
    });
});
