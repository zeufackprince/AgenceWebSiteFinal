import React, { useState } from 'react';
// import UserList from './UserList';
import './MainList.css';
import UserList from './UserList.js'

function MainList() {

  const [User, setUser] = useState([
    {  nom: 'Gilles', email: 'dodji@gmail.com',numero: '+237 657849302' },
    {  nom: 'Rhina',   email: 'lacmou@gmail.com',  numero: '+237 679056201' },
    {  nom: 'Sosthenes', email: 'sosthenes@gmail.com',numero: '+237 654382901' },
    {  nom: 'Aniva', email: 'aniva@gmail.com',numero: '+237 654372819' },
    {  nom: 'Prince',  email: 'prince@gmail.com', numero: '+237 694039218' },
    {  nom: 'Armel', email: 'armel@gmail.com',numero: '+237 655554532' },
    {  nom: 'Canteuf',  email: 'canteuf@gmail.com',numero: '+237 675903728' },
    {  nom: 'Laeticia', email: 'laeticia@gmail.com', numero: '+237 680789065' },
    {  nom: 'Patricia',  email: 'patricia@gmail.com',numero: '+237 685432000' },
    {  nom: 'Dambove',  email: 'dambove@gmail.com',numero: '+237 671234567' }
  
  ]);
  
  const handleDelete = (id) => {
    setUser(User.filter(User => User.name !== id));
  };
  
  return (
    <div className="mainList">
        <h1>IMMOBILIUS</h1>
        <button onClick={() => alert('Add User')} className='btn-add'>Add an User <span>+</span></button>
        <UserList User={User} onDelete={handleDelete} />
    </div>
  )
}

export default MainList;