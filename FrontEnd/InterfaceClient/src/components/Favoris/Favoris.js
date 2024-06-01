import React from 'react';
import './Favoris.css'
import '../root.css'
import { FaTimes } from 'react-icons/fa';
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
                                <Link to='/detail' className='moreDetails'>
                                    <img src={bien.img} alt={bien.title} />
                                    <p className="titreArticleFav">{bien.title}</p>
                                    <p className="priceFav">{bien.price}</p>
                                </Link>
                                <FaTimes className="removeFav" onClick={() => removeFavori(bien.id)}/>
                            </div>
                       
                        
                    ))
                )}
            </div>
        </div>
        </>
        
    );
};

export default Favoris;
