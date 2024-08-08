import React, { useState } from 'react';

export const Operation = () => {
    // Estado para el valor del campo de entrada y el valor seleccionado del desplegable
    const [inputValue, setInputValue] = useState('');
    const [operationType, setOperationType] = useState('addition');

    // Manejador del envío del formulario
    const handleSubmit = (event) => {
        event.preventDefault();
        // Aquí puedes procesar el formulario o enviar los datos al servidor
        console.log('Input Value:', inputValue);
        console.log('Selected Operation:', operationType);
    };

    return (
        <>
            <div className="container">
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="inputValue" className="form-label">Input Value</label>
                        <input
                            type="text"
                            className="form-control"
                            id="inputValue"
                            value={inputValue}
                            onChange={(e) => setInputValue(e.target.value)}
                            required
                        />
                    </div>

                    <div className="mb-3">
                        <label htmlFor="operationType" className="form-label">Operation Type</label>
                        <select
                            id="operationType"
                            className="form-select"
                            value={operationType}
                            onChange={(e) => setOperationType(e.target.value)}
                        >
                            <option value="addition">Addition</option>
                            <option value="subtraction">Subtraction</option>
                            <option value="multiplication">Multiplication</option>
                            <option value="division">Division</option>
                            <option value="square_root">Square Root</option>
                            <option value="random_string">Random String</option>
                        </select>
                    </div>

                    <button type="submit" className="btn btn-primary">Submit</button>
                </form>
            </div>
        </>
    );
};