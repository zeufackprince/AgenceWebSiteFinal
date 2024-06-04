import React from 'react';
import { Link } from 'react-router-dom';

function MainRightTop() {
  return (
    <div className='top-Cards'>
        <div className="top-card_name">
            <h2>Statistics</h2>
            <Link to='/' className='view--more'>View More</Link>
        </div>

        <div className="housing">
            <p>Housing Available <span>65</span></p>
            <p>Housing Canceled <span>15</span></p>
            <p>Housing Rented <span>20</span></p>
            <p>Housing Reserved <span>35</span></p>
            <p>Total Housing <span>115</span></p>
        </div>
    </div>
  )
}

export default MainRightTop;