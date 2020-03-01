import React, {Component} from 'react';

class Block extends Component {
    render() {
        const isWall = this.props.block ? this.props.block.wall : false;
        const isPlayer = this.props.block ? this.props.block.player : false;
        const hasPoint = this.props.block ? this.props.block.point : false;
        let className = isPlayer ? "player" : "";
        if (className === "") {
            className = isWall ? "wall" : "";
        }

        return (
            <div className={"block " + className}>
                {hasPoint && <h1 className="point">o</h1>}
            </div>
        )
    }
}

export default Block;