import axios from "axios";

export const loginUser = async ({username, password}) => {
    const apiUrl = process.env.API_URL;
    debugger;
    console.log(apiUrl);
    try{
        return await axios.post('http://localhost:8080/login', {
        //return await axios.post(apiUrl.toString+'/login', {
        //return await axios.post('https://full-stack-app-vrkr.onrender.com/login', {
            username,
            password,
        })
    } catch (error) {
        throw error;
    }
}