import React from 'react'
import { Pagination } from 'react-bootstrap';
 
export default function TablePagination(props) {
    let items = [];
    for (let number = 0; number < props.totalPages; number++) {
        items.push(
            <Pagination.Item key={number + 1} active={number === props.activePage} activeLabel="" onClick={() => { props.setActivePage(number) }}>
                { number + 1}
            </Pagination.Item>,
        );
    }
 
    return (
        <Pagination className="justify-content-center" size="sm">
            <Pagination.First onClick={() => { props.setActivePage(0) }} />
            <Pagination.Prev onClick={() => { props.setActivePage(props.activePage ? props.activePage - 1 : props.activePage) }} />
            {items}
            <Pagination.Next onClick={() => { props.setActivePage(props.activePage === props.totalPages - 1 ? props.activePage : props.activePage + 1) }} />
            <Pagination.Last onClick={() => { props.setActivePage(props.totalPages - 1) }} />
        </Pagination>
    )
}