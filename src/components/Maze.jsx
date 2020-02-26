import React, {Component} from 'react';
import Row from "./Row";

class Maze extends Component {
    render() {
        const rows = this.renderRows();
        return (
            <div className="maze">
                {rows}
            </div>
        )
    }

    renderRows() {
        if (!this.props.mazeRows) {
            return null;
        }
        return this.props.mazeRows.map((item, index) => {
            return <Row mazeBlocks={item.mazeBlocks} key={item.id}/>
        });
    }
}

export default Maze;