let teamLeadType = 'TEAM_LEAD';

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
    $("#questionButtons").prop('display', 'none');

    $.getJSON("/api/user", function (data) {
        if (!data) return;

        if (!data.card) return;

        if (data.card.title) {
            $('#titleRole').text(data.card.title);
        }
        if (data.card.cardTypeDescription) {
            $('#typeRole').text(data.card.cardTypeDescription);
        }
        if (data.card.description) {
            $('#descriptionRole').text(data.card.description)
        }
        if (data.card.cardType === teamLeadType) {
            $("#questionButtons").css('display', 'grid');
        }
        if (data.card.pathIcon) {
            $('#imageRole').attr("src", "img/" + data.card.pathIcon);
        }
        if (data.isAdmin) {
            $('#settingsBtn').css('display', 'block');
        }

    }).always(function () {
        unmask();
    });
}

function defaultDisable(isDisabled) {
    if ($('#wordText')[0] && isDisabled) $('#wordText').text("---");
    if ($('#requestBtn')[0]) $('#requestBtn').prop('disabled', isDisabled);
    if ($('#repeatBtn')[0]) $('#repeatBtn').prop('disabled', isDisabled);
}

function getQuestion() {
    mask();
    let type = new URL(window.location.href).searchParams.get("type");
    defaultDisable(true);

    $.getJSON("/api/question?cardType=" + type, function (data) {
        if (!data) return;

        defaultDisable(false);
        $('#wordText').text(data.text);

        getUsers(data.id);
    }).always(function () {
        unmask();
    });
}

function getUsers(questionId) {
    mask();
    $.getJSON("/api/user/withoutLead", function (data) {
        if (!data) return;

        $.each(data, function (index, item) {
            if (!item) return;

            $('#listUsers').append("<a href=\"\" class=\"list-group-item list-group-item-action\" onclick='incUserAnswer(" + item.id + ", " + questionId + ")'>" + item.name + "</a>");
        });
    }).always(function () {
        unmask();
    });
}

function incUserAnswer(userId, questionId) {
    mask();

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/api/user/incCount",
        data: JSON.stringify({userId: userId, questionId: questionId}),
        cache: false,
        timeout: 600000,
        success: function () {
            unmask();
        },
        error: function (e) {
            unmask();
        }
    });

    window.location.href = '/index.html';
}

function getStatByUser() {
    mask();
    $.getJSON("/api/statistics/user", function (data) {
        if (!data) return;

        $.each(data, function (index, item) {
            if (!item) return;

            let row = $('<tr><th scope="row">' + (index + 1) + '</th</tr>');
            let nameCol = $('<td>' + item.name + '</td>');
            let countCol = $('<td>' + item.count + '</td>');
            let positionCol = $('<td>' + item.position + '</td>');
            row
                .append(nameCol)
                .append(countCol)
                .append(positionCol);

            $('#bodyTblUsers').append(row);
        });
    }).always(function () {
        unmask();
    });
}

function getStatByGroup() {
    mask();
    $.getJSON("/api/statistics/group", function (data) {
        if (!data) return;

        $.each(data, function (index, item) {
            if (!item) return;

            let row = $('<tr><th scope="row">' + (index + 1) + '</th</tr>');
            let nameCol = $('<td>' + item.name + '</td>');
            let countCol = $('<td>' + item.count + '</td>');
            let positionCol = $('<td>' + item.position + '</td>');
            row
                .append(nameCol)
                .append(countCol)
                .append(positionCol);

            $('#bodyTblGroups').append(row);
        });
    }).always(function () {
        unmask();
    });
}