import axios from "axios";
import {useEffect, useState} from "react";

export default function OAuth() {

    const [username, setUsername] = useState<string | null | undefined>(undefined)

    function login() {
        const host =
            window.location.host === 'localhost:5173' ?
                'http://localhost:8080'
                :
                window.location.origin
        ;

        window.open(host + '/oauth2/authorization/github', '_self')
    }

    function logout() {
        const host =
            window.location.host === 'localhost:5173' ?
                'http://localhost:8080'
                :
                window.location.origin

        window.open(host + '/logout', '_self')
    }

    function loadUser() {
        axios.get("api/auth/me")
            .then(r => setUsername(r.data))
            .catch(e => {
                setUsername(null)
                console.error(e)
            })
    }

    useEffect(() => {
        loadUser()
    }, []);

    return (
        <>
            {username &&
                <>
                    <h2>Hallo {username}</h2>
                    <button onClick={logout}>Logout!</button>
                </>
            }
                {!username &&
                    <button onClick={login}>Login</button>
                }
        </>
    )
}