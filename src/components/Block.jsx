import React, {Component} from 'react';

class Block extends Component {
    render() {
        const isWall = this.props.block ? this.props.block.wall : false;
        const isPlayer = this.props.block ? this.props.block.player : false;
        const isGhost = this.props.block ? this.props.block.ghost : false;
        const hasPoint = this.props.block ? this.props.block.point : false;
        let className = isPlayer ? "player" : "";
        className = isWall ? "wall" : className;
        className = isGhost ? "ghost" : className;

        return (
            <div className={"block " + className}>
                {hasPoint && <h1 className="point">o</h1>}
            </div>
        )
    }
}

export default Block;