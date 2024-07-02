import React from 'react';
import Admins from './Admins.js';
import { Link } from 'react-router-dom';

function MainRightBopttom() {
  return (
    <div className='bottomRightCard'>
      <div className="bottomName">       
        <h2>Messages</h2>
        <Link to='/'>View More</Link>
      </div>

      <div className='notifContainer'>
        {
          Admins && Admins.map((admin) => 
          (
            <div className="notifactions" key={Admins.id}>
              <div className="notif-image">
                <img src={admin.imgSrc} alt="photo de profile"/>
              </div>
              <p className="notif-name">
                  {admin.admin_name} <span>Message</span>
              </p>
              <Link to='/' className="button1 btn">Message</Link>
            </div>
          ))
        }
      </div>
    </div>
  )
}

export default MainRightBopttom;