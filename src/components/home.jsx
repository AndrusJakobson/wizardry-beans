import React, {Component} from 'react';
import {w3cwebsocket as W3CWebSocket} from "websocket";
import Maze from "./Maze";
import "./../style/base.css";

class Home extends Component {

    constructor(props) {
        super(props);
        document.onkeydown = this.keyDownHandler.bind(this);
    }

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
            this.setState({maze: JSON.parse(message.data).maze});
        };
    }

    keyDownHandler(event) {
        if (event && event.code) {
            const direction = this.getPressedKey(event.code);
            if (direction) {
                this.websocket.send(direction)
            }
        }
    }

    getPressedKey(keyCode) {
        switch (keyCode) {
            case 'ArrowUp':
                return 'up';
            case 'ArrowRight':
                return 'right';
            case 'ArrowDown':
                return 'down';
            case 'ArrowLeft':
                return 'left';
        }
        return null;
    }

    render() {
        let maze = this.state.maze ? this.state.maze.maze : {};
        return (
            <div onKeyDown={this.keyDownHandler.bind(this)}>
                <Maze mazeRows={maze}/>
            </div>
        )
    }
}

export default Home;