//Tableau de bord 
import React from 'react'
import './DashMain.css'
import TopContainer from './TopContainer.js'
import MainContainer from '../MainContainer/MainContainer.js';

function DashMain() {
  return (
    <div className='Container'>
        <TopContainer />
        <MainContainer />
    </div>
  );
}

export default DashMain