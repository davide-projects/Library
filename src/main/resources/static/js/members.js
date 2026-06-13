// -------------------------------------------------------------
// GLOBAL FUNCTIONS
// -------------------------------------------------------------

// LOAD MEMBERS (GLOBAL)
function loadMembers() {
    $.ajax({
        url: "/members",
        method: "GET",
        success: function (data) {
            let table = $("#membersTable tbody");
            table.empty();

            data.forEach(member => {
                table.append(`
                    <tr>
                        <td>${member.id}</td>
                        <td>${member.firstName}</td>
                        <td>${member.lastName}</td>
                        <td>${member.city}</td>
                        <td>${member.phone}</td>
                        <td>
                            <button class="btn btn-warning btn-sm me-2"
                                onclick="openEditMemberModal(${member.id}, '${member.firstName}', '${member.lastName}', '${member.city}', '${member.phone}')">
                                Edit
                            </button>

                            <button class="btn btn-danger btn-sm"
                                onclick="openDeleteMemberModal(${member.id})">
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
function openEditMemberModal(id, firstName, lastName, city, phone) {

    openModal(
        "Edit Member",
        `
            <input type="hidden" id="editMemberId" value="${id}">

            <div class="mb-3">
                <label class="form-label">Nome</label>
                <input class="form-control" id="editFirstName" value="${firstName}">
            </div>

            <div class="mb-3">
                <label class="form-label">Cognome</label>
                <input class="form-control" id="editLastName" value="${lastName}">
            </div>

            <div class="mb-3">
                <label class="form-label">Città</label>
                <input class="form-control" id="editCity" value="${city}">
            </div>

            <div class="mb-3">
                <label class="form-label">Telefono</label>
                <input class="form-control" id="editPhone" value="${phone}">
            </div>
        `,
        `
            <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button class="btn btn-primary" onclick="saveMemberChanges()">Save</button>
        `
    );
}

// SAVE EDIT
function saveMemberChanges() {

    const id = $("#editMemberId").val();

    const updatedMember = {
        firstName: $("#editFirstName").val(),
        lastName: $("#editLastName").val(),
        city: $("#editCity").val(),
        phone: $("#editPhone").val()
    };

    $.ajax({
        url: "/members/" + id,
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updatedMember),
        success: function () {
            loadMembers();
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
function openDeleteMemberModal(id) {

    openModal(
        "Confirm deletion",
        `<p class="text-center">Are you sure you want to delete this member?</p>`,
        `
            <button class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
            <button class="btn btn-danger" onclick="confirmDeleteMember(${id})">Delete</button>
        `
    );
}

// CONFIRM DELETE
function confirmDeleteMember(id) {

    $.ajax({
        url: "/members/" + id,
        method: "DELETE",
        success: function () {
            loadMembers();
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

// -------------------------------------------------------------
// DOCUMENT READY
// -------------------------------------------------------------

$(document).ready(function () {

    // ADD MEMBER
    $("#addMemberForm").submit(function (e) {
        e.preventDefault();

        const newMember = {
            firstName: $("#firstName").val(),
            lastName: $("#lastName").val(),
            city: $("#city").val(),
            phone: $("#phone").val()
        };

        $.ajax({
            url: "/members",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(newMember),
            success: function () {
                $("#addMemberForm")[0].reset();
                loadMembers();
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

    loadMembers();
});
