import React from 'react'
import './Container.css'
import TopContainer from './TopContainer.js';
import MainContainer from '../MainContainer/MainContainer.js';

function Container() {
  return (
    <div className='Container'>
        <TopContainer />
        <MainContainer />
    </div>
  );
}

export default Container