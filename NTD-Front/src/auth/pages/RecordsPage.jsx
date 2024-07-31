export const Records = () => {

    const records = [{
        "id": "1",
        "user_id" : "01",
        "amount": "100",
        "date" : "07-25-2024",
        "operation_id": "1",
        "response" : "101",
        "balance" : "102"
    }]

    return(
        <>
            <div className="container">
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
                        {records.map((record) => (
                            <tr key={record.id}>
                                <th scope="row">1</th>
                                <td>{record.user_id}</td>
                                <td>{record.amount}</td>
                                <td>{record.date}</td>
                                <td>{record.operation_id}</td>
                                <td>{record.response}</td>
                                <td>{record.balance}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </>
    );
}