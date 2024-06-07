import React, { useState } from 'react';
import ListComponent from './work/ListComponent.jsx'
import Tableau from './work/Tableau.jsx';


function MyForm() {
  // State pour stocker les valeurs des champs du formulaire
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: ''
  });

  // Fonction pour gérer les changements dans les champs du formulaire
  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };



  // Fonction pour gérer la soumission du formulaire
  const handleSubmit = (event) => {
    event.preventDefault();
    // Vous pouvez ajouter ici la logique pour traiter les données du formulaire
    console.log(formData);
  };

  return (
    <div>
      <h2>Mon Formulaire</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name">Nom:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="message">Message:</label>
          <textarea
            id="message"
            name="message"
            value={formData.message}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Envoyer</button>
      </form>

      <ListComponent name="Laety"></ListComponent>
      <Tableau></Tableau>
    </div>
  );
}

export default MyForm;n