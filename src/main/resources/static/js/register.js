$(function () {
    $("#root-form").ajaxForm(function (data) {
            console.log(data);
            if (data == false) {
                alert("The user has already registered and is jumping to login..");
                window.location.href = "login";
            } else {
                alert("Successful registration, enter the home page..");
                localStorage.setItem("username", $('input[type="text"]')[0].value);
                window.location.href = "index";
            }
        }
    );
});
