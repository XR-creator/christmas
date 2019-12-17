function init() {
    $('.loading').css('display', 'none');
}

function mask() {
    $('.loading').css('display', 'block');
}

function unmask() {
    $('.loading').css('display', 'none');
}

function getUserInfo() {
    mask();
    $.getJSON("/api/user", function (data) {
        if (!data) return;

        if (data.card) {
            $('#titleRole').text(data.title);
        }
        if (data.cardType) {
            $('typeRole').text(data.cardType)
        }
        if (data.description) {
            $('descriptionRole').text(data.description)
        }
        if (data.pathIcon) {
            $('#imageRole').attr("src","img/" + data.pathIcon);
        }

    }).always(function () {
        unmask();
    });
}