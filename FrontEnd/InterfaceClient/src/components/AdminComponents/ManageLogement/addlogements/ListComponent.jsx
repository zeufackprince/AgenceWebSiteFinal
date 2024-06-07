import React from "react";
import { Component } from "react";
class ListComponent extends Component {
    constructor(props){
        super(props)
        this.state = {
        }        
    }
    render(){
        return (
            <div className="shopping-list">
            <h1>liste de courses pour {this.props.name}</h1>
            <ul>
                <li>insta</li>
                <li>wattsapp</li>
                <li>oculus</li>
            </ul>
        </div>
        );
    }
}

        
        

export default ListComponent 