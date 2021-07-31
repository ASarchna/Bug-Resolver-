import React from 'react'
import { OverlayTrigger,Tooltip } from 'react-bootstrap';
import { ApplyEllipsis } from '../Utils';


export default function Cell(props) {
    return (
        <td>
        <OverlayTrigger
            placement="bottom"
            overlay={<Tooltip>{props.content}</Tooltip>}
        >
            <span>{ApplyEllipsis(props.content)}</span>
        </OverlayTrigger>
    </td>

    )
}
