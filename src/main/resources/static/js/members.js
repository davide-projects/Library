$(document).ready(function () {

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
                                <button class="btn btn-danger btn-sm" onclick="deleteMember(${member.id})">
                                    Elimina
                                </button>
                            </td>
                        </tr>
                    `);
                });
            }
        });
    }

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
                alert("Errore: " + xhr.responseJSON?.message);
            }
        });
    });

    loadMembers();
});

function deleteMember(id) {
    if (!confirm("Sei sicuro di voler eliminare questo membro?")) return;

    $.ajax({
        url: "/members/" + id,
        method: "DELETE",
        success: function () {
            location.reload();
        },
        error: function (xhr) {
            alert("Errore: " + xhr.responseJSON?.message);
        }
    });
}