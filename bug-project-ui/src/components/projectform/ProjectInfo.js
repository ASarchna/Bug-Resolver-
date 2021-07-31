import axios from "axios";
import { useEffect, useState } from "react"
import { useParams } from "react-router-dom";
import { Button, Modal } from 'react-bootstrap';
import UpdateBug from "../bugform/UpdateBug";
import TablePagination from "../pagination/Pagination";
import Cell from "../cell/Cell";


export default function ProjectInfo() {
    console.log('in projctInfo');
    const { projectid } = useParams();
    const [show, setShow] = useState(false);
    const [bug, setBug] = useState({});
    const [sortedField, setsortedField] = useState(null);
    const [bugs, setBugs] = useState({ bugList: [], bugFilter: '' });
    const [projectInfo, setProjectInfo] = useState({ project: {} });
    const [pagination, setPagination] = useState({ totalPages: 1, pageSize: 5, totalElements: 0 }) ;
    const [activePage, setActivePage] = useState(0) ;
    const [totalBugs,setTotalBugs]=useState(0);
    const handleClose =()=>{
        setShow(false);
    }
    const handleShow =(bug)=>{
        setBug(bug);
        setShow(true);
    
    }
    useEffect(() => {
        const pageParameters = "?pageNo=" + activePage + "&pageSize=" + pagination.pageSize;
        axios.get(process.env.REACT_APP_BACKEND_URL_BUG_LIST_PROJECT + projectid+pageParameters)
            .then(res => {
                setBugs({ ...bugs, bugList: res.data.content });
            });
    }, [activePage]);
    useEffect(() => {
        
        const fetchProject = async () => {
            const promiseProject = await axios.get(process.env.REACT_APP_BACKEND_URL_PARTICULAR_PROJECT + projectid);

            setProjectInfo({ project: promiseProject.data });
        }
        fetchProject();
    }, []);


    useEffect(() => {
        console.log(bugs.bugFilter, '- Filter Has changed')
        console.log(process.env.REACT_APP_SERVER_URL_PROJECT_FILTER)
        const pageParameters = "?pageNo=" + activePage + "&pageSize=" + pagination.pageSize;
        let url = bugs.bugFilter == "" ? process.env.REACT_APP_BACKEND_URL_BUG_LIST_PROJECT + projectid+pageParameters : process.env.REACT_APP_BACKEND_URL_BUG_LIST_OF_PROJECT_FILTER + projectid + '/' + bugs.bugFilter+pageParameters;
        axios.get(url)
            .then(res => {
                setBugs({ ...bugs, bugList: res.data.content });
                setPagination({...pagination,totalPages:res.data.totalPages,totalElements:res.data.totalElements});
                setTotalBugs(res.data.totalElements);
                console.log(bugs);
            });
    }, [bugs.bugFilter,activePage]);

    const handleChange = (event) => {
        setBugs({ ...bugs, [event.target.name]: [event.target.value] });
      setActivePage(0);
    }
    if (sortedField !== null) {
        bugs.bugList.sort((a, b) => {
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
            
            <div>
                <h5 style={{ textAlign: "center", float: "center"}}>Bug list of {projectInfo.project.name}</h5>
                <h6 style={{ textAlign: "center", float: "center"}}>total Bug :{totalBugs} </h6>
               <div>
               
               <input style={{ marginTop: "5px", marginLeft: "10px", textAlign: "right", float: "right" }} 
                            value={bugs.bugFilter} onChange={handleChange} name="bugFilter"></input>

                </div>
                <table class="table table-striped " >
                    <thead>
                        <tr >
                            <th scope='col' onClick={() => setsortedField('name') }
                            >
                            Name </th>
                            <th scope='col' onClick={() => setsortedField('ownerName')}> OwnerName</th>
                            <th scope='col' onClick={() => { setsortedField('email') }}> Email</th>
                            <th scope='col' onClick={() => setsortedField('description')} > description  </th>

                        </tr>

                    </thead>
                    <tbody>
                        {bugs.bugList.map(bug => {
                            console.log(bug);
                            return (<tr scope='row' >
                                <td>{bug.name}</td>
                                <td>{bug.ownerName}</td>
                                <td>{bug.email}</td>
                                <Cell content={bug.description}></Cell>
                                <td><Button onClick={()=>handleShow(bug)} class="btn btn-primary btn-sm" style={{marginRight:"10px"}}>Update Bug</Button >
                               </td>
                            </tr>);
                        })}
                    </tbody>
                </table>
            </div>
            <Modal show={show} onHide={handleClose}>
            <Modal.Header>
               <Modal.Title>{bug.name}</Modal.Title>
            </Modal.Header>
            <Modal.Body><UpdateBug sendBug={bug} handleClose={handleClose}></UpdateBug></Modal.Body>
           
                
            
        </Modal>
        <TablePagination totalPages={pagination.totalPages} activePage={activePage} setActivePage={setActivePage}></TablePagination>
        </div>
    )
}
