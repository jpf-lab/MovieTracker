import { Routes, Route } from 'react-router-dom'
import SavedItems from './SavedItems'
import '../css/App.css'

function App() {
  return (
      <Routes>
        <Route path="/" element={<div>Startseite (kommt noch)</div>} />
        <Route path="/saved" element={<SavedItems />} />
      </Routes>
  )
}

export default App