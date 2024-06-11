import React, { useRef, useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import './Header.css';
import '../root.css';
import avatar from '../../pages/UserProfile/profilImages/avatar.svg';
import { FaBars, FaSearch, FaTimes, FaPen, FaHeart } from 'react-icons/fa';
import { NavLink, Link } from "react-router-dom";
import { AuthContext } from '../AuthContext';
import '../Search/Search.css';

function Header() {
    const { login } = useContext(AuthContext); // Utilisation du contexte d'authentification
    const navLinksRef = useRef(null); // Référence pour les liens de navigation
    const profileContainer = useRef(null); // Référence pour le conteneur du profil
    const loginFormRef = useRef(null); // Référence pour le formulaire de connexion
    const signupFormRef = useRef(null); // Référence pour le formulaire d'inscription
    const loginBtnRef = useRef(null); // Référence pour le bouton de connexion
    const signupBtnRef = useRef(null); // Référence pour le bouton d'inscription
    const linkCompteRef = useRef(null); // Référence pour le lien de création de compte
    const titreCRef = useRef(null); // Référence pour le titre de connexion
    const titreSRef = useRef(null); // Référence pour le titre d'inscription
    const Form = useRef(null); // Référence pour le formulaire pop-up
    const navigate = useNavigate(); // Utilisation de la navigation

    // Afficher ou masquer le formulaire pop-up
    const showForm = () => {
        if (Form.current) {
            Form.current.classList.toggle('showForm');
        }
    };

    // Fermer le formulaire pop-up
    const closeForm = () => {
        if (Form.current) {
            Form.current.classList.remove('showForm');
        }
    };

    // Gérer la soumission du formulaire de connexion
    const handleLogin = (event) => {
        event.preventDefault();
        const email = event.target.elements.email.value; // Récupérer l'email
        const password = event.target.elements.password.value; // Récupérer le mot de passe

        // Simuler la logique de connexion
        let userData;
        if (email === 'admin@example.com' && password === 'admin') {
            userData = { email, role: 'admin' }; // Admin
        } else if (email === 'client@example.com' && password === 'user') {
            userData = { email, role: 'client' }; // Client
        } else {
            alert('Email ou mot de passe incorrect'); // Alerte en cas d'erreur
            return;
        }

        // Connexion de l'utilisateur
        login(userData);

        // Redirection en fonction du rôle
        if (userData.role === 'admin') {
            navigate('/dashboard'); // Rediriger vers le tableau de bord admin
        } else {
            navigate('/'); // Rediriger vers la page d'accueil
        }

        closeForm(); // Fermer le formulaire pop-up
    };

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

        // Configurer les actions des boutons de connexion et d'inscription
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
                            <li><NavLink to='/Acheter'>Acheter</NavLink></li>
                            <li><NavLink to='/Louer'>Louer</NavLink></li>
                            <li><NavLink to='/NosServices'>Nos services</NavLink></li>
                            <li><NavLink to='/Contact'>Contact</NavLink></li>
                            <button className="loginLink" onClick={showForm}><span>Se connecter</span></button>
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
                        <Link to='/UserProfile'><FaPen className="FaPen" /></Link>
                    </div>
                    <div className="profileInformation">
                        <Link to='/Favoris' className="favoriLink">Favoris <FaHeart className="favori" /></Link>
                        <button className="logOut">Deconnexion</button>
                    </div>
                </div>
            </header>

            <div className="popUpForm" ref={Form}>
                <div className="wrap">
                    <FaTimes className="closeForm" onClick={closeForm} />
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
                            <form className="Fconnexion" ref={loginFormRef} onSubmit={handleLogin} method="post">
                                <div className="form-div">
                                    <input type="text" name="email" placeholder="E-mail" required />
                                    <input type="password" name="password" placeholder="Mot de passe" required />
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
