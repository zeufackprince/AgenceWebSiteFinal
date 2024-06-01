import React, { useRef } from 'react'
import "./UserProfile.css"
import user from '../profilImages/avatar.svg';
import '../../../components/root.css'
import { Link } from 'react-router-dom';


const UserProfile = () => {

  return (
   <>
    <h1>Editer le profil utilisateur</h1>
    <div className='userProfile'>
      <div className='editProfilContainer'>

       <div className='profileImg'>
            <img src={user} alt='User profile image' className='imgProfile'/>
        </div>

        <form className='editProfile'>
          <input type='text' placeholder='Noms' required/>
          <input type='text' placeholder='Prenoms' />
          <input type='text'placeholder='Ville'/>
          <input type='text' placeholder='Contact'/>
          <input type='text' placeholder='Email' />
          <div className='buttonProfile'>
          <input type='submit' value='Modifier' className='editprofBtn'/>
          <Link className='editprofBtn'>Annuler</Link>
          </div>
          
        </form>

      </div>
      
    </div>

   </>
  )
}

export default UserProfile
