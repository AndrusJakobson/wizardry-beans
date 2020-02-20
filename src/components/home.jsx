import React, {Component} from 'react';
import { w3cwebsocket as W3CWebSocket } from "websocket";

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
            console.log("message: " + message.data);
        };
    }

    sendMessage() {
        this.websocket.send(this.state.value);
    }

    handleChange(event) {
        this.setState({value: event.target.value});
    }

    render() {
        return (
            <div>
                <p>
                    Server time: {this.state.serverTime ? this.state.serverTime : 'no data'}
                </p>
                <input onChange={this.handleChange.bind(this)}/>
                <button onClick={this.sendMessage.bind(this)}>Send message</button>
            </div>
        )
    }
}

export default Home;