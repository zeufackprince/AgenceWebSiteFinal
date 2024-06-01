import React from 'react'
import "./style.css"
import { Link } from 'react-router-dom';
import { PButton } from '../../components/_tools';

const Profil = () => {
  const user = "/user.png";
  const deconnexion = () => { }
  const modifier = () => { }
  const changer = () => { }

  return (
    <div className='profil'>
      <div className="userp">
        <div className='userp-img-wrapper'>
          <div className="userp-img">
            <img src={user} alt="" />
            <PButton changer={changer} />
          </div>
        </div>
      </div>
      <div className='info-container'>
        <p>Noms</p>
        <p>Prenoms</p>
        <p>Ville</p>
        <p>Contact</p>
        <p>email</p>
        <Link to="/profil update">
          <PButton modifier={modifier} />
        </Link>
      </div>
      <PButton deconnexion={deconnexion} />
    </div>
  )
}

export default Profil