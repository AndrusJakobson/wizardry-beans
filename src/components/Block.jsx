import React, {Component} from 'react';

class Block extends Component {
    render() {
        const isWall = this.props.block ? this.props.block.wall : false;

        return (
            <div className={"block" + (isWall ? " wall" : "")}>

            </div>
        )
    }
}

export default Block;