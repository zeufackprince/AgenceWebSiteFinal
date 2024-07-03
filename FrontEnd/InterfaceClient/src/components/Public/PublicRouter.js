import React, { useState, useEffect } from "react";
import { Route, Routes } from "react-router-dom";
import Acceuil from "../../pages/Accueil/Acceuil";
import UserProfile from "../../pages/UserProfile/UpdateUserProfile/UserProfile";
import Favoris from "../../components/Public/Favoris/Favoris";
import Search from "../../components/Public/Search/Search";
import Details from "../../components/Public/DetailsBien/Details"; 
import Acheter from '../../pages/Public/Acheter/Acheter'
import Louer from '../../pages/Public/Louer/Louer'
import Contact from '../../pages/Public/Contact/Contact'

import house1 from '../../images/houses/house1.jpg';
import house2 from '../../images/houses/house2.jpg';
import Layout from "./Layout";

const PublicRouter = () => {
    const [favoris, setFavoris] = useState(JSON.parse(localStorage.getItem('favoris')) || []);
    const [biens, setBiens] = useState([
        { id: 1, img: house1, title: 'Titre du bien', price: 'Prix du bien', description: 'Description du bien' },
        { id: 2, img: house2, title: 'Titre du bien', price: 'Prix du bien', description: 'Description du bien' },
        { id: 3, img: house2, title: 'Titre du bien', price: 'Prix du bien', description: 'Description du bien' },
    ]);

    useEffect(() => {
        localStorage.setItem('favoris', JSON.stringify(favoris));
    }, [favoris]);

    const addFavori = (bien) => {
        if (!favoris.some(favori => favori.id === bien.id)) {
            setFavoris([...favoris, bien]);
        }
    };

    const removeFavori = (id) => {
        setFavoris(favoris.filter(favori => favori.id !== id));
    };

    return (
       
            <Routes>
                <Route element={<Layout />}>
                    <Route path="/" element={<Acceuil addFavori={addFavori} favoris={favoris} />} />
                    <Route path="/UserProfile" element={<UserProfile />} />
                    <Route path="/Favoris" element={<Favoris favoris={favoris} removeFavori={removeFavori} />} />
                    <Route path="/detail/:id" element={<Details biens={biens} />} />
                    <Route path="/rechercher" element={<Search />} />
                    <Route path="/acheter" element={<Acheter/>}/>
                    <Route path="/louer" element={<Louer/>}/>
                    <Route path="/contact" element={<Contact/>}/>
                </Route>
            </Routes>
    );
};

export default PublicRouter;
