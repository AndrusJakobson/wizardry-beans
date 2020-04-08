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
            const responseObject = JSON.parse(message.data);
            console.log(responseObject);
            this.setState({
                maze: responseObject.viewport,
                isAlive: responseObject.playerAlive,
                score: responseObject.playerScore,
                time: responseObject.time
            });
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
        if (this.state == null) {
            return null;
        }
        let maze = this.state.maze ? this.state.maze.maze : {};
        if (this.state.isAlive) {
            return (
                <div onKeyDown={this.keyDownHandler.bind(this)}>
                    <h1>Time: {this.msToTime(this.state.time)}</h1>
                    <Maze mazeRows={maze}/>
                </div>
            )
        }

        return (
            <div>
                <h1>You lost. Score: {this.state.score}</h1>
            </div>
        )
    }

    msToTime(s) {
        const ms = s % 1000;
        s = (s - ms) / 1000;
        const secs = s % 60;
        s = (s - secs) / 60;
        const mins = s % 60;
        const hrs = (s - mins) / 60;

        return ('' + mins).padStart(2, '0') + ':' + (secs + '').padStart(2, '0');
    }
}

export default Home;