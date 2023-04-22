enrolledCourseList = []

$(document).ready(function () {
    console.log("enroll")
    getAvailableCourses()
});

function getAvailableCourses() {
    id = localStorage.getItem("userid")
    firstname = localStorage.getItem("firstname").toUpperCase();
    lastname = localStorage.getItem("lastname").toUpperCase();
    $("#dashboardName").text(firstname + " " + lastname + " Enroll")
    console.log("Get Available Courses")
    $.ajax({
        type: "get",
        url: "http://localhost:8080/course/all/" + id,
        success: function (courseList) {
            console.log(courseList);
            $("table.courselist tbody").find("tr").remove()
            console.log(JSON.stringify(courseList))
            for (course in courseList) {
                console.log(courseList[course].courseName)
                id = courseList[course].courseId
                var courseHtml = '<tr>' +
                    '<td>' +
                    '<p>' + courseList[course].courseName + '</p>' +
                    '</td>' +
                    '<td>' +
                    '<p>' + courseList[course].professorName + '</p>' +
                    '</td>' +
                    '<td>' +
                    '<input type="checkbox" id="' + id + '" value="' + id + '" onClick="addCourses(event)"/>' +
                    '</td>' +
                    '</tr>'
                console.log(courseHtml)
                $("table.courselist tbody").append(courseHtml)
            }
        }
    })
}

function addCourses(event) {
    var value = (event.target.id)
    if ($("#" + event.target.id).prop("checked") == true) {
        console.log("checked")
        enrolledCourseList.push(parseInt(value))
        console.log(enrolledCourseList)
    }
    else {
        console.log("unchecked")
        enrolledCourseList.splice(enrolledCourseList.indexOf(parseInt(value)), 1)
        console.log(enrolledCourseList)
    }

    if (enrolledCourseList.length == 0) {
        $("#enrollBtn").prop("disabled", true)
    }
    else {
        $("#enrollBtn").prop("disabled", false)
    }
}

function enrollCourses() {
    console.log("enroll courses function")
    console.log(JSON.stringify(enrolledCourseList))
    id = localStorage.getItem("userid");
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "http://localhost:8080/enroll/user/" + id + "/course",
        data: JSON.stringify(enrolledCourseList),
        dataType: "json",
        success: function (data) {
            console.log("success call " + data)
            getAvailableCourses()
        }
    })
}

function loadDashboardPage() {
    filepath = "file:///C:/Users/dodla/OneDrive/Desktop/CourseEnrollment/frontend"
    window.location.href = filepath + "/student/student.html";
}