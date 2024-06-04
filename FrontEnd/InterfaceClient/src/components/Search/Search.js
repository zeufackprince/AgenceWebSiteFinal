import React, { useState } from 'react';
import '../../components/root.css'


const quartiersParVille = {
    Douala: ['Bonapriso', 'Akwa', 'Bonanjo', 'Deido'],
    Yaoundé: ['Bastos', 'Mokolo', 'Nlongkak', 'Etoudi'],
    Bafoussam: ['Tougang', 'Djeleng', 'Banengo'],
    Dschang: ['Tchoualé', 'Nkong-Ni', 'Foréké'],
};

const Seacrh = () => {
    const [typeLogement, setTypeLogement] = useState('');
    const [ville, setVille] = useState('');
    const [quartier, setQuartier] = useState('');
    const [budget, setBudget] = useState('');

    const handleVilleChange = (e) => {
        setVille(e.target.value);
        setQuartier(''); // Reinitialise le quartier quand on change de ville
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        //
        console.log('Type de Logement:', typeLogement);
        console.log('Ville:', ville);
        console.log('Quartier:', quartier);
        console.log('Budget:', budget);
    };

    return (
        <>
            <h1 className='titleSearch'>Quel type de bien immobilier voulez-vous ?</h1>
            <form onSubmit={handleSubmit} className='searchForm'>
                
                <div className='logementContainer'>
                    <label>Type de Logement :</label>
                    <select value={typeLogement} onChange={(e) => setTypeLogement(e.target.value)} className='logementChoise' required>
                        <option value=""></option>
                        <option value="studio">Studio</option>
                        <option value="appartement">Appartement</option>
                        <option value="chambre">Chambre</option>
                    </select>
                </div>

                <div className='cityContainer'>
                    <label>Ville :</label>
                    <select value={ville} onChange={handleVilleChange} required>
                        <option value=""></option>
                        <option value="Douala">Douala</option>
                        <option value="Yaoundé">Yaoundé</option>
                        <option value="Bafoussam">Bafoussam</option>
                        <option value="Dschang">Dschang</option>
                    </select>
                </div>

                <div className='quartierContainer'>
                    <label>Quartier :</label>
                    <select value={quartier} onChange={(e) => setQuartier(e.target.value)} required>
                        <option value=""></option>
                        {ville && quartiersParVille[ville].map((q, index) => (
                            <option key={index} value={q}>{q}</option>
                        ))}
                    </select>
                </div>

                <div className='budget'>
                    <label>Budget (Fcfa) :</label>
                    <input
                        type="number"
                        value={budget}
                        onChange={(e) => setBudget(e.target.value)}
                        required
                    />
                </div>

                <button type="submit" className='SearchBtn'>Rechercher</button>
            </form>
        </>    
    );
};

export default Seacrh;
