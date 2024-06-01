import React from "react";
import Header from "./components/Header/Header";
import { Route, Routes, BrowserRouter } from "react-router-dom";
import Acceuil from "./pages/Accueil/Acceuil";
import Footer from "./components/Footer/Footer";
import UserProfile from "./pages/UserProfile/UpdateUserProfile/UserProfile";
import Favoris from "./components/Favoris/Favoris";
import DetailBien from "./pages/DetailBien/DetailBien";

class App extends React.Component {
    constructor(props) {
        super(props);
        // Récupérer les données des favoris depuis le localStorage s'ils existent
        const storedFavoris = JSON.parse(localStorage.getItem('favoris')) || [];
        this.state = {
            favoris: storedFavoris
        };
    }

    addFavori = (bien) => {
        this.setState((prevState) => {
            const isAlreadyFavori = prevState.favoris.some(favori => favori.id === bien.id);
            if (!isAlreadyFavori) {
                const updatedFavoris = [...prevState.favoris, bien];
                // Enregistrer les favoris dans le localStorage
                localStorage.setItem('favoris', JSON.stringify(updatedFavoris));
                return {
                    favoris: updatedFavoris
                };
            }
            return prevState;
        });
    };

    removeFavori = (id) => {
        this.setState((prevState) => {
            const updatedFavoris = prevState.favoris.filter(favori => favori.id !== id);
            // Enregistrer les favoris dans le localStorage
            localStorage.setItem('favoris', JSON.stringify(updatedFavoris));
            return {
                favoris: updatedFavoris
            };
        });
    };

    render() {
        return (
            <>
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
                        <Route path="/detail/:title" element = {<DetailBien/>}/>
                    </Routes>
                    <Footer />
                </BrowserRouter>
            </>
        );
    }
}

export default App;
