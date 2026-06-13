// -------------------------------------------------------------
// GLOBAL FUNCTIONS
// -------------------------------------------------------------

// LOAD LOANS
function loadLoans() {
    $.ajax({
        url: "/loan",
        method: "GET",
        success: function (data) {
            let table = $("#loansTable tbody");
            table.empty();

            data.forEach(loan => {
                table.append(`
                    <tr>
                        <td>${loan.id}</td>
                        <td>${loan.member.firstName} ${loan.member.lastName}</td>
                        <td>${loan.book.title}</td>
                        <td>${loan.loanDate}</td>
                        <td>${loan.returnDate ?? "-"}</td>
                        <td>
                            ${
                    loan.returnDate
                        ? `<span class="text-muted">Restituito</span>`
                        : `<button class="btn btn-success btn-sm"
                                            onclick="openReturnLoanModal(${loan.id})">
                                            Restituisci
                                       </button>`
                }
                        </td>
                    </tr>
                `);
            });
        },
        error: function (xhr) {
            console.log("ERRORE GET /loan:", xhr.responseText);
        }
    });
}

// OPEN RETURN MODAL
function openReturnLoanModal(id) {
    openModal(
        "Conferma restituzione",
        `<p class="text-center">Vuoi segnare questo prestito come restituito?</p>`,
        `
            <button class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
            <button class="btn btn-success" onclick="confirmReturnLoan(${id})">Conferma</button>
        `
    );
}

// CONFIRM RETURN
function confirmReturnLoan(id) {
    $.ajax({
        url: "/loan/" + id + "/return",
        method: "PATCH",
        success: function () {
            loadLoans();
            bootstrap.Modal.getInstance(document.getElementById("globalModal")).hide();
            $(".modal-backdrop").remove();
            $("body").removeClass("modal-open");
        },
        error: function (xhr) {
            console.log("ERRORE PATCH /loan/" + id + "/return:", xhr.responseText);
            openModal(
                "Errore",
                `<p class="text-center">${xhr.responseJSON?.message}</p>`,
                `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Chiudi</button>`
            );
        }
    });
}

// -------------------------------------------------------------
// DOCUMENT READY
// -------------------------------------------------------------

$(document).ready(function () {

    // ADD LOAN
    $("#addLoanForm").submit(function (e) {
        e.preventDefault();

        if (!$("#memberId").val() || !$("#bookId").val()) {
            openModal(
                "Errore",
                `<p class="text-center">Inserisci ID membro e ID libro.</p>`,
                `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Chiudi</button>`
            );
            return;
        }

        const newLoan = {
            bookIds: [ parseInt($("#bookId").val()) ],
            memberId: parseInt($("#memberId").val())
        };

        $.ajax({
            url: "/loan",
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(newLoan),
            success: function () {
                $("#addLoanForm")[0].reset();
                loadLoans();
            },
            error: function (xhr) {
                console.log("ERRORE POST /loan:", xhr.responseText);
                openModal(
                    "Errore",
                    `<p class="text-center">${xhr.responseJSON?.message}</p>`,
                    `<button class="btn btn-secondary w-100" data-bs-dismiss="modal">Chiudi</button>`
                );
            }
        });
    });

    loadLoans();
});
