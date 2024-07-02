import './App.css';
import Menu from './components/Menu/Menu.js';
import Container from './components/Container/Container.js';
import { BrowserRouter, Routes,Route } from 'react-router-dom';
import Clients from './components/crud-app-clients/MainList.js'



function App() {
  return (
  <div className='App'>
      <BrowserRouter>
        <Menu />
        <Routes>
          <Route path='/' element={<Container/>} />
          <Route path='/crud-app-clients' element={<Clients/>} />
        </Routes>
      </BrowserRouter>
      
      
  </div>
  )
};

export default App;
