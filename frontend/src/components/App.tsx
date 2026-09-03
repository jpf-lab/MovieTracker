import { Routes, Route } from 'react-router-dom'
import Home from './Home'
import SavedItems from './SavedItems'
import '../css/App.css'

function App() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/saved" element={<SavedItems />} />
        </Routes>
    )
}

export default App