import React, {useEffect} from 'react';
import './Menu.css';
import logo from '../../img/logo.png'
import { Link } from 'react-router-dom';
import {
  FaEnvelope, 
  FaUsers,
  FaSignOutAlt,
  FaBuilding,
  FaChartLine,
  FaCog,
  FaBorderAll
} from "react-icons/fa";


function Menu() {

  useEffect(() => {
    const mainMenuLi = document.getElementById("main-Menu").querySelectorAll("li");
    function changeActive() {
      mainMenuLi.forEach(n => n.classList.remove("active"));
      this.classList.add("active");
    }
    mainMenuLi.forEach((n) => n.addEventListener("click", changeActive))
  }, []);

  return (
    <menu>
        <img src = {logo} alt = "Logo" />

        <ul id="main-Menu">
          <li><Link to='/'><FaBorderAll /><span>Dashboard</span> </Link></li>
          <li><Link to='/crud-app-clients'><FaUsers /><span>Clients</span></Link></li>
          <li><Link to='/'><FaEnvelope /><span>Notifications</span></Link></li>
          <li><Link to='/'><FaBuilding /><span>Logements</span></Link></li>
          <li><Link to='/'><FaChartLine /><span>Statistiques</span></Link></li>
        </ul>

        <ul className='last-Menu'>
        <li><Link to='/'><FaCog /><span>Paramètres</span></Link></li>
        <li><Link to='/'><FaSignOutAlt /><span>Déconnexion</span></Link></li>       
        </ul>
    </menu>
  );
}



export default Menu 