import axios from 'axios'
import { Button, Modal } from 'react-bootstrap';
import React, { useEffect, useState } from 'react'
import Updateproject from './UpdateProject';
import Cell from '../cell/Cell';
import { ApplyEllipsis } from '../Utils';
import ProjectInfo from './ProjectInfo';
import { BrowserRouter, Route, useHistory } from 'react-router-dom';
import TablePagination from '../pagination/Pagination';

export default function Projectlist() {
   const history= useHistory();
    const [show, setShow] = useState(false);
    const [project, setproject] = useState({});
    const [sortedField, setsortedField] = useState(null);
    const [projects, setProjects] = useState({ projectList: [], projectFilter: '' });
    const [pagination, setPagination] = useState({ totalPages: 1, pageSize: 5, totalElements: 0 }) ;
    const [activePage, setActivePage] = useState(0) ; 
    const [totalProjects,setTotalProjects]=useState(0);

    const handleClose =()=>{
        setShow(false);
    }
    const handleShow =(project)=>{
        setproject(project);
        setShow(true);

    }
    
    
    useEffect(() => {
        const pageParameters = "?pageNo=" + activePage + "&pageSize=" + pagination.pageSize;
        console.log(projects.projectFilter, '- Filter Has changed')
        console.log(process.env.REACT_APP_SERVER_URL_PROJECT_FILTER+projects.projectFilter+pageParameters)
        let url = projects.projectFilter == "" ? process.env.REACT_APP_BACKEND_URL_PROJECT_PAGINATION+pageParameters : process.env.REACT_APP_SERVER_URL_PROJECT_FILTER + projects.projectFilter+pageParameters;
        axios.get(url)
            .then(res => {
                setProjects({ ...projects, projectList: res.data.content });
                setPagination({...pagination,totalPages:res.data.totalPages,totalElements:res.data.totalElements});
                setTotalProjects(res.data.totalElements)
                console.log(projects);
            });
    },[projects.projectFilter,activePage]);


     const handleChange =(event)=>{
        setProjects({ ...projects, [event.target.name]: [event.target.value] });
        setActivePage(0);

     }
     if(sortedField!==null){
         projects.projectList.sort((a,b)=>{
                if (a[sortedField] < b[sortedField]) {
                    return -1;
                }
                if (a[sortedField] > b[sortedField]) {
                    return 1;
                }
                return 0;
            });
     }
    return (
        <div>
            <h3 style={{ textAlign: "center", float: "center" }}>Projects</h3>
            <h6 style={{ textAlign: "center", float: "center" }}> total projects :{totalProjects}</h6>
            <div>
            
            <input style={{ marginTop: "5px", marginRight:"2%", textAlign: "right", float: "right" }} value={projects.projectFilter} onChange={handleChange} name="projectFilter"></input>
            <label style={{marginTop:"5px",textAlign: "right", float: "right",marginRight:"1%"}}>Search Project</label>
             </div>
             <table class="table table-striped " style={{width:"96%", marginLeft:"2%", marginRight:"2%"}}>
            <thead>
                <tr >
                <th scope='col' onClick={() => setsortedField('name')}> 
                 Name </th>
                <th scope='col' onClick={() => setsortedField('description')} > description  </th>
                <th scope='col'  > </th>
                
                </tr>
                
            </thead>
             <tbody>
                {projects.projectList.map(project =>{
                    console.log(project);
                    return( <tr scope='row' >
                        <td>{project.name}</td>
                         <Cell content={project.description}></Cell>
                        <td>
                        <Button onClick={()=>handleShow(project)} class="btn btn-primary btn-sm" style={{marginRight:"10px"}}>Update Project</Button >
                        <Button variant="primary" > <a  style={{ textDecoration: 'none',color :"white" }} href={'/getProject/'+ project.id} >Open Bug List</a></Button>
                        </td>
                        </tr>);
                })}
        </tbody>  
        </table>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header>
               <Modal.Title>{project.name}</Modal.Title>
            </Modal.Header>
            <Modal.Body><Updateproject sendProject={project} handleClose={handleClose}></Updateproject></Modal.Body>
           
        </Modal>
        <TablePagination totalPages={pagination.totalPages} activePage={activePage} setActivePage={setActivePage}></TablePagination>
        </div>
        
    )
}
