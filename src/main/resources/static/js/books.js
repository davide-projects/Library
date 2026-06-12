$(document).ready(function () {

    function loadBooks() {
        $.ajax({
            url: "/book",
            method: "GET",
            success: function (data) {
                let table = $("#booksTable tbody");
                table.empty();

                data.forEach(book => {
                    table.append(`
                        <tr>
                            <td>${book.id}</td>
                            <td>${book.title}</td>
                            <td>${book.author}</td>
                            <td>${book.publisher}</td>
                            <td>
                                <button class="btn btn-danger btn-sm" onclick="deleteBook(${book.id})">
                                    Elimina
                                </button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

    $("#addBookForm").submit(function (e) {
        e.preventDefault();

        const book = {
            title: $("#title").val(),
            author: $("#author").val(),
            publisher: $("#publisher").val()
        };

        $.ajax({
            url: "/book",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(book),
            success: function () {
                $("#addBookForm")[0].reset();
                loadBooks();
            },
            error: function (xhr) {
                alert("Errore: " + xhr.responseJSON?.message);
            }
        });
    });

    loadBooks();
});

function deleteBook(id) {
    if (!confirm("Sei sicuro di voler eliminare questo libro?")) return;

    $.ajax({
        url: "/book/" + id,
        method: "DELETE",
        success: function () {
            // ricarica la tabella dopo eliminazione
            location.reload();
        },
        error: function (xhr) {
            alert("Errore: " + xhr.responseJSON?.message);
        }
    });
}