import axios from "axios";

export const loginUser = async ({username, password}) => {
    try{
        //return await axios.post('http://localhost:8080/login', {
        return await axios.post('https://zvllf0kd-8080.usw3.devtunnels.ms/login', {
            username,
            password,
        })
    } catch (error) {
        throw error;
    }
}