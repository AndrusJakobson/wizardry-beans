import React, {Component} from 'react';

class Block extends Component {
    render() {
        const isWall = this.props.block ? this.props.block.wall : false;
        const isPlayer = this.props.block ? this.props.block.player : false;
        let className = isPlayer ? "player" : "";
        if (className === "") {
            className = isWall ? "wall" : "";
        }

        return (
            <div className={"block " + className}>

            </div>
        )
    }
}

export default Block;