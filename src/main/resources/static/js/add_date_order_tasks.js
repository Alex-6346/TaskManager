function stringToDate(str){
    date = str.split("-");
    return new Date(date[2], date[1]-1, date[0])
}

function dateToStr(date){
    year = date.getFullYear()
    month = date.getMonth() + 1
    day = date.getDate()
    return day + '-' + month + '-' + year
}


// function getDatesListAndMap(data){
//     dateTaskMap = new Map()
//     dates = []
//     data.forEach(function (task) {
//             var date = dateToStr(stringToDate(task.date))
//             if (dateTaskMap.get(date) === undefined) {
//                 dates.push(stringToDate(date))
//                 dateTaskMap.set(date, [task])
//             } else {
//                 let dateTasks = dateTaskMap.get(date)
//                 dateTasks.push(task)
//                 dateTaskMap.set(date, dateTasks)
//             }
//         }
//     )
//     dates.sort((date1, date2) => date1 - date2);
//     return [dates, dateTaskMap]
// }

function addOverdueTasksToPage(data, showTasksFun){
    if(data.length === 0){
        return
    }
    $('#tasksContainer').append(`<h3> Overdue:  </h3>`)
    $('#tasksContainer').append(`<ul id="overdue"> </ul>`)
    const [dates, dateTaskMap] = getDatesListAndMap(data)
    dates.forEach(function (dateObj){
        addOverdueDateTasks(dateObj, dateTaskMap, showTasksFun)
    })
}


function addTasksToPage(data, showTasksFun) {
    const [dates, dateTaskMap] = getDatesListAndMap(data)
    dates.forEach(function (dateObj){
        addDateWithTasks(dateObj, dateTaskMap, showTasksFun)
    })
}

function addOverdueDateTasks(dateObj, dateTaskMap, showTasksFun){
    date = dateToStr(dateObj)
    dateTaskMap.get(date).forEach(function (task) {
        var badge = '          <span  style="font-size:14px;" class="badge bg-danger">' + date + '</span>'
        // '<div class="col-auto">' +
        // '             <span  style="font-size:14xpx;" class="badge bg-danger p-1">' + date + '</span>' +
        // '        </div>'

        addTaskToView(task, 'overdue',showTasksFun, badge)
    })
}

function addDateWithTasks(dateObj, dateTaskMape, showTasksFun){
    date = dateToStr(dateObj)
    $('#tasksContainer').append(`<h3> ${date}    </h3>`)
    $('#tasksContainer').append(`<ul id="${date}"> </ul>`)
    console.log(date)
    dateTaskMap.get(date).forEach(function (task) {
        console.log(task)
        addTaskToView(task, date, showTasksFun)
    })
}

function getDatesListAndMap(data){
    dateTaskMap = new Map()
    dates = []
    data.forEach(function (task) {
            var date = dateToStr(stringToDate(task.date))
            if (dateTaskMap.get(date) === undefined) {
                dates.push(stringToDate(date))
                dateTaskMap.set(date, [task])
            } else {
                let dateTasks = dateTaskMap.get(date)
                dateTasks.push(task)
                dateTaskMap.set(date, dateTasks)
            }
        }
    )
    dates.sort((date1, date2) => date1 - date2);
    return [dates, dateTaskMap]
}