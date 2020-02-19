import {Client} from "@stomp/stompjs";
import React, {Component} from 'react';

class Home extends Component {

    state = {
        serverTime: null,
    };

    componentDidMount() {
        this.client = new Client();

        this.client.configure({
            brokerURL: 'ws://localhost:8085/updateWS',
            onConnect: () => {
                this.client.subscribe('/game/update', message => {
                    this.setState({serverTime: message.body});
                });
                this.client.subscribe('/external/info', message => {
                    alert(message);
                });
            },
        });

        this.client.activate();
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