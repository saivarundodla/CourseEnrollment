$(document).ready(function () {
    console.log("ready!");
    getCourses();
});

function getCourses() {
    $.ajax({
        type: "get",
        url: "http://localhost:8080/course/all",
        success: function (data) {
            console.log(data);
            loadCourses(data)
        }
    })
}

function loadCourses(courseList) {
    $("table.courselist tbody").find("tr").remove()
    console.log(JSON.stringify(courseList))
    for (course in courseList) {
        console.log(courseList[course].courseName)
        id = courseList[course].courseId
        var courseHtml = '<tr>' +
            '<td>' +
            '<input type="text" id="coursename' + id + '"  class="coursename course' + id + '" disabled value="' + courseList[course].courseName + '"></input>' +
            '</td>' +
            '<td>' +
            '<input type="text" id="professorname' + id + '" class="professorname course' + id + '" disabled value="' + courseList[course].professorName + '"></input>' +
            '</td>' +
            '<td>' +
            '<input type="number" id="capacity' + id + '" class="capacity course' + id + '" disabled value="' + courseList[course].capacity + '"></input>' +
            '</td>' +
            '<td>' +
            '<button class="btn" id="edit' + id + '" onClick="updateCourse(event)">Edit</input>' +
            '</td>' +
            '<td>' +
            '<input type="submit" class="btn" id="submit' + id + '" disabled onClick="submitCourse(event)"></input>' +
            '</td>' +
            '</tr>'
        console.log(courseHtml)
        $("table.courselist tbody").append(courseHtml)
    }
}

function updateCourse(event) {
    console.log(event.target.id.substring(4))
    id = event.target.id.substring(4)
    $(".course" + id).prop("disabled", false);
    $("#submit" + id).prop("disabled", false);
}

function submitCourse(event) {
    console.log(event.target.id);
    id = event.target.id.substring(6);
    data = {
        "courseId": id,
        "courseName": $("#coursename" + id).val(),
        "professorName": $("#professorname" + id).val(),
        "capacity": $("#capacity" + id).val()
    }
    console.log(JSON.stringify(data));
    $.ajax({
        type: "put",
        contentType: "application/json",
        url: "http://localhost:8080/course/" + id,
        data: JSON.stringify(data),
        dataType: "json",
        success: function (data) {
            console.log(data);
        }
    })
    $("#submit" + id).prop("disabled", true);
}
