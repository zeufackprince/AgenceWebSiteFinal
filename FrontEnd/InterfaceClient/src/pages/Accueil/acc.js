import React, { useState } from 'react';
import './Acceuil.css';
import '../../components/root.css';
import house1 from '../../images/houses/house1.jpg';
import house2 from '../../images/houses/house2.jpg';
import house3 from '../../images/houses/house3.jpg';
import house4 from '../../images/houses/house4.jpg';
import house5 from '../../images/houses/house5.jpg';
import house6 from '../../images/houses/house6.jpg';
import house7 from '../../images/houses/house7.jpg';
import { FaHeart } from "react-icons/fa";

const Acc = ({ addFavori, favoris }) => {
    // Liste des biens immobiliers
    const biens = [
        { id: 1, img: house1, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
        { id: 2, img: house2, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
        { id: 3, img: house3, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
        { id: 4, img: house4, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
        { id: 5, img: house5, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
        { id: 6, img: house6, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa" },
    ];

    // État pour gérer les messages temporaires d'ajout aux favoris
    const [addedFavoriIds, setAddedFavoriIds] = useState([]);

    // Fonction pour gérer l'ajout d'un bien aux favoris
    const handleAddFavori = (bien) => {
        if (!addedFavoriIds.includes(bien.id)) {
            addFavori(bien);
            setAddedFavoriIds([...addedFavoriIds, bien.id]);
            // Supprime le message après 3 secondes
            setTimeout(() => {
                setAddedFavoriIds((prevIds) => prevIds.filter(id => id !== bien.id));
            }, 3000);
        }
    };

    return (
        <>
            {/* Présentation d'Immobilius */}
            <section className="presentation">
                <div className="txtPres">
                    <h1>BIENVENU SUR IMMOBILIUS</h1>
                    <h3>Lorem ipsum dolor sit amet consectetur adipisicing elit...</h3>
                </div>
            </section>
            {/* À propos de nous */}
            <section className="aboutUs">
                <div className="imgAboutUs">
                    <img src={house7} alt="" />
                </div>
                <div className="aboutImmo">
                    <h1>À propos de nous</h1>
                    <h2>Lorem ipsum dolor sit amet consectetur adipisicing elit...</h2>
                    <h3>Lorem ipsum dolor sit, amet consectetur adipisicing elit...</h3>
                </div>
            </section>
            {/* Articles immobiliers */}
            <section className="articlesImmo">
                <h1>Achetez/ louer un bien immobilier sur immobilius</h1>
                <div className="listBien">
                    {biens.map(bien => (
                        <div className="bienImmo" key={bien.id}>
                            <img src={bien.img} alt="" />
                            <p className="titreArticle">{bien.title}</p>
                            <p className="price">{bien.price}</p>
                            <FaHeart 
                                className={`addToFavori ${favoris.some(favori => favori.id === bien.id) ? 'addedFavori' : ''}`} 
                                onClick={() => handleAddFavori(bien)} 
                            />
                            {addedFavoriIds.includes(bien.id) && <p className="favoriAddedMessage">Ajouté en favori</p>}
                            <button className="more">En savoir plus</button>
                        </div>
                    ))}
                </div>
            </section>
        </>
    );
};

export default Acc;
