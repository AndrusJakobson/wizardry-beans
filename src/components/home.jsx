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
            this.setState({playerMove: message.data})
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
                <p>Write up, right, down, left</p>
                <p>
                    Current move: {this.state.playerMove ? this.state.playerMove : 'no data'}
                </p>
                <input onChange={this.handleChange.bind(this)}/>
                <button onClick={this.sendMessage.bind(this)}>Send message</button>
                <p>Updates every 200 ms</p>
            </div>
        )
    }
}

export default Home;