import React, { Component } from "react";
// import userEvent from '@testing-library/user-event'
import "./form.css";

class Form extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div className="container">
        <form onSubmit={this.handleSubmit}>
          <fieldset>
            <div className="top">
              <div className="title">
                <h2>Ajout d'un bien immobilier</h2>
              </div>

              <div className="content">
                <div className="form-input">
                  <label htmlFor="nomBatiment">nom du batiment</label>
                  <input
                    type="text"
                    id="nomBatiment"
                    name="nomBatiment"
                    value={this.state.nombatiment}
                    onChange={this.handleChange}
                    required
                  />
                </div>

                <div className="form-input">
                  <label htmlFor="Dimension">dimension</label>
                  <input type="number" id="disponibilite" />
                </div>

                <div className="form-input">
                  <label htmlFor="Price">prix</label>
                  <input
                    type="number"
                    id="Price"
                    name="Price"
                    value={this.state.c}
                    onChange={this.handleChange}
                    required
                  />
                </div>

                <div className="form-input">
                  <label htmlFor="">descriptoin</label>
                  <textarea
                    id="Description"
                    name="Description"
                    rows="2"
                    cols="86"
                    value={this.state.Description}
                    onChange={this.handleChange}
                    required
                  ></textarea>
                </div>

                <div className="form-select">
                  <label htmlFor="type">type</label>
                  <select
                    id="type"
                    name="type"
                    value={this.state.type}
                    onChange={this.handleChange}
                    required
                  >
                    <option>------------</option>
                    <option>Appartement</option>
                    <option>Studio</option>
                    <option>Chambre</option>
                  </select>
                </div>

                <div className="form-select">
                  <label htmlFor="ville">ville</label>
                  <select
                    id="ville"
                    name="ville"
                    value={this.state.ville}
                    onChange={this.handleChange}
                    required
                  >
                    <option>------------</option>
                    <option>Douala</option>
                    <option>Yaounde</option>
                    <option>Bafousann</option>
                    <option>Dschang</option>
                  </select>
                </div>

                <div className="form-input">
                  <label htmlFor="image">image </label>
                  <input
                    type="file"
                    id="image"
                    name="image"
                    value={this.state.c}
                    onChange={this.handleChange}
                    required
                  />
                </div>
              </div>
            </div>

            <div className="bottom">
              <input type="submit" value="Valider" />
              <input type="reset" value="Annuler Fornulaire" />
            </div>
            {/* <button type="submit">Enregistrer</button><br>
              <button type="reset">annuler</button></br> */}
          </fieldset>
        </form>
      </div>
    );
  }
}

export default Form;
