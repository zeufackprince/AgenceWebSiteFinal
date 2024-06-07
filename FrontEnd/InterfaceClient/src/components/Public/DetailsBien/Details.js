import React from 'react';
import { useParams } from 'react-router-dom';
import './Details.css';

const Details = ({ biens }) => {
  const { id } = useParams();
  const bien = biens.find(b => b.id === parseInt(id));

  return (
    <div className="details">
      <h1>{bien.title}</h1>
      <img src={bien.img} alt={bien.title} />
      <p className="price">{bien.price}</p>
      <p className="description">{bien.description}</p>

      <button onClick={() => alert('Contactez l\'agent immobilier')}>Contacter l'agent immobilier</button>
    </div>
  );
};

export default Details;
