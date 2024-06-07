import React, { useState } from 'react';
import '../../../components/root.css'


const quartiersParVille = {
    Douala: ['Bonapriso', 'Akwa', 'Bonanjo', 'Deido'],
    Yaoundé: ['Bastos', 'Mokolo', 'Nlongkak', 'Etoudi'],
    Bafoussam: ['Tougang', 'Djeleng', 'Banengo'],
    Dschang: ['Tchoualé', 'Nkong-Ni', 'Foréké'],
};

const Seacrh = () => {
    const [typeLogement, setTypeLogement] = useState('');
    const [ville, setVille] = useState('');
    const [Budget, setBudget] = useState('');
   

    const handleVilleChange = (e) => {
        setVille(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        //
        console.log('Type de Logement:', typeLogement);
        console.log('Ville:', ville);
        console.log('Budget:', Budget);
        
    };

    return (
        <>
            <h1 className='titleSearch'>Quel type de bien immobilier voulez-vous ?</h1>
            <form onSubmit={handleSubmit} className='searchForm'>
                
                <div className='logementContainer'>
                    <label>Type de Logement :</label>
                    <select value={typeLogement} onChange={(e) => setTypeLogement(e.target.value)} className='logementChoise' required>
                        <option value=""></option>
                        <option value="STUDIO">Studio</option>
                        <option value="APPARTEMENT">Appartement</option>
                        <option value="CHAMBRE">Chambre</option>
                    </select>
                </div>

                <div className='cityContainer'>
                    <label>Ville :</label>
                    <select value={ville} onChange={handleVilleChange} required>
                        <option value=""></option>
                        <option value="DOUALA">Douala</option>
                        <option value="YAOUNDE">Yaoundé</option>
                        <option value="BAFOUSSAM">Bafoussam</option>
                        <option value="DSCHANG">Dschang</option>
                        <option value="LIMBE">Limbe</option>
                        <option value="BUEA">Buea</option>
                        <option value="KRIBI">Kribi</option>
                        <option value="FOUMBAN">Foumban</option>
                        <option value="MAROUA">Maroua</option>
                    </select>
                </div>

           

                <div className='budget'>
                    {/*<h2>Budjet (Fcfa)</h2>
                    <label>Min</label>
                    <input
                        type="number"
                        value={minBudget}
                        onChange={(e) => setminBudget(e.target.value)}
                        required
                    />
                    <label>Max</label>
                    <input type='numer' value={maxBudget} onChange={(e) => setmaxBudget(e.target.value)}
                    required/>*/}

                    <label>Budget (Fcfa) :</label>
                    <select value={Budget} onChange={(e) => setBudget(e.target.value)} className='budgetChoice' required>
                        <option value=""></option>
                        <option value={20000}>0-20000</option>
                        <option value={50000}>20001-50000</option>
                        <option value={100000}>50001-100000</option>
                    </select>
                </div>

                <button type="submit" className='SearchBtn'>Rechercher</button>
            </form>
        </>    
    );
};

export default Seacrh;
