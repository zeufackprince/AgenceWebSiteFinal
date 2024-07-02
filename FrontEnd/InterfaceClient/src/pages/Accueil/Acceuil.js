import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { FaHeart } from "react-icons/fa";
import './Acceuil.css';
import '../../components/root.css';
import house1 from '../../images/houses/house1.jpg';
import house2 from '../../images/houses/house2.jpg';
import house3 from '../../images/houses/house3.jpg';
import house4 from '../../images/houses/house4.jpg';
import house5 from '../../images/houses/house5.jpg';
import house6 from '../../images/houses/house6.jpg';
import house7 from '../../images/houses/house7.jpg';

const Acceuil = ({ addFavori, favoris }) => {
    const biens = [
        { id: 1, img: house1, title: "Appartement haut standing avec balcon arrière", price: "150.000Fcfa", description: "Description du bien 1" },
        { id: 2, img: house2, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa", description: "Description du bien 2"},
        { id: 3, img: house3, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa", description: "Description du bien 3"},
        { id: 4, img: house4, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa", description: "Description du bien 4" },
        { id: 5, img: house5, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa", description: "Description du bien 5" },
        { id: 6, img: house6, title: "Lorem ipsum dolor sit, amet consectetur", price: "15.000Fcfa", description: "Description du bien 6"},
    ];

    const [addedFavoriIds, setAddedFavoriIds] = useState([]);

    const handleAddFavori = (bien) => {
        if (!addedFavoriIds.includes(bien.id)) {
            addFavori(bien);
            setAddedFavoriIds([...addedFavoriIds, bien.id]);
            setTimeout(() => {
                setAddedFavoriIds((prevIds) => prevIds.filter(id => id !== bien.id));
            }, 3000);
        }
    };

    return (
        <>
            <section className="presentation">
                <div className="txtPres">
                    <h1>BIENVENU SUR IMMOBILIUS</h1>
                    <h3>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi cum ullam fugiat cupiditate neque, fugit consequuntur reiciendis incidunt quis placeat odio quo, asperiores voluptatum facilis id veritatis expedita corrupti tempore.</h3>
                </div>
            </section>
            <section className="aboutUs">
                <div className="imgAboutUs">
                    <img src={house7} alt="" />
                </div>
                <div className="aboutImmo">
                    <h1>À propos de nous</h1>
                    <h2>Lorem ipsum dolor sit amet consectetur adipisicing elit...</h2>
                    <h3>Lorem ipsum dolor sit amet consectetur adipisicing elit. Sequi cum ullam fugiat cupiditate neque, fugit consequuntur reiciendis incidunt quis placeat odio quo, asperiores voluptatum facilis id veritatis expedita corrupti tempore.</h3>
                </div>
            </section>
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
                            <Link to={`/detail/${bien.id}`} className="more">En savoir plus</Link>
                        </div>
                    ))}
                </div>
            </section>
        </>
    );
};

export default Acceuil;
