import React from 'react';
class Paty extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            query: '',
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        console.log('Recherche soumise pour :', this.state.query);
    }

    handleInputChange(event) {
        this.setState({
            query: event.target.value,
        });
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input
                    type="text"
                    value={this.state.query}
                    onChange={this.handleInputChange}
                    placeholder="Entrez votre recherche"
                />
                <button type="submit">Rechercher</button>
            </form>
        );
    }
}

export default Paty;