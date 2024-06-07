//Ici se trouve les biens ajoutés en favori
import React from 'react';
import './Favoris.css'
import '../../root.css'
import x_cross from './x_cross.svg'
import { Link } from 'react-router-dom';


const Favoris = ({ favoris, removeFavori }) => {
    return (
        <>
        <div className="favoriContainer">
            <h1>Mes Favoris</h1>

            <div className='favoriContent'>
                {favoris.length === 0 ? (
                    <p>Aucun favori ajouté.</p>
                ) : (
                    favoris.map(bien => (
                       
                            <div key={bien.id} className="bienImmoFav">
                                <Link to={`/detail/${bien.id}`} className='moreDetails'>
                                    <img src={bien.img} alt={bien.title} />
                                    <p className="titreArticleFav">{bien.title}</p>
                                    <p className="priceFav">{bien.price}</p>
                                </Link>
                                <img src={x_cross} className="removeFav" onClick={() => removeFavori(bien.id)}/>
                            </div>
                       
                        
                    ))
                )}
            </div>
        </div>
        </>
        
    );
};

export default Favoris;
