import React, {Component} from 'react';
import Block from "./Block";

class Row extends Component {
    render() {
        const blocks = this.renderBlocks();
        return (
            <div className="row">
                {blocks}
            </div>
        )
    }

    renderBlocks() {
        if (!this.props.mazeBlocks) {
            return null;
        }

        return this.props.mazeBlocks.map((item, index) => {
            if (item == null) {
                return null;
            }
            return <Block block={item} key={item.id}/>
        })
    }
}

export default Row;