let teamLeadType = 'TEAM_LEAD';
let successMessage = 'Успешно сохранено';

let users = [];
let groups = [];

function init() {
    $.getJSON("/api/user", function (data) {
        if (!data) return;

        if (data.isAdmin && $('#settingsBtn')[0]) {
            $('#settingsBtn').css('display', 'block');
        }
    });
    $('.loading').css('display', 'none');
}

function mask() {
    $('.loading').css('display', 'block');
}

function unmask() {
    $('.loading').css('display', 'none');
}

$.postJSON = function (url, data, callback, error) {
    return jQuery.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'type': 'POST',
        'url': url,
        'data': JSON.stringify(data),
        'dataType': 'json',
        'success': callback,
        'error': function () {
            window.alert('Ошибка сети');
        }
    });
};

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

function getUsersForCounts() {
    mask();
    $.getJSON("/api/user/withoutAdmin", function (data) {
        if (!data) return;

        users = [];
        $.each(data, function (index, item) {
            if (!item) return;

            users.push(item);
            $('#userEditSelector').append("<option value='" + item.id + "'>" + item.name + "</option>");
        });

        if (users.length) {
            $('#userCount').val(users[0].count);
        }

        $('#userEditSelector').change(function () {
            let userItem = _.find(users, {id: Number.parseInt($(this).val())});
            if (!userItem) return;

            $('#userCount').val(userItem.count);
        });
    }).always(function () {
        unmask();
    });
}

function incUserAnswer(userId, questionId) {
    mask();

    $.postJSON("/api/user/incCount", {userId: userId, questionId: questionId}, function () {
        unmask();
        window.location.href = '/index.html';
    });
}

function getStatByUser() {
    mask();
    $.getJSON("/api/statistics/user", function (data) {
        if (!data) return;

        $.each(data, function (index, item) {
            if (!item) return;

            let row = $('<tr><th scope="row">' + (index + 1) + '</th></tr>');
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

    function generateSubList(name, map) {
        let resultEl =
            '<td>' +
            '   <ul class="list-group bmd-list-group-sm">' +
            '                                <a class="list-group-item">' +
            '                                    <div class="bmd-list-group-col">';
        resultEl = resultEl + '<p class="list-group-item-heading">' + name + '</p>';
        if (map && map.length) {
            $.each(map, function (index, item) {
                if (!item) return;
                resultEl = resultEl + '<p class="list-group-item-text">' + item + '</p>';
            });
        }
        resultEl = resultEl +
            '                                    </div>' +
            '                                </a>' +
            '   </ul>' +
            '</td>';

        return $(resultEl);
    }

    $.getJSON("/api/statistics/group", function (data) {
        if (!data) return;

        $.each(data, function (index, item) {
            if (!item) return;

            let row = $('<tr><th scope="row" style="vertical-align: sub">' + (index + 1) + '</th></tr>');
            let nameCol = $('<td>' + item.name + '</td>');
            let countCol = $('<td>' + item.count + '</td>');
            let positionCol = $('<td>' + item.position + '</td>');

            if (item.users.length) {
                nameCol = generateSubList(item.name, _.map(item.users, 'name'));
                countCol = generateSubList(item.count, _.map(item.users, 'count'));
                positionCol = generateSubList(item.position, _.map(item.users, 'position'));
            }
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

function editUserCount() {
    let userCountElement = $('#userCount')[0];
    let userId = $('#userEditSelector option:selected').val();
    let userCount = $('#userCount').val();

    if (!userCountElement) return;

    $.postJSON('/api/user/edit/count', {id: userId, count: userCount}, function () {
        window.alert(successMessage);
    });
}

function getGroupsForCounts() {
    mask();

    function fillFields(group) {
        $('#groupAllCount').val(group.count);
        $('#groupCountDisable').val(group.personalCount);
        $('#groupPersonalDisable').val(group.count - group.personalCount);
        $('#groupCount').val(group.personalCount);
    }

    $.getJSON("/api/statistics/group", function (data) {
        if (!data) return;

        groups = [];
        $.each(data, function (index, item) {
            if (!item) return;

            groups.push(item);
            $('#groupEditSelector').append("<option value='" + item.id + "'>" + item.name + "</option>");
        });

        if (groups.length) {
            fillFields(groups[0]);
        }

        $('#groupEditSelector').change(function () {
            let groupItem = _.find(groups, {id: Number.parseInt($(this).val())});
            if (!groupItem) return;

            fillFields(groupItem);
        })
    }).always(function () {
        unmask();
    });
}

function editGroupCount() {
    let groupCountElement = $('#groupCount')[0];
    let groupId = $('#groupEditSelector option:selected').val();
    let groupCount = $('#groupCount').val();

    if (!groupCountElement) return;

    $.postJSON('/api/user/edit/group/count', {id: groupId, count: groupCount}, function () {
        window.alert(successMessage);
    });
}