import { Routes, Route, Navigate, useNavigate } from "react-router-dom";
import { UsersPage } from '../pages/UsersPage';
import { Navbar } from "../components/layout/Navbar";
import { Operation } from "../auth/pages/OperationPage";
import { Records } from "../auth/pages/RecordsPage";

export const UserRoutes = ({ login, handlerLogout }) => {

    const navigate = useNavigate();

    const nav_operation = () => {
    navigate('/operation');
    };

    const records = () =>{
        navigate('/records')
    }

    const records_list = [{
        "id": "1",
        "user_id" : "01",
        "amount": "100",
        "date" : "07-25-2024",
        "operation_id": "1",
        "response" : "101",
        "balance" : "102"
    }]

    return (
        <>
            <Navbar login={ login } handlerLogout={handlerLogout} nav={nav_operation} records={records}/>
            <Routes>
                
                <Route path="users" element={<UsersPage></UsersPage> }></Route>
                <Route path="/" element={ <Navigate to="/users" />} ></Route>
                <Route path="operation" element={<Operation></Operation>}></Route>
                <Route path="records" element={<Records data={records_list}></Records>}></Route>
            </Routes>
        </>
    );
}