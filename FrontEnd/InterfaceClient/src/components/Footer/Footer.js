import React from "react";
import { Link } from "react-router-dom";
import './Footer.css'
import '../../components/root.css'
import { FaFacebook,FaTwitter,FaInstagram } from "react-icons/fa";
const Footer =() =>{
     
    return(
      <footer className="footer">
      <div className="footer-container">
          <div className="footer-section">
              <Link className="logo" to='/'>IMMOBILIUS</Link>
              <p>© 2024 IMMOBILIUS. Tous droits réservés.</p>
          </div>
          <div className="footer-section">
              <h4>Navigation</h4>
              <ul>
                 <li><Link to = '/'>Acceuil</Link></li>
                 <li><Link to = '/Acheter' >Acheter</Link></li>
                  <li><Link to = '/Louer'>Louer</Link></li>
                  <li><Link to = '/NosServices'>Nos services</Link></li>
                  <li><Link to = '/Contact'>Contact</Link></li>
              </ul>
          </div>
          <div className="footer-section">
              <h4>Suivez-nous</h4>
              <ul className="social-links">
                  <li><a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className=' faceLk'><FaFacebook/></a></li>
                  <li><a href="https://twitter.com" target="_blank" rel="noopener noreferrer" className=' twLk'><FaTwitter/></a></li>
                  <li><a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className=' igLk'><FaInstagram/></a></li>
              </ul>
          </div>
      </div>
  </footer>
    )
}

export default Footer