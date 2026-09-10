import {Routes, Route} from 'react-router-dom'
import Home from './Home'
import SavedItems from './SavedItems'
import '../css/App.css'
import axios from "axios";
import {useEffect} from "react";

// import OAuth from "./OAuth.tsx";

function App() {

    const loadUser = () => {
        axios.get('/api/auth/me')
            .then(response => {
                console.log(response.data)
            })
    }

    useEffect(() => {
        loadUser();
    }, []);

    return (
        <>
            {/*<OAuth/>*/}
            <Routes>
                <Route path="/" element={<Home/>}/>
                <Route path="/saved" element={<SavedItems/>}/>
            </Routes>
        </>
    )
}

export default App