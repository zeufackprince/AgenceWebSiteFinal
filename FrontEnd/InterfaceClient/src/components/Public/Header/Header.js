import React, { useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import './Header.css';
import '../../root.css';
import avatar from '../../../pages/UserProfile/profilImages/avatar.svg';
import { FaBars, FaSearch, FaTimes, FaPen, FaHeart } from 'react-icons/fa';
import { NavLink, Link } from "react-router-dom";
import '../Search/Search.css';

function Header() {
    const navLinksRef = useRef(null); // Référence pour les liens de navigation
    const profileContainer = useRef(null); // Référence pour le conteneur du profil
    const navigate = useNavigate(); // Utilisation de la navigation

    

   

    useEffect(() => {
        const listMenu = navLinksRef.current.querySelectorAll('ul li'); // Liste des éléments de menu

        // Fermer le menu
        const closeMenu = () => {
            if (navLinksRef.current) {
                navLinksRef.current.classList.remove('showMenu');
            }
        };

        // Ajouter des gestionnaires d'événements pour chaque élément de menu
        listMenu.forEach(item => {
            item.addEventListener('click', closeMenu);
        });

        

        // Nettoyer les gestionnaires d'événements
        return () => {
            listMenu.forEach(item => {
                item.removeEventListener('click', closeMenu);
            });
        };
    }, []);

    return (
        <>
        <header>
            <nav className="navbar">
                <NavLink className="logo" to='/'>IMMOBILIUS</NavLink>
                <div className="nav-links" ref={navLinksRef}>
                    <ul>
                        <FaTimes className="closeMenu" onClick={() => navLinksRef.current.classList.remove('showMenu')} />
                        <li><NavLink to='/'>Acceuil</NavLink></li>
                        <li><NavLink to='/acheter'>Acheter</NavLink></li>
                        <li><NavLink to='/louer'>Louer</NavLink></li>
                        <li><NavLink to='/contact'>Contact</NavLink></li>
                        <Link to='/auth/login' className="loginLink"><span>Se connecter</span></Link>
                    </ul>
                    <button className="searchBtn">
                        <Link to='/rechercher' href="#"><FaSearch className="fa-solid faSearch" /></Link>
                    </button>
                </div>
                <div className="profil">
                    <img src={avatar} onClick={() => profileContainer.current.classList.toggle('activeProfile')} />
                </div>
                <FaBars className="fa-solid fa-bars" onClick={() => navLinksRef.current.classList.toggle('showMenu')} />
            </nav>

            <div className="showProfile" ref={profileContainer}>
                <FaTimes className="FaTimes" onClick={() => profileContainer.current.classList.remove('activeProfile')} />

                <div className="manageProfile">
                    <img src={avatar} />
                    <p className="usernameProfil"></p>
                </div>

                <div className="profileInformation">
                    <Link to='/Favoris' className="favoriLink">Favoris <FaHeart className="favori" /></Link>
                    <div className="userInfo">
                        <p>Nom</p>
                        <p>Prenom</p>
                        <p>Email</p>
                        <p>Telephone</p>
                    </div>
                    <Link to='/UserProfile' className="logOut">Modifier</Link>
                </div>
            </div>
        </header>

       
    </>
    );
}

export default Header;
