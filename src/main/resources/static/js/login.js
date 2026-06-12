$(document).ready(function () {

    // Rimuove eventuali token vecchi
    localStorage.removeItem("auth");

    $("#loginForm").submit(function (e) {
        e.preventDefault();

        $("#errorMsg").addClass("d-none");

        const username = $("#username").val();
        const password = $("#password").val();

        if (!username || !password) {
            $("#errorMsg").text("Inserisci username e password").removeClass("d-none");
            return;
        }

        const encoded = btoa(username + ":" + password);

        $.ajax({
            url: "/members",
            method: "GET",
            headers: {
                "Authorization": "Basic " + encoded
            },
            success: function () {
                localStorage.setItem("auth", encoded);
                window.location.href = "/index.html";
            },
            error: function () {
                $("#errorMsg").text("Credenziali non valide").removeClass("d-none");
            }
        });
    });

});
