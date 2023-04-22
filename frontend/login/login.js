$(document).ready(function () {
    console.log("ready!");
    $("#login").submit(function (event) {
        event.preventDefault();
        console.log(JSON.stringify(event))
        checkCredentials();
    })
});

function checkCredentials() {
    filepath = "file:///C:/Users/dodla/OneDrive/Desktop/CourseEnrollment/frontend"
    console.log("Submitted");
    console.log(JSON.stringify($("#login")));
    console.log($("#loginid").val())
    console.log($("#password").val())

    data = {
        "loginId": $("#loginid").val(),
        "password": $("#password").val(),
    }

    console.log(data)
    console.log(JSON.stringify(data))

    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "http://localhost:8080/user/check",
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            $('#invaliderror').css("visibility", "hidden")
            $(".errorborder").css({ "border-style": "solid", "border-width": "2px", "border-color": "green" })
            console.log("Login connection successful");
            console.log(JSON.stringify(data));
            console.log(window.location);
            localStorage.setItem("userid", data.userId);
            localStorage.setItem("firstname", data.firstName);
            localStorage.setItem("lastname", data.lastName);
            if (data.loginId == "admin") {
                console.log(filepath + "/admin/admin.html");
                window.location.href = filepath + "/admin/admin.html";
            }
            else {
                console.log(filepath + "/student/student.html");
                window.location.href = filepath + "/student/student.html";
            }
        },
        error: function (data) {
            console.log("Invalid Login Credentials");
            // $("#invaliderror").show()
            $('#invaliderror').css("visibility", "visible")
            $(".errorborder").css({ "border-style": "solid", "border-width": "2px", "border-color": "red" })
        }
    })

    return false;
}

