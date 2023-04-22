$(document).ready(function () {
    console.log("ready!");
    getCourses();
});

function getCourses() {
    id = localStorage.getItem("userid");
    firstname = localStorage.getItem("firstname").toUpperCase();
    lastname = localStorage.getItem("lastname").toUpperCase();
    $("#dashboardName").text(firstname + " " + lastname + " Dashboard")
    $.ajax({
        type: "get",
        url: "http://localhost:8080/user/" + id,
        success: function (courseList) {
            console.log(courseList);
            $("table.courselist tbody").find("tr").remove()
            console.log(JSON.stringify(courseList))
            for (course in courseList) {
                console.log(courseList[course].courseName)
                id = courseList[course].courseId
                var courseHtml = '<tr>' +
                    '<form method="POST">' +
                    '<td>' +
                    '<p>' + courseList[course].courseName + '</p>' +
                    '</td>' +
                    '<td>' +
                    '<p>' + courseList[course].professorName + '</p>' +
                    '</td>' +
                    '<td>' +
                    '<p>Enrolled</p>' +
                    '</td>' +
                    '<td>' +
                    '</form>' +
                    '</tr>'
                console.log(courseHtml)
                $("table.courselist tbody").append(courseHtml)
            }
        }
    })
}

function loadEnrollPage() {
    filepath = "file:///C:/Users/dodla/OneDrive/Desktop/CourseEnrollment/frontend"
    window.location.href = filepath + "/student/enroll.html";
}

