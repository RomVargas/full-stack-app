import { useEffect, useState } from "react";
import axios from "axios";

export const Records = () => {

    const apiUrl = process.env.API_URL;

    const [records, setRecords] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [recordsPerPage] = useState(15);
    const [filter, setFilter] = useState('');

    const fetchRecords = async () => {
    try {
        const response = await axios.get(`${apiUrl}/users`);
        console.log(response.data);
        setRecords(response.data);
    } catch (error) {
        console.error(error);
        throw error;
    }
};
useEffect(() => {
    fetchRecords();
}, []);

// Filtra los registros basados en el valor del filtro
    const filteredRecords = records.filter(record =>
        Object.values(record).some(value =>
            value.toString().toLowerCase().includes(filter.toLowerCase())
        )
    );

// Calcula los índices de los registros a mostrar en la página actual
const indexOfLastRecord = currentPage * recordsPerPage;
const indexOfFirstRecord = indexOfLastRecord - recordsPerPage;
const currentRecords = filteredRecords.slice(indexOfFirstRecord, indexOfLastRecord);

// Cambia la página actual
const paginate = (pageNumber) => setCurrentPage(pageNumber);

// Crea un array con el número de páginas
const pageNumbers = [];
for (let i = 1; i <= Math.ceil(filteredRecords.length / recordsPerPage); i++) {
    pageNumbers.push(i);
}

    return(
        <>
            <div className="container">
                {/* Campo de filtro */}
                <div className="mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Filter Records"
                        value={filter}
                        onChange={(e) => setFilter(e.target.value)}
                    />
                </div>
                <table className="table">
                    <thead>
                        <tr>
                        <th scope="col">#id</th>
                        <th scope="col">user_id</th>
                        <th scope="col">amount</th>
                        <th scope="col">date</th>
                        <th scope="col">operation_id</th>
                        <th scope="col">response</th>
                        <th scope="col">balance</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentRecords.map((record) => (
                            <tr key={record.id}>
                                <th scope="row">{record.id}</th>
                                <td>{record.user.id}</td>
                                <td>{record.amount}</td>
                                <td>{record.date}</td>
                                <td>{record.operation.id}</td>
                                <td>{record.response}</td>
                                <td>{record.userBalance}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                {/* Paginación */}
                <nav>
                    <ul className="pagination">
                        {pageNumbers.map((number) => (
                            <li key={number} className="page-item">
                                <button
                                    onClick={() => paginate(number)}
                                    className="page-link"
                                >
                                    {number}
                                </button>
                            </li>
                        ))}
                    </ul>
                </nav>
            </div>
        </>
    );
}