import React, { useEffect } from 'react';
import './DashMenu.css';
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

function DashMenu() {

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
       <h1 className='logo'>IMMOBILIUS</h1>

        <ul id="main-Menu">
          <li><Link to='/admin/dashboard'><FaBorderAll /><span>Dashboard</span> </Link></li>
          <li><Link to='/admin/crud-client'><FaUsers /><span>Clients</span></Link></li>
          <li><Link to='/admin/'><FaEnvelope /><span>Notifications</span></Link></li>
          <li><Link to='/admin/crud-app-logements'><FaBuilding /><span>Logements</span></Link></li>
          <li><Link to='/'><FaChartLine /><span>Statistiques</span></Link></li>
        </ul>

        <ul className='last-Menu'>
        <li><Link to='/dashboard'><FaCog /><span>Paramètres</span></Link></li>
        <li><Link to='/dashboard'><FaSignOutAlt /><span>Déconnexion</span></Link></li>
        </ul>
    </menu>
  );
}

export default DashMenu;
