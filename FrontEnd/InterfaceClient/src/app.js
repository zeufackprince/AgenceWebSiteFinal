import React, { useContext } from "react";
import './App.css';
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import { AuthProvider, AuthContext } from './components/AuthContext'; // Assurez-vous que ce chemin est correct
import Header from "./components/Header/Header";
import Footer from "./components/Footer/Footer";
import Acceuil from "./pages/Accueil/Acceuil";
import UserProfile from "./pages/UserProfile/UpdateUserProfile/UserProfile";
import Favoris from "./components/Favoris/Favoris";
import Search from "./components/Search/Search";
import DetailBien from "./pages/DetailBien/DetailBien";

import DashMenu from "./components/AdminComponents/DashMenu/DashMenu";
import MainList from "./components/AdminComponents/crud-app-clients/MainList";
import AddLogement from "./components/AdminComponents/ManageLogement/Main";
import DashMain from "./components/AdminComponents/DashMain/DashMain";

// Composant de route protégée pour limiter l'accès en fonction du rôle
const ProtectedRoute = ({ children, role }) => {
    const { user } = useContext(AuthContext);

    // Si l'utilisateur n'est pas connecté, rediriger vers la page d'accueil
    if (!user) {
        return <Navigate to="/" />;
    }

    // Si le rôle de l'utilisateur ne correspond pas, rediriger vers la page d'accueil
    if (user.role !== role) {
        return <Navigate to="/" />;
    }

    // Si tout est bon, rendre les enfants du composant
    return children;
};

class App extends React.Component {
    constructor(props) {
        super(props);
        const storedFavoris = JSON.parse(localStorage.getItem('favoris')) || [];
        this.state = {
            favoris: storedFavoris
        };
    }

    // Ajouter un bien aux favoris et le sauvegarder dans le localStorage
    addFavori = (bien) => {
        this.setState((prevState) => {
            const isAlreadyFavori = prevState.favoris.some(favori => favori.id === bien.id);
            if (!isAlreadyFavori) {
                const updatedFavoris = [...prevState.favoris, bien];
                localStorage.setItem('favoris', JSON.stringify(updatedFavoris));
                return { favoris: updatedFavoris };
            }
            return prevState;
        });
    };

    // Retirer un bien des favoris et mettre à jour le localStorage
    removeFavori = (id) => {
        this.setState((prevState) => {
            const updatedFavoris = prevState.favoris.filter(favori => favori.id !== id);
            localStorage.setItem('favoris', JSON.stringify(updatedFavoris));
            return { favoris: updatedFavoris };
        });
    };

    render() {
        return (
            <AuthProvider>
                <BrowserRouter>
                    <Header />
                    <Routes>
                        <Route
                            path="/"
                            element={<Acceuil addFavori={this.addFavori} favoris={this.state.favoris} />}
                        />
                        <Route
                            path="/UserProfile"
                            element={<UserProfile />}
                        />
                        <Route
                            path="/Favoris"
                            element={
                                <Favoris
                                    favoris={this.state.favoris}
                                    removeFavori={this.removeFavori}
                                />
                            }
                        />
                        <Route path="/detail/:title" element={<DetailBien />} />
                        <Route path="/rechercher" element={<Search />} />

                        {/* Routes protégées pour l'interface admin */}
                        <Route
                            path="/dashboard"
                            element={
                                <ProtectedRoute role="admin">
                                    <DashMain />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/crud-app-clients"
                            element={
                                <ProtectedRoute role="admin">
                                    <MainList />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/manage-housings"
                            element={
                                <ProtectedRoute role="admin">
                                    <AddLogement />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/dashmenu"
                            element={
                                <ProtectedRoute role="admin">
                                    <DashMenu />
                                </ProtectedRoute>
                            }
                        />
                    </Routes>
                    {/* Afficher le Footer uniquement si l'utilisateur n'est pas sur une route admin */}
                    {!this.state.user?.role === 'admin' && <Footer />}
                </BrowserRouter>
            </AuthProvider>
        );
    }
}

export default App;
