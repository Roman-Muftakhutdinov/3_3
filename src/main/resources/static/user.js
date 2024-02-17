const urlUser = '/api/user'
const container = document.querySelector('#tbodyUser')
let result = ''

function fillTableUser (user) {

        let roles =[]
        for (let role of user.roles) {
            if(role.name) {
                roles.push(role.name.toString().replace("ROLE_", ""))
            }
        }
        result =   `
        <tr id="${user.id}">
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.password}</td>
            <td>${roles}</td>       
         </tr>
    `
    container.innerHTML = result
}
window.addEventListener('DOMContentLoaded', getPageUser);

async function getPageUser() {
    const response = await fetch(urlUser)
    let data = await response.json()
    fillTableUser(data)
    console.log(data)
}

// document.getElementById('userLeftPanel')
//     .addEventListener('click', getPageUser)

///////////////
// on (document, 'click', 'userLeftPanel', e => {
//     getPageUser()
// })
////////////