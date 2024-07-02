import React, {useEffect} from 'react';
import profile from '../../img/profile.jpg';
import { Link } from 'react-router-dom';
import { FaBell, FaChevronDown } from "react-icons/fa";

function TopContainer() {

  useEffect(() => {
    const menuTarget = document.getElementById("chevron");
    const menuContainer = document.getElementById("menu-Container");

    menuTarget.addEventListener("mouseenter", () => {
      menuTarget.style.transform = 'rotate(180deg)';
      menuContainer.style.transform = 'translateX(0px)';
    });

    menuContainer.addEventListener("mouseleave", () => {
      menuTarget.style.transform = 'rotate(0deg)';
      menuContainer.style.transform = 'translateX(250px)';
    });

  }, []);

  return (
    <div className='TopContainer'>
        <div className="container-title">
          <h1 class="main-title">Dashboard</h1>
        </div>

        <div className="profile-box">
          <i className="notif-icon">
            <Link to='/' id=""><FaBell /></Link>     
          </i>
          <div className="profileImage">
            <Link to='/'><img src={profile} alt="" width="50px"/></Link>
          </div>
          <p className="profileName"><span>Teufack</span> Doury Cantor</p>
          <i className="menuChevron" id="chevron">
            <FaChevronDown />
          </i>
          <div className="menu-Container" id="menu-Container">
            <ul>
              <li><Link to='/'>Dashboard</Link></li>
              <li><Link to='/crud-app-logements'>Logements</Link></li>
              <li><Link to=''>Notifications</Link></li>
              <li><Link to=''>Infos</Link></li>
            </ul>
          </div>
        </div>
    </div>
  )
}

export default TopContainer