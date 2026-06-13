function openModal(title, bodyHtml, footerHtml) {

    // Rimuove eventuali backdrop rimasti da modali precedenti
    $(".modal-backdrop").remove();
    $('body').removeClass('modal-open');

    $("#globalModalTitle").html(title);
    $("#globalModalBody").html(bodyHtml);
    $("#globalModalFooter").html(footerHtml);

    let modal = new bootstrap.Modal(document.getElementById("globalModal"));
    modal.show();
}
