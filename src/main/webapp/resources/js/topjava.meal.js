var ctx;

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            //"pagingType": "full_numbers",
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    makeEditable(updateTable);
});

function resetFilter() {
    $('#filterForm').find(":input").val("");
    updateTable()
}

function updateTable() {
    $.get(ctx.ajaxUrl + "filter", $('#filterForm').serialize(), function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    });
}