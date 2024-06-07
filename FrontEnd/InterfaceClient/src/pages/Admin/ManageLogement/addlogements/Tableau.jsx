import React from "react";
import { Component } from "react";


class Tableau extends Component {
    constructor(props){
        super(props)
        this.state = {
            
        }

    }
    render() {
        return (
            <table>
                <thead>
                    <tr>
                        <th >name</th>
                        <th>job</th>
                        
                    </tr>
                </thead>

                <tbody>
                    <tr>
                        <td>elsa</td>
                        <td>wilf</td>
                    </tr>
                    <tr>
                        <td>mark</td>
                        <td>laeti</td>
                    </tr>
                    <tr>
                        <td>dee</td>
                        <td>aspiring </td>
                    </tr>
                    <tr>
                        <td>dennise</td>
                        <td>bartendez</td>
                    </tr>
                </tbody>
            </table>
        );
    }
}

export default Tableau;