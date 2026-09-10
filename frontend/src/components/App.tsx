import {Routes, Route, NavLink} from 'react-router-dom'
import Home from './Home'
import SavedItems from './SavedItems'
import '../css/App.css'

function App() {
    const linkClass = (isActive: boolean) =>
        `px-4 py-2 text-sm font-medium rounded-lg transition-colors ${
            isActive ? "bg-cyan-400 text-slate-950" : "text-slate-400 hover:text-slate-100"
        }`;

    return (
        <>
            <div className="bg-slate-950 border-b border-slate-800">
                <div className="max-w-5xl mx-auto px-6 py-4 flex items-center justify-between">
                    <span className="text-slate-100 text-xl font-medium">MovieTracker</span>
                    <nav className="flex gap-2">
                        <NavLink to="/" end className={({isActive}) => linkClass(isActive)}>Favoriten</NavLink>
                        <NavLink to="/search" className={({isActive}) => linkClass(isActive)}>Suche</NavLink>
                    </nav>
                </div>
            </div>
            <Routes>
                <Route path="/" element={<SavedItems/>}/>
                <Route path="/search" element={<Home/>}/>
            </Routes>
        </>
    )
}

export default App