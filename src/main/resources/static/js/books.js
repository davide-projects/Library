// GLOBAL FUNCTIONS -----------------------------------------------------

// LOAD BOOKS (GLOBAL)
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
                            <button class="btn btn-warning btn-sm me-2"
                                onclick="openEditModal(${book.id}, '${book.title}', '${book.author}', '${book.publisher}')">
                                Edit
                            </button>

                            <button class="btn btn-danger btn-sm"
                                onclick="openDeleteModal(${book.id})">
                                Delete
                            </button>
                        </td>
                    </tr>
                `);
            });
        }
    });
}

// OPEN EDIT MODAL
function openEditModal(id, title, author, publisher) {

    openModal(
        "Edit Book",
        `
            <input type="hidden" id="editId" value="${id}">

            <div class="mb-3">
                <label class="form-label">Title</label>
                <input class="form-control" id="editTitle" value="${title}">
            </div>

            <div class="mb-3">
                <label class="form-label">Author</label>
                <input class="form-control" id="editAuthor" value="${author}">
            </div>

            <div class="mb-3">
                <label class="form-label">Publisher</label>
                <input class="form-control" id="editPublisher" value="${publisher}">
            </div>
        `,
        `
            <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button class="btn btn-primary" onclick="saveBookChanges()">Save</button>
        `
    );
}

// SAVE EDIT
function saveBookChanges() {

    const id = $("#editId").val();

    const updatedBook = {
        title: $("#editTitle").val(),
        author: $("#editAuthor").val(),
        publisher: $("#editPublisher").val()
    };

    $.ajax({
        url: "/book/" + id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedBook),
        success: function () {
            loadBooks();
            bootstrap.Modal.getInstance(document.getElementById("globalModal")).hide();
        },
        error: function (xhr) {
            openModal(
                "Error",
                `<p class="text-center">${xhr.responseJSON?.message}</p>`,
                `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Close</button>`
            );
        }
    });
}

// OPEN DELETE MODAL
function openDeleteModal(id) {

    openModal(
        "Confirm deletion",
        `<p class="text-center">Are you sure you want to delete this book?</p>`,
        `
            <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button class="btn btn-danger" onclick="confirmDeleteBook(${id})">Delete</button>
        `
    );
}

// CONFIRM DELETE
function confirmDeleteBook(id) {

    $.ajax({
        url: "/book/" + id,
        method: "DELETE",
        success: function () {
            loadBooks();
            bootstrap.Modal.getInstance(document.getElementById("globalModal")).hide();
        },
        error: function (xhr) {
            openModal(
                "Error",
                `<p class="text-center">${xhr.responseJSON?.message}</p>`,
                `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Close</button>`
            );
        }
    });
}

// DOCUMENT READY -------------------------------------------------------

$(document).ready(function () {

    // ADD BOOK
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
                openModal(
                    "Error",
                    `<p class="text-center">${xhr.responseJSON?.message}</p>`,
                    `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Close</button>`
                );
            }
        });
    });

    loadBooks();
});
