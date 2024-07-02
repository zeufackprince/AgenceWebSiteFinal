import React from 'react';
import { FaHeart } from "react-icons/fa";
import { Link } from 'react-router-dom';

function CardMain({imgSrc, title, likes}) {
  return (
    <div className='Card-Main'>
        <img src={imgSrc} alt="" />

        <div className="card_main_name">
            <h2>{title}</h2>

            <div className="card_icon">
                <i>
                    <FaHeart />
                    <span>{likes}</span>
                </i>
            </div>
        </div>
        
        <div className="stats">
            <p>Current Bid <span>1500 $</span></p>
            <p>Ending In <span>1d:12:25m</span></p>
        </div>

        <div className="card-button">
            <Link to='/' className="button1 btn">See bids</Link>
            <Link to='/' className="button2 btn">Modify</Link>
        </div>
    </div>
  )
}

export default CardMain;