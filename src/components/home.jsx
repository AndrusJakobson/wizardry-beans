import React, {Component} from 'react';
import { w3cwebsocket as W3CWebSocket } from "websocket";
import Maze from "./Maze";
import "./../style/base.css";

class Home extends Component {

    state = {
        serverTime: null,
        value: "",
    };

    componentDidMount() {
        this.websocket = new W3CWebSocket('ws://localhost:8085/socket');

        this.websocket.onopen = () => {
            console.log('on open');
        };

        this.websocket.onmessage = (message) => {
            this.setState({maze: JSON.parse(message.data)})
        };
    }

    render() {
        return (
            <div>
                <Maze mazeRows={this.state.maze}/>
            </div>
        )
    }
}

export default Home;