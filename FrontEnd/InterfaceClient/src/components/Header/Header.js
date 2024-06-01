import React, { useRef, useEffect } from "react";
import './Header.css';
import '../root.css';
import avatar from '../../pages/UserProfile/profilImages/avatar.svg';
import { FaBars, FaSearch, FaTimes, FaPen, FaHeart } from 'react-icons/fa';
import { NavLink, Link } from "react-router-dom";

function Header() {
    const navLinksRef = useRef(null);
    const profileContainer = useRef(null);
    const loginFormRef = useRef(null);
    const signupFormRef = useRef(null);
    const loginBtnRef = useRef(null);
    const signupBtnRef = useRef(null);
    const linkCompteRef = useRef(null);
    const titreCRef = useRef(null);
    const titreSRef = useRef(null);
    const Form = useRef(null);

      // Affichage du Formulaire
      const showForm = () =>{
        if(Form.current){
            Form.current.classList.toggle('showForm')
        }
    }

    const closeForm = () =>{
        if(Form.current){
            Form.current.classList.remove('showForm')
        }
    }
    
    useEffect(() => {
        const listMenu = navLinksRef.current.querySelectorAll('ul li');

        // Affichage du menu déroulant
        const closeMenu = () => {
            if (navLinksRef.current) {
                navLinksRef.current.classList.remove('showMenu');
            }
        };

        const slideMenu = () => {
            if (navLinksRef.current) {
                navLinksRef.current.classList.toggle('showMenu');
            }
        };


      
        // Affichage de l'edition du profile
        const activeProfile = () => {
            if (profileContainer.current) {
                profileContainer.current.classList.toggle('activeProfile');
            }
        };

        const closeProfile = () => {
            if (profileContainer.current) {
                profileContainer.current.classList.remove('activeProfile');
            }
        };

        listMenu.forEach(item => {
            item.addEventListener('click', closeMenu);
        });

        if (signupBtnRef.current && loginBtnRef.current && linkCompteRef.current) {
            signupBtnRef.current.onclick = () => {
                if (loginFormRef.current && titreCRef.current) {
                    loginFormRef.current.style.marginLeft = '-50%';
                    titreCRef.current.style.marginLeft = '-150%';
                }
            };

            loginBtnRef.current.onclick = () => {
                if (loginFormRef.current && titreCRef.current) {
                    loginFormRef.current.style.marginLeft = '0%';
                    titreCRef.current.style.marginLeft = '0%';
                }
            };

            linkCompteRef.current.onclick = () => {
                if (loginFormRef.current && titreCRef.current) {
                    loginFormRef.current.style.marginLeft = '-50%';
                    titreCRef.current.style.marginLeft = '-150%';
                }
            };
        }

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
                            <li><NavLink to='/Acheter'>Acheter</NavLink></li>
                            <li><NavLink to='/Louer'>Louer</NavLink></li>
                            <li><NavLink to='/NosServices'>Nos services</NavLink></li>
                            <li><NavLink to='/Contact'>Contact</NavLink></li>
                            <button className="loginLink" onClick={() => showForm()}><span>Se connecter</span></button>
                        </ul>
                        <button className="searchBtn">
                            <a href="#"><FaSearch className="fa-solid faSearch" /></a>
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
                        <Link to='/UserProfile'><FaPen className="FaPen" /></Link>
                    </div>
                    <div className="profileInformation">
                        <Link to='/Favoris' className="favoriLink">Favoris <FaHeart className="favori" /></Link>
                        <button className="logOut">Deconnexion</button>
                    </div>
                </div>
            </header>

            {/* Formulaire de connexion et d'authentification*/}
            <div className="popUpForm" ref={Form}>
                <div className="wrap">
                    <FaTimes className="closeForm" onClick={()=> closeForm()}/>
                    <div className="titres">
                        <div className="titreCo" ref={titreCRef}><p>Connexion</p></div>
                        <div className="titreSi" ref={titreSRef}><p>Inscription</p></div>
                    </div>
                    <div className="form-contenu">
                        <div className="slide-controls">
                            <input type="radio" name="slider" id="login" defaultChecked />
                            <input type="radio" name="slider" id="signup" />
                            <label htmlFor="login" className="slide login" ref={loginBtnRef}>Connexion</label>
                            <label htmlFor="signup" className="slide signup" ref={signupBtnRef}>Inscription</label>
                            <div className="slide-tab"></div>
                        </div>
                        <div className="form-card">
                            <form className="Fconnexion" ref={loginFormRef}>
                                <div className="form-div">
                                    <input type="text" name="password" placeholder="E-mail" required />
                                    <input type="password" placeholder="Mot de passe" required />
                                </div>
                                <Link className="mdp">Mot de passe oublié?</Link>
                                <input type="submit" value="Connexion" className="submitForm" />
                                <div className="compte" ref={linkCompteRef}>Vous n'êtes pas membre ? <Link>Créer un compte</Link></div>
                            </form>
                            <form className="Fsignup" ref={signupFormRef}>
                                <div className="form-div">
                                    <input type="text" placeholder="E-mail" />
                                    <input type="text" placeholder="Tel Ex : +237...." />
                                    <input type="password" placeholder="Mot de passe" required />
                                    <input type="password" placeholder="Confirmer le mot de passe" required />
                                </div>
                                <input type="submit" value="Créer un compte" className="submitForm" />
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Header;
