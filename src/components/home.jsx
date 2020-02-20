import {Client} from "@stomp/stompjs";
import React, {Component} from 'react';
import { w3cwebsocket as W3CWebSocket } from "websocket";

class Home extends Component {

    state = {
        serverTime: null,
    };

    componentDidMount() {
        // this.client = new Client();
        this.websocket = new W3CWebSocket('ws://localhost:8085/socket');

        this.websocket.onopen = () => {
            console.log('on open');
            this.websocket.send("Hello world");
        };

        this.websocket.onmessage = (message) => {
            console.log(message);
        };
        // this.client.configure({
        //     // brokerURL: 'ws://localhost:8085/updateWS',
        //     brokerURL: 'ws://localhost:8085/socket',
        //     onConnect: () => {
        //         console.log('ON CONNECT');
        //         this.client.subscribe('/game/update', message => {
        //             console.log('SUBSCRIBE');
        //             this.setState({serverTime: message.body});
        //         });
        //         this.client.subscribe('/external/info', message => {
        //             console.log('ALERT');
        //             alert(message);
        //         });
        //     },
        // });
        //
        // this.client.activate();
    }

    render() {
        return (
            <div>
                <p>
                    Server time: {this.state.serverTime ? this.state.serverTime : 'no data'}
                </p>
            </div>
        )
    }
}

export default Home;