import { useEffect, useState } from "react";
import { UserModalForm } from "../components/UserModalForm";
import { UsersList } from "../components/UsersList";
import { useUsers } from "../hooks/useUsers";
import axios from 'axios';
import Swal from "sweetalert2";

export const UsersPage = () => {
    const [users, setUsers] = useState([]);

    const fetchUsers = async () => {
        try {
            const response = await axios.get("http://localhost:8080/users");
            setUsers(response.data);
        } catch (error) {
            Swal.fire("Error", "No se pudieron cargar los usuarios", "error");
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);


    const {
        //users,
        userSelected,
        initialUserForm,
        visibleForm,
        handlerAddUser,
        handlerRemoveUser,
        handlerUserSelectedForm,
        handlerOpenForm,
        handlerCloseForm,
    } = useUsers();

    return (
        <>

            {!visibleForm ||
                <UserModalForm
                    userSelected={userSelected}
                    initialUserForm={initialUserForm}
                    handlerAddUser={handlerAddUser}
                    handlerCloseForm={handlerCloseForm}
                />}
            <div className="container my-4">
                <h2>Users App</h2>
                <div className="row">
                    <div className="col">
                        {visibleForm || <button
                            className="btn btn-primary my-2"
                            onClick={handlerOpenForm}>
                            Nuevo Usuario
                        </button>}

                        {
                            users.length === 0
                                ? <div className="alert alert-warning">No hay usuarios en el sistema!</div>
                                : <UsersList
                                    handlerUserSelectedForm={handlerUserSelectedForm}
                                    handlerRemoveUser={handlerRemoveUser}
                                    users={users}
                                />
                        }
                    </div>
                </div>
            </div>
        </>
    );
}