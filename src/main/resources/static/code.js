const url = '/api/admin/'
const deleteRoles = document.getElementById('delete-roles')
const container = document.querySelector('#tbodyAdmin')
let result = ''
const containerNew = document.querySelectorAll('.select')
let resultNew = ''
const formNewUserAdd = document.getElementById('addNewUserForm')


const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}
async function getAllUsers() {
    const response = await fetch(url)
    let data = await response.json()
    fillTableUsers(data)
}
function fillTableUsers (users) {
    users.forEach(user => {
        let roles =[]
        for (let role of user.roles) {
                if(role.name) {
                    roles.push(role.name.toString().replace("ROLE_", ""))
                }
        }
        result +=   `
        <tr id="${user.id}">
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.surname}</td>
            <td>${user.age}</td>
            <td>${user.password}</td>
            <td>${roles}</td>       
            <td class="text-center"><a class="btnEdit btn btn-info" data-toggle="modal">Edit</a></td>
            <td class="text-center"><a class="btnDelete btn btn-danger">Delete</a></td>
        </tr>
    `
    })
    container.innerHTML = result
}
window.addEventListener('DOMContentLoaded', getAllUsers);

const getAllRoles = (roles) => {
    roles.forEach(role => {
        resultNew += `
            <option value="${role.id}">
                ${role.name.replace("ROLE_","")}
            </option>         
        `
    })
    containerNew.forEach(c => c.innerHTML=resultNew)
}
async function getAllRolesFinal() {
    const response = await fetch(url+'getRoles')
    const data = await response.json()
    getAllRoles(data)
}

window.addEventListener('DOMContentLoaded',getAllRolesFinal)

async function addNewUser(event)  {
    event.preventDefault()
    const username = document.getElementById('new-username')
    const surname = document.getElementById('new-surname')
    const age = document.getElementById('new-age')
    const password = document.getElementById('new-password')
    const rolesSelect = document.getElementById('new-roles')
    const roles =[]

    for (let i = 0; i < rolesSelect.selectedOptions.length; i++) {
        roles[i] = ({
            id: rolesSelect.selectedOptions[i].value,
            name: 'ROLE_' + rolesSelect.selectedOptions[i].innerText
        })
    }
    const response = await fetch(url, {
        method:'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            username: username.value,
            surname: surname.value,
            age: age.value,
            password: password.value,
            roles: roles
        })
    });
    const data = await response.json();

    let arr =[]
    arr.push(data)
    fillTableUsers(arr)

    document.getElementById('adminPanel').click()
    formNewUserAdd.reset()
}

formNewUserAdd.addEventListener('submit',addNewUser);

//заполнение модального окна
const fillEditModal = ({id, username, surname, age, password, roles}) => {
    document.getElementById('edit-id').value = `${id}`
    document.getElementById('edit-username').value = `${username}`
    document.getElementById('edit-surname').value = `${surname}`
    document.getElementById('edit-age').value = `${age}`
    document.getElementById('edit-password').value = `${password}`
}
//получение юзера гет запросом и вызов функции заполнения модального окна
async function editModalUser (id) {
    let response = await fetch(url+id)
    const data = await response.json()
    fillEditModal(data)
}
//вызов модального окна кнопкой edit
on (document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode
    const id = row.firstElementChild.innerHTML
    $('#editModal').modal('show')
    editModalUser(id)
})

async function editUser(event) {
    event.preventDefault()
    let id = document.getElementById('edit-id')
    let username = document.getElementById('edit-username')
    let surname = document.getElementById('edit-surname')
    let age = document.getElementById('edit-age')
    let password = document.getElementById('edit-password')
    let editRolesSelect = document.getElementById('edit-roles')
    let roles =[]

    for (let i = 0; i < editRolesSelect.selectedOptions.length; i++) {
        roles[i] = ({
            id: editRolesSelect.selectedOptions[i].value,
            name: 'ROLE_' + editRolesSelect.selectedOptions[i].innerText
        })
    }
    const response = await fetch(url+id.value, {
        method:'PATCH',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({
            username: username.value,
            surname: surname.value,
            age: age.value,
            password: password.value,
            roles: roles
        })
    });
    const data = await response.json();

    let arr =[]
    for (let role of data.roles) {
        if(role.name) {
            arr.push(role.name.toString().replace("ROLE_", ""))
        }
    }

    const row = document.getElementById(id.value)
    row.children[0].textContent = data.id
    row.children[1].textContent = data.username
    row.children[2].textContent = data.surname
    row.children[3].textContent = data.age
    row.children[4].textContent = data.password
    row.children[5].textContent = arr

    document.getElementById('btnEditClose').click()
    document.getElementById('adminPanel').click()
}

document.getElementById('formEdit')
    .addEventListener('submit',editUser)

//заполняем модальное окно fillDeleteModal начало
function fillDeleteModal({id, username, surname, age, password, roles}) {
    document.getElementById('delete-id').value = `${id}`
    document.getElementById('delete-username').value = `${username}`
    document.getElementById('delete-surname').value = `${surname}`
    document.getElementById('delete-age').value = `${age}`
    document.getElementById('delete-password').value = `${password}`

        //обнуляем select
    while (deleteRoles.firstChild) {
        deleteRoles.removeChild(deleteRoles.firstChild)
    }
        //заполняем select ролями конкретного пользователя
    for(let i = 0; i< roles.length; i++) {
        let option = document.createElement('option')
        option.text = roles[i].name.replace('ROLE_', '')
        deleteRoles.appendChild(option)
    }
}
//заполнение модального окна fillDeleteModal конец
async function deleteModalUser(id) {
    let response = await fetch(url+id)
    const data = await response.json()
    fillDeleteModal(data)
}

//по нажатию кнопки удаление происходит вызов модального окна и заполнение полей
on (document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode
    const id = row.firstElementChild.innerHTML
    $('#deleteModal').modal('show')
    deleteModalUser(id)
})

async  function deleteUser(event) {
    event.preventDefault()
    let id = document.getElementById('delete-id').value
    const res = await  fetch(url+id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=utf-8',
        }
    });

    const data = await res.text();
    document.getElementById(id).remove()
    document.getElementById('btnDeleteClose').click()
    document.getElementById('adminPanel').click()
}

//по клику происходит удаление пользователя
document.getElementById('formDelete')
    .addEventListener('submit',deleteUser)
