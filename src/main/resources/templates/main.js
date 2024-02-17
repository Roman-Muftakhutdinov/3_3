// const url = 'http://localhost:8080/admin'
// const container = document.querySelector('tbody')
// let result = ''
//
// const editModal = $('#editModalLabel').ariaModal(document.getElementById('editModalLabel'))
// const formEdit = document.querySelector('form')
// const id = document.getElementById('edit-id')
// const username = document.getElementById('edit-username')
// const surname = document.getElementById('edit-surname')
// const age = document.getElementById('edit-age')
// const password = document.getElementById('edit-password')
// const roles = document.getElementById('edit-roles')
// const btnEdit = document.getElementById('btnEdit')
//
// let option = ''
//
// btnEdit.addEventListener('click',() =>  {
//     username.value = ''
//     surname.value = ''
//     age.value = ''
//     password.value = ''
//     roles.value = ''
//
//     editModal.show()
//     option = 'edit'
// })
//
// const mostrar = (editModal) =>{
//     editModal.forEach(user   => {
//         result +=
//             <tr>
//                 <td>${user.id}</td>
//                 <td>${user.username}</td>
//                 <td>${user.surname}</td>
//                 <td>${user.age}</td>
//                 <td>${user.password}</td>
//                 <td>${user.role}</td>
//                 <td class="text-center"><a class="btnEdit btn btn-info">Edit</a></td>
//                 <td class="text-center"><a class="btnDelete btn btn-danger">Delete</a></td>
//             </tr>
//
//     })
// container.innerHTML = result
//
// }
//
// fetch(url)
//     .then(response => response.json())
//     .then(data => mostrar(data))
//     .catch(error =>console.log(error))
//
